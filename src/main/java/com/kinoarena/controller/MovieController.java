package com.kinoarena.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.kinoarena.controller.manager.AdminManager;
import com.kinoarena.model.dao.BroadcastDao;
import com.kinoarena.model.dao.CinemaDao;
import com.kinoarena.model.dao.MovieDao;
import com.kinoarena.model.dao.ReservationDao;
import com.kinoarena.model.dao.UserDao;
import com.kinoarena.model.pojo.Broadcast;
import com.kinoarena.model.pojo.Cinema;
import com.kinoarena.model.pojo.Movie;
import com.kinoarena.model.pojo.Reservation;
import com.kinoarena.model.pojo.Seat;
import com.kinoarena.model.pojo.User;
import com.kinoarena.utilities.exceptions.InvalidDataException;

@Controller
@MultipartConfig
public class MovieController {

	private static final String COVER_FILE_SUFFIX = "-cover";
	private static final String SERVER_FILES_LOCATION = "D:\\kinoarenaMovieCovers\\";

	// TODO admin Add/remove movie
	// this needs to be tested
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String saveImage(HttpServletRequest request, @RequestParam("file") MultipartFile uploadedFile)
			throws IOException {

		try {
			// String extension =
			// FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
			// //(gif|jpg|jpeg|tiff|png)
			//
			// if(extension != "gif" || extension != "jgp" || extension != "jpeg" ||
			// extension != "tiff" ||extension != "png") {
			// //throw smthing
			// }

			// maybe put all req params in signature?
			User admin = (User) request.getSession().getAttribute("admin");
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			Double duration = Double.parseDouble(request.getParameter("duration"));
			Double rating = 0.0;

			Movie movie = new Movie(title, description, rating, duration);
			String file_location = SERVER_FILES_LOCATION + movie.getTitle() + ";" + movie.getId() + COVER_FILE_SUFFIX;
			movie.setFile_location(file_location);

			File serverFile = new File(file_location);
			Files.copy(uploadedFile.getInputStream(), serverFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

			AdminManager.getInstance().addNewMovie(movie, admin);

			return "adminMain";
		} catch (Exception e) {
			request.setAttribute("exception", e);
			return "error";
		}
	}

	@RequestMapping(value = "/getCover", method = RequestMethod.GET)
	@ResponseBody
	public void getCover(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String file = request.getParameter("file");
		String file_location = SERVER_FILES_LOCATION + file + COVER_FILE_SUFFIX;
		File serverFile = new File(file_location);
		Files.copy(serverFile.toPath(), response.getOutputStream());
	}

	@RequestMapping(value = "/rateMovie", method = RequestMethod.POST)
	@ResponseBody
	public String rateMovie(HttpServletRequest request) {
		try {
			User user = (User) request.getSession().getAttribute("user");
			int movieIdToBeRated = Integer.parseInt(request.getParameter("movieIdToBeRated"));
			int newRating = Integer.parseInt(request.getParameter("ratingSelect"));

			Movie movie = MovieDao.getInstance().getMovieById(movieIdToBeRated);
			UserDao.getInstance().rateMovie(user, movie, newRating);
			return "viewAllMovies";
		} catch (Exception e) {
			request.setAttribute("exception", e);
			return "error";
		}
	}

	@RequestMapping(value = "/reserveInterim", method = RequestMethod.POST)
	@ResponseBody
	public String reserveInterim(HttpServletRequest request, HttpServletResponse response, Model springModel) {
		try {
			int broadcastId = Integer.parseInt(request.getParameter("broadcastSelect"));
			Broadcast broadcast = BroadcastDao.getInstance().getBroadcastById(broadcastId);
			Cinema cinema = CinemaDao.getInstance().getCinemaById(broadcast.getCinemaId());
			Movie movie = MovieDao.getInstance().getMovieById(broadcast.getMovieId());

			springModel.addAttribute("cinemaName",cinema.getName());
			springModel.addAttribute("hallId",broadcast.getHallId());
			springModel.addAttribute("movieTitle", movie.getTitle());
			springModel.addAttribute("broadcastProjectionTime", broadcast.getProjectionTime());
			springModel.addAttribute("broadcastPrice",broadcast.getPrice());
			springModel.addAttribute("broadcastId",broadcastId);

			return "reservationHall";
		} catch (Exception e) {
			request.setAttribute("exception", e);
			return "error";
		}

	}

	
	@RequestMapping(value="/reserveSubmit", method=RequestMethod.POST)
	public String reserve(HttpServletRequest request,HttpServletResponse response,Model springModel) {
		try {
			User user = (User) request.getSession().getAttribute("user");
			int broadcast_id = Integer.parseInt(request.getParameter("hiddenBroadcastId"));

			String s = (String) request.getParameter("hiddenSeats");
			if (s == "") {
				// try with isEmpty()
				return "viewAllMovies";
			}
			String[] allSeats = s.split(",");
			ArrayList<Seat> selectedSeats = new ArrayList<Seat>();

			for (int i = 0; i < allSeats.length; i++) {
				String[] rowAndCow = allSeats[i].split(" ");
				int row = Integer.parseInt(rowAndCow[0].replaceAll("\\D+", ""));
				int col = Integer.parseInt(rowAndCow[1].replaceAll("\\D+", ""));

				Seat newSeat = new Seat(row, col);
				selectedSeats.add(newSeat);
			}

			Reservation reservation = new Reservation(user.getId(), broadcast_id, selectedSeats);
			ReservationDao.getInstance().addReservation(reservation, selectedSeats);
			return "viewAllMovies";
		} catch (Exception e) {
			request.setAttribute("exception", e);
			return "error";
		}
	}

	@RequestMapping(value="/getReservedSeats/{broadcastId}", method=RequestMethod.GET)
	@ResponseBody
	public String reserveSubmit(HttpServletRequest request,HttpServletResponse response,@PathVariable(value="broadcastId") Integer broadcastId) {
		try {
			Broadcast broadcast = BroadcastDao.getInstance().getBroadcastById(broadcastId);
			ArrayList<String> allSeatsForBroadcast = ReservationDao.getInstance().getAllOccupiedSeatsForABroadcast(broadcast);
			//response.getWriter().write(allSeatsForBroadcast.toString().substring(1, allSeatsForBroadcast.toString().length()-1));
			return allSeatsForBroadcast.toString().substring(1, allSeatsForBroadcast.toString().length()-1);
		} catch (Exception e) {
			return e.getMessage();
//			request.setAttribute("exception", e);
//			return "error";
		}
	}


	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("movie");
		try {
			Movie selectedMovie = MovieDao.getInstance().getMovieByName(name);
			request.getSession().setAttribute("selectedMovie", selectedMovie);
		} catch (Exception e) {
			System.out.println("SQL Bug: " + e.getMessage());
			return "error";
		}
		return "viewAMovie";
	}
	
	//TODO put in the rest controller
	@RequestMapping(value="/searchAutoComplete", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<String> searchAutoComplete(HttpServletResponse response, HttpServletRequest request) {
		  response.setContentType("application/json");
        try {
            String term = request.getParameter("term");
            System.out.println("Data from ajax call " + term);
            ArrayList<String> allMovies = (ArrayList<String>) MovieDao.getInstance().getMoviesContains(term);
            System.out.println(allMovies.toString());
            return allMovies;
        } catch (Exception e) {
      	  System.out.println("Bug: " + e.getMessage());
			  return null;
        }
	}

	
}

