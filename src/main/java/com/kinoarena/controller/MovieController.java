package com.kinoarena.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@ResponseBody
	public double rateMovieAjax(HttpServletRequest request,HttpSession session) throws SQLException, InvalidDataException {
		User user = (User) session.getAttribute("user");
	
		int movieIdToBeRated = Integer.parseInt(request.getParameter("id"));
		int newRating = Integer.parseInt(request.getParameter("rating"));

		if(newRating<1 || newRating>10) {
			throw new InvalidDataException("Invalid entered rating. It should be between 1 and 10");
		}
		
		Movie movie = movieDao.getMovieById(movieIdToBeRated);
		userDao.rateMovie(user, movie, newRating);
		
		double newAvgRating = movieDao.getMovieRatingById(movieIdToBeRated);
		
		return newAvgRating;
	}

	@RequestMapping(value = "/reserveInterim", method = RequestMethod.POST)
	public String reserveInterim(HttpServletRequest request, HttpServletResponse response,
			Model springModel,HttpSession session) throws SQLException, InvalidDataException {

        //here we need user due to check his ages for discount
		User user = (User) session.getAttribute("user");

		int broadcastId = Integer.parseInt(request.getParameter("broadcastSelect"));
		Broadcast broadcast = broadcastDao.getBroadcastById(broadcastId);
		Cinema cinema = cinemaDao.getCinemaById(broadcast.getCinemaId());
		Movie movie = movieDao.getMovieById(broadcast.getMovieId());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		
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
		
		totalPrice = totalPrice*100;
		totalPrice = Math.round(totalPrice);
		totalPrice = totalPrice/100;
         		
		springModel.addAttribute("cinemaName",cinema.getName());
		springModel.addAttribute("hallId",broadcast.getHallId());
		springModel.addAttribute("movieTitle", movie.getTitle());
		springModel.addAttribute("broadcastProjectionTime", broadcast.getProjectionTime().format(formatter));
		springModel.addAttribute("broadcastId",broadcastId);
		springModel.addAttribute("broadcastPrice",totalPrice);

		return "reservationHall";

	}

	
	@RequestMapping(value="/reserveSubmit", method=RequestMethod.POST)
	public String reserve(HttpServletRequest request,HttpServletResponse response,
			Model springModel,HttpSession session) throws InvalidDataException, SQLException {
		User user = (User) session.getAttribute("user");
		int broadcast_id = Integer.parseInt(request.getParameter("hiddenBroadcastId"));

		String seats = (String) request.getParameter("hiddenSeats");
		if (seats.isEmpty()) {
			return "viewAllMovies";
		}
		String[] allSeats = seats.split(",");
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
	
		return allSeatsForBroadcast.toString().substring(1, allSeatsForBroadcast.toString().length()-1);
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(HttpServletRequest request,HttpSession session, HttpServletResponse response) throws InvalidDataException, SQLException {
		String name = request.getParameter("movie");
		Movie selectedMovie = movieDao.getMovieByName(name);
		session.setAttribute("selectedMovie", selectedMovie);
		
		return "viewAMovie";
	}
	
	@RequestMapping(value="/searchAutoComplete", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<String> searchAutoComplete(HttpServletResponse response, HttpServletRequest request) throws SQLException {
		response.setContentType("application/json");
        String term = request.getParameter("term");
        ArrayList<String> allMovies = (ArrayList<String>) movieDao.getMoviesContains(term);
        return allMovies;
	}

	
}

