package com.kinoarena.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kinoarena.controller.manager.AdminManager;
import com.kinoarena.model.dao.BroadcastDao;
import com.kinoarena.model.dao.CinemaDao;
import com.kinoarena.model.dao.HallDao;
import com.kinoarena.model.dao.MovieDao;
import com.kinoarena.model.dao.UserDao;
import com.kinoarena.model.pojo.Broadcast;
import com.kinoarena.model.pojo.Cinema;
import com.kinoarena.model.pojo.Hall;
import com.kinoarena.model.pojo.Movie;
import com.kinoarena.model.pojo.User;
import com.kinoarena.utilities.Validations;
import com.kinoarena.utilities.exceptions.InvalidDataException;
import com.kinoarena.utilities.exceptions.NotAnAdminException;


@Controller
public class AdminController {
	
	private static final int MAX_PRICE = 99;

	@Autowired
	private MovieDao movieDao;
	
	@Autowired	
	private BroadcastDao broadcastDao;
	
	@Autowired
	private HallDao hallDao;
	
	@Autowired
	private CinemaDao cinemaDao;
	
	@Autowired
	private AdminManager adminManager;
	
	@Autowired
	private UserDao userDao;
	
	private static final String COVER_FILE_SUFFIX = "-cover";
	private static final String SERVER_FILES_LOCATION = "D:\\kinoarenaMovieCovers\\";

	
	//adminPanel.jsp->removeBroadcast.jsp
	@RequestMapping(value = "/removeBroadcastPage", method = RequestMethod.GET)
	public String getToRemoveBroadcast(Model springModel) throws InvalidDataException, SQLException {
		
		springModel.addAttribute("broadcasts", broadcastDao.getAllBroadcasts());
		return "removeBroadcast";

	}
	
	
	@RequestMapping(value = "/removeBroadcast", method = RequestMethod.POST)
	public String removeBroadcast(@RequestParam("broadcastSelect") int broadcastId, HttpSession session,
			Model springModel) throws SQLException, NotAnAdminException, InvalidDataException {

		User admin = (User) session.getAttribute("admin");
	
		adminManager.removeBroadcast(broadcastId, admin);
		springModel.addAttribute("message", "Successfully removed broadcast!");
		springModel.addAttribute("broadcasts", broadcastDao.getAllBroadcasts());
		return "removeBroadcast";
	}

	//adminPanel.jsp -> addBroadcast.jsp
	@RequestMapping(value = "/addBroadcastPage", method = RequestMethod.GET)
	public String getToAddBroadcast(Model springModel,HttpSession session) throws SQLException, InvalidDataException {
	
		User admin = (User) session.getAttribute("admin");

		springModel.addAttribute("movies", movieDao.getAllMovies());
		springModel.addAttribute("cinemas", cinemaDao.getAllCinemas());
		springModel.addAttribute("halls", hallDao.getAllHalls());
		
		return "addBroadcast";
	}
	
	@RequestMapping(value = "/addBroadcast", method = RequestMethod.POST)
	public String addBroadcast(
					@RequestParam("movieSelect") int movieId,
					@RequestParam("cinemaSelect") int cinemaId,
					@RequestParam("hallSelect") int hallId,
					@RequestParam("projection_time") String time,
					@RequestParam("price") double price, HttpSession session,
					Model springModel) throws SQLException, NotAnAdminException, InvalidDataException {
		
		User admin = (User) session.getAttribute("admin");

		LocalDateTime projectionTime = LocalDateTime.parse(time);
		Broadcast newBroadcast = new Broadcast(cinemaId, movieId, hallId, projectionTime, price);
		adminManager.addNewBroadcast(newBroadcast, admin);
		springModel.addAttribute("message", "Successfully added a broadcast!");
		springModel.addAttribute("movies", movieDao.getAllMovies());
		springModel.addAttribute("cinemas", cinemaDao.getAllCinemas());
		springModel.addAttribute("halls", hallDao.getAllHalls());
		return "addBroadcast";
	}

	//adminPanel.jsp -> makeAdmin.jsp
	@RequestMapping(value = "/makeAdminPage", method = RequestMethod.GET)
	public String getToMakeAdmin(Model springModel) throws SQLException, InvalidDataException {

		springModel.addAttribute("usersThatAreNotAdmin", userDao.getAllUsersButNoAdmins());
		return "makeAdmin";
	}
	
	
	@RequestMapping(value = "/makeAdmin", method = RequestMethod.POST)
	public String infoAdminCreateAdmin(@RequestParam("usersSelect") String email, HttpSession session,
			Model springModel) throws NotAnAdminException, SQLException, InvalidDataException {

		User user = (User) session.getAttribute("admin");
		Validations.verifyEmail(email);
		adminManager.changeUserIsAdminStatus(user, email.trim());

		springModel.addAttribute("message", "Admin is created");
		springModel.addAttribute("usersThatAreNotAdmin", userDao.getAllUsersButNoAdmins());
		return "makeAdmin";
	}
	
	//adminPanel.jsp -> addCinema.jsp
	@RequestMapping(value = "/addCinemaPage", method = RequestMethod.GET)
	public String getToAddCinema() {
		return "addCinema";
	}
	

	@RequestMapping(value = "/addCinema", method = RequestMethod.POST)
	public String addCinema(
			@RequestParam("address") String address,
			@RequestParam("name") String name,
			HttpSession session, Model springModel) throws SQLException, NotAnAdminException, InvalidDataException {

		User admin = (User) session.getAttribute("admin");
		Cinema newCinema = new Cinema(name, address);
		adminManager.addNewCinema(newCinema, admin);
		springModel.addAttribute("message","Successfully added a cinema");
		return "addCinema";
    }
	
	@RequestMapping(value = "/removeCinemaPage", method = RequestMethod.GET)
	public String getToRemoveCinema(Model springModel) throws SQLException, InvalidDataException {
		springModel.addAttribute("cinemas", cinemaDao.getAllCinemas());
		return "removeCinema";
	}
	
	@RequestMapping(value = "/removeCinema",method = RequestMethod.POST)
	public String removeCinema(
			@RequestParam("cinemaSelect") Integer cinemaId,
			HttpSession session,Model springModel) throws SQLException, InvalidDataException, NotAnAdminException {

		User admin = (User) session.getAttribute("admin");
		Cinema cinemaToDelete = cinemaDao.getCinemaById(cinemaId);

		adminManager.removeCinema(cinemaToDelete,admin);
		springModel.addAttribute("message","Successfully removed a cinema");
		springModel.addAttribute("cinemas", cinemaDao.getAllCinemas());
		return "removeCinema";
	}

	//adminPanel.jsp->addHall.jsp
	@RequestMapping(value = "/addHallPage", method = RequestMethod.GET)
	public String getToAddHall(Model springModel) throws SQLException, InvalidDataException {
		springModel.addAttribute("cinemas", cinemaDao.getAllCinemas());
		return "addHall";
	}
	
	
	@RequestMapping(value = "/addHall", method = RequestMethod.POST)
	public String addHall(
			@RequestParam("cinemaSelect") int cinemaId ,
			HttpSession session, Model springModel) throws InvalidDataException, SQLException, NotAnAdminException {

		User admin = (User) session.getAttribute("admin");

		Hall newHall = new Hall(cinemaId);
		adminManager.addNewHall(newHall, admin);
		springModel.addAttribute("message","Successfully added a hall");
		springModel.addAttribute("cinemas", cinemaDao.getAllCinemas());
		return "addHall";
	}

	//adminPanel.jsp -> changeBroadcastPrice.jsp	
	@RequestMapping(value = "/changeBroadcastPricePage", method = RequestMethod.GET)
	public String goToChangeBroadcastPrice(Model springModel) throws InvalidDataException, SQLException {

		springModel.addAttribute("broadcasts", broadcastDao.getAllBroadcasts());
		return "changeBroadcastPrice";
	}
	
	
	@RequestMapping(value = "/changeBroadcastPrice", method = RequestMethod.POST)
	public String changeBroadcastPrice(
			@RequestParam("broadcastSelect") int broadcastId,
			@RequestParam("newPrice") double newPrice,
			HttpSession session,
			Model springModel) throws SQLException, NotAnAdminException, InvalidDataException {
		
		if(newPrice < 0) {
			throw new InvalidDataException("New price is lower than 0");
		}
		if(newPrice > MAX_PRICE) {
			throw new InvalidDataException("New price is lower than "+ MAX_PRICE);
		}
		
		User admin = (User) session.getAttribute("admin");
		
		adminManager.changeBroadcastPrice(admin, broadcastId, newPrice);
		springModel.addAttribute("message","Successfully changed a price");
		springModel.addAttribute("broadcasts", broadcastDao.getAllBroadcasts());
		return "changeBroadcastPrice";
	}

	//adminPanel.jsp -> removeHall.jsp
	@RequestMapping(value = "/removeHallPage", method = RequestMethod.GET)
	public String getToRemoveHall(Model springModel) throws SQLException, InvalidDataException {
		
		springModel.addAttribute("halls",hallDao.getAllHalls());
		return "removeHall";
	}
	
	
	@RequestMapping(value = "/removeHall", method = RequestMethod.POST)
	public String removeHall(
			@RequestParam("hallSelect") int hallId ,
			HttpSession session,Model springModel) throws SQLException, NotAnAdminException, InvalidDataException {

		User admin = (User) session.getAttribute("admin");
		Hall hallToDelete = hallDao.getHallById(hallId);
		adminManager.removeHall(hallToDelete, admin);
		springModel.addAttribute("message","Successfully removed a hall");
		springModel.addAttribute("halls",hallDao.getAllHalls());
		return "removeHall";
	}

	//adminPanel.jsp -> addMovie.jsp
	@RequestMapping(value = "/addMoviePage", method = RequestMethod.GET)
	public String getToAddMovie(Model springModel) throws SQLException {
		return "addMovie";
	}
	

	@RequestMapping(value = "/addMovie", method = RequestMethod.POST)
	public String addMovie(
			@RequestParam("title") String title,
			@RequestParam("description") String description,
			@RequestParam("duration") double duration,
			@RequestParam("file") MultipartFile uploadedFile,
			@RequestParam("genresSelect") ArrayList<String> genresSelected,
			HttpSession session, Model springModel) throws InvalidDataException, SQLException, NotAnAdminException, IOException {
	

		User admin = (User) session.getAttribute("admin");
	
		Double rating = 0.0;
		Movie movie = new Movie(title, description, rating, duration,genresSelected);
		System.out.println(movie + " "+admin+" "+genresSelected);
		adminManager.addNewMovie(movie, admin,genresSelected);
		
		String file_location = SERVER_FILES_LOCATION+movie.getTitle()+";"+movie.getId()+COVER_FILE_SUFFIX;
		movie.setFile_location(file_location);
		
		movieDao.updateFileLocaiton(movie,file_location);	
		
		File serverFile = new File(file_location);
		Files.copy(uploadedFile.getInputStream(), serverFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

		springModel.addAttribute("message","Successfully added a movie");
		return "addMovie";

    }

	//adminPanel.jsp -> removeMovie.jsp
	@RequestMapping(value = "/removeMoviePage", method = RequestMethod.GET)
	public String getToRemoveMovie(Model springModel) throws SQLException, InvalidDataException {
			
		springModel.addAttribute("movies", movieDao.getAllMovies());
		return "removeMovie";
	}
		

	@RequestMapping(value = "/removeMovie", method = RequestMethod.POST)
	public String removeMovie(@RequestParam("movieSelect") int movieId,
		HttpSession session, Model springModel) throws SQLException, InvalidDataException, NotAnAdminException {

		User admin = (User) session.getAttribute("admin");
	
		Movie movieToDelete= movieDao.getMovieById(movieId);
		//should check how to use On delete cascade to many;many relationship so that not use deleteGenresFromMovie()
		adminManager.removeMovie(movieToDelete, admin);
		springModel.addAttribute("message", "Successfully removed a moive!");
		springModel.addAttribute("movies", movieDao.getAllMovies());
		return "removeMovie";
	}
	
	@RequestMapping(value = "/changeBroadcastProjectionTimePage")
	public String getToChangeProjectionTimePage(Model springModel) throws InvalidDataException, SQLException {
		springModel.addAttribute("broadcasts", broadcastDao.getAllBroadcasts());
		return "changeBroadcastProjectionTime";
	}
	
	@RequestMapping(value = "/changeBroadcastProjectionTime", method = RequestMethod.POST)
	public String changeBroadcastProjectionTime(
			@RequestParam("broadcastSelect") int broadcastId,
			@RequestParam("newProjectionTime") String newTime,
			HttpSession session,
			Model springModel) throws SQLException, NotAnAdminException, InvalidDataException {
		
		User admin = (User) session.getAttribute("admin");
		LocalDateTime newProjectionTime = LocalDateTime.parse(newTime);
		adminManager.changeBroadcastProjectionTime(admin, broadcastId, newProjectionTime);
		springModel.addAttribute("message", "Successfully changed a projection time!");
		springModel.addAttribute("broadcasts", broadcastDao.getAllBroadcasts());
		return "changeBroadcastProjectionTime";
	}
}
