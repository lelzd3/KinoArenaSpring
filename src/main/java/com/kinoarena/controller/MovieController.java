package com.kinoarena.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.kinoarena.model.dao.HallDao;
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

	@Autowired
	private MovieDao movieDao;
	
	@Autowired	
	private BroadcastDao broadcastDao;
	
	@Autowired
	private CinemaDao cinemaDao;
	
	@Autowired
	private ReservationDao reservationDao;
	
	@Autowired 
	private UserDao userDao; 
	
	private static final String COVER_FILE_SUFFIX = "-cover";
	private static final String SERVER_FILES_LOCATION = "D:\\kinoarenaMovieCovers\\";

	@RequestMapping(value = "/getCover", method = RequestMethod.GET)
	@ResponseBody
	public void getCover(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String file = request.getParameter("file");
		String file_location = SERVER_FILES_LOCATION + file + COVER_FILE_SUFFIX;
		File serverFile = new File(file_location);
		Files.copy(serverFile.toPath(), response.getOutputStream());
	}

	@RequestMapping(value = "/rateMovie", method = RequestMethod.POST)
	public String rateMovie(HttpServletRequest request) throws SQLException, InvalidDataException {
		User user = (User) request.getSession().getAttribute("user");
		
		String jspName = request.getParameter("hiddenJspName");
		
		int movieIdToBeRated = Integer.parseInt(request.getParameter("movieIdToBeRated"));
		int newRating = Integer.parseInt(request.getParameter("ratingSelect"));

		Movie movie = movieDao.getMovieById(movieIdToBeRated);
		userDao.rateMovie(user, movie, newRating);
		return jspName;
	}

	@RequestMapping(value = "/reserveInterim", method = RequestMethod.POST)
	public String reserveInterim(HttpServletRequest request, HttpServletResponse response, Model springModel) throws SQLException, InvalidDataException {

        //here we need user due to check his ages for discount
		User user = (User) request.getSession().getAttribute("user");

		int broadcastId = Integer.parseInt(request.getParameter("broadcastSelect"));
		Broadcast broadcast = broadcastDao.getBroadcastById(broadcastId);
		Cinema cinema = cinemaDao.getCinemaById(broadcast.getCinemaId());
		Movie movie = movieDao.getMovieById(broadcast.getMovieId());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd EE HH:mm");
		
		double totalPrice = broadcast.getPrice();
		double amountToDecrease = totalPrice * 0.2;
		
		//20% off on Thursday
		if(broadcast.getProjectionTime().getDayOfWeek().name().equalsIgnoreCase("Thursday")) {
			totalPrice = totalPrice - amountToDecrease;
		}
		//20% more if under 18
		if(user.getAge() <= 18) {
			totalPrice = totalPrice - amountToDecrease;
		}
		
		springModel.addAttribute("cinemaName",cinema.getName());
		springModel.addAttribute("hallId",broadcast.getHallId());
		springModel.addAttribute("movieTitle", movie.getTitle());
		springModel.addAttribute("broadcastProjectionTime", broadcast.getProjectionTime().format(formatter));
		springModel.addAttribute("broadcastId",broadcastId);
		springModel.addAttribute("broadcastPrice",totalPrice);

		return "reservationHall";

	}

	
	@RequestMapping(value="/reserveSubmit", method=RequestMethod.POST)
	public String reserve(HttpServletRequest request,HttpServletResponse response,Model springModel) throws InvalidDataException, SQLException {
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
		reservationDao.addReservation(reservation, selectedSeats);
		return "viewAllMovies";
	}

	@RequestMapping(value="/getReservedSeats/{broadcastId}", method=RequestMethod.GET)
	@ResponseBody
	public String reserveSubmit(HttpServletRequest request,HttpServletResponse response,@PathVariable(value="broadcastId") Integer broadcastId) throws SQLException, InvalidDataException {
		Broadcast broadcast = broadcastDao.getBroadcastById(broadcastId);
		ArrayList<String> allSeatsForBroadcast = reservationDao.getAllOccupiedSeatsForABroadcast(broadcast);
		//response.getWriter().write(allSeatsForBroadcast.toString().substring(1, allSeatsForBroadcast.toString().length()-1));
		
		return allSeatsForBroadcast.toString().substring(1, allSeatsForBroadcast.toString().length()-1);
	}


	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(HttpServletRequest request, HttpServletResponse response) throws InvalidDataException, SQLException {
		String name = request.getParameter("movie");
		Movie selectedMovie = movieDao.getMovieByName(name);
		request.getSession().setAttribute("selectedMovie", selectedMovie);
		
		return "viewAMovie";
	}
	
	//TODO put in the rest controller
	@RequestMapping(value="/searchAutoComplete", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<String> searchAutoComplete(HttpServletResponse response, HttpServletRequest request) throws SQLException {
		response.setContentType("application/json");
        String term = request.getParameter("term");
        System.out.println("Data from ajax call " + term);
        ArrayList<String> allMovies = (ArrayList<String>) movieDao.getMoviesContains(term);
        System.out.println(allMovies.toString());
        return allMovies;
	}

	
}

