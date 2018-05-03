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

	//remove it later with the context in login
	@Autowired
	ServletContext context;
	
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
	public String getToRemoveBroadcast(Model springModel,HttpServletRequest request) {
		try {
			springModel.addAttribute("broadcasts", broadcastDao.getAllBroadcasts());
			return "removeBroadcast";
		} catch (InvalidDataException | SQLException e) {
			request.setAttribute("exception", e);
			return "error";
		}
		
	}
	
	
	@RequestMapping(value = "/removeBroadcast", method = RequestMethod.POST)
	public String removeBroadcast(@RequestParam("broadcastSelect") int broadcastId, HttpSession session,HttpServletRequest request) {
		try {
			User admin = (User) session.getAttribute("admin");
			try {
				Broadcast broadcast = broadcastDao.getBroadcastById(broadcastId);
				adminManager.removeBroadcast(broadcast, admin);
			} catch (NotAnAdminException e) {
				request.setAttribute("exception", e);
				return "error";
			} catch (InvalidDataException e) {
				request.setAttribute("exception", e);
				return "error";
			}

		} catch (SQLException e) {
			request.setAttribute("exception", e);
			return "error";
		}
		return "adminMain";
	}

	//adminPanel.jsp -> addBroadcast.jsp
	@RequestMapping(value = "/addBroadcastPage", method = RequestMethod.GET)
	public String getToAddBroadcast(Model springModel,HttpSession session,HttpServletRequest request) {
		
		try {
			User admin = (User) session.getAttribute("admin");

			springModel.addAttribute("movies", movieDao.getAllMovies());
			springModel.addAttribute("cinemas", cinemaDao.getAllCinemas());
			springModel.addAttribute("halls", hallDao.getAllHalls());
			
			return "addBroadcast";
		}catch (SQLException e) {
			request.setAttribute("exception", e);
			return "error";
		} catch (InvalidDataException e) {
			request.setAttribute("exception", e);
			return "error";
		}
		
	}
	
	@RequestMapping(value = "/addBroadcast", method = RequestMethod.POST)
	public String addBroadcast(
					@RequestParam("movieSelect") int movieId,
					@RequestParam("cinemaSelect") int cinemaId,
					@RequestParam("hallSelect") int hallId,
					@RequestParam("projection_time") String time,
					@RequestParam("free_sits") int freeSits,
					@RequestParam("price") double price, HttpSession session, HttpServletRequest request) {
		
		try {
			User admin = (User) session.getAttribute("admin");
			try {
				LocalDateTime projectionTime = LocalDateTime.parse(time);
				Broadcast newBroadcast = new Broadcast(cinemaId, movieId, hallId, projectionTime, price);
				adminManager.addNewBroadcast(newBroadcast, admin);

			} catch (NotAnAdminException e) {
				request.setAttribute("exception", e);
				return "error";
			} catch (InvalidDataException e) {
				request.setAttribute("exception", e);
				return "error";
			}

		} catch (SQLException e) {
			System.out.println("SQL Exception in /admin/confirmed");
			e.printStackTrace();
			return "error";
		}
		return "adminMain";
	}

	//adminPanel.jsp -> makeAdmin.jsp
	@RequestMapping(value = "/makeAdminPage", method = RequestMethod.GET)
	public String getToMakeAdmin(Model springModel,HttpServletRequest request) {

		try {
			springModel.addAttribute("usersThatAreNotAdmin", userDao.GetAllUsersButNoAdmins());
			return "makeAdmin";
		} catch (SQLException e) {
			request.setAttribute("exception", e);
			return "error";
		} catch (InvalidDataException e) {
			request.setAttribute("exception", e);
			return "error";
		}
	
	}
	
	
	@RequestMapping(value = "/makeAdmin", method = RequestMethod.POST)
	public String infoAdminCreateAdmin(@RequestParam("usersSelect") String email, HttpSession session, Model model,HttpServletRequest request) {

		User user = (User) session.getAttribute("admin");
		if (user != null && user.getIsAdmin()) {
			try {

				Validations.verifyEmail(email);
				adminManager.changeUserIsAdminStatus(user, email.trim());
			} catch (SQLException e) {
				request.setAttribute("exception", e);
				return "error";
			} catch (InvalidDataException e) {
				request.setAttribute("exception", e);
				return "error";
			} catch (NotAnAdminException e) {
				request.setAttribute("exception", e);
				return "error";
			}
		}
		model.addAttribute("create", "Admin is create");
		return "adminMain";
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
			HttpSession session, Model model, HttpServletRequest request) {

		User admin = (User) session.getAttribute("admin");
		try {
			Cinema newCinema = new Cinema(name, address);
			adminManager.addNewCinema(newCinema, admin);

		} catch (SQLException e) {
			request.setAttribute("exception", e);
			return "error";
		} catch (NotAnAdminException e) {
			request.setAttribute("exception", e);
			return "error";
		} catch (InvalidDataException e) {
			request.setAttribute("exception", e);
			return "error";
		}
		return "adminMain";
    }
	// adminMain.jsp -> removeCinema.jsp
	@RequestMapping(value = "/removeCinemaPage", method = RequestMethod.GET)
	public String getToRemoveCinema(Model springModel,HttpServletRequest request) {
		try {
			springModel.addAttribute("cinemas", cinemaDao.getAllCinemas());
			return "removeCinema";
		} catch (SQLException e) {
			request.setAttribute("exception", e);
			return "error";
		} catch (InvalidDataException e) {
			request.setAttribute("exception", e);
			return "error";
		}

	}
	
	@RequestMapping(value = "/removeCinema",method = RequestMethod.POST)
	public String removeCinema(
			@RequestParam("cinemaSelect") Integer cinemaId,
			HttpSession session,HttpServletRequest request) {
		try {
			User admin = (User) session.getAttribute("admin");
			Cinema cinemaToDelete = cinemaDao.getCinemaById(cinemaId);

			adminManager.removeCinema(cinemaToDelete,admin);
			
			return "adminMain";
		} catch (Exception e) {
			request.setAttribute("exception", e);
			return "error";
		}
	}

	//adminPanel.jsp->addHall.jsp
	@RequestMapping(value = "/addHallPage", method = RequestMethod.GET)
	public String getToAddHall() {
		return "addHall";
	}
	
	
	@RequestMapping(value = "/addHall", method = RequestMethod.POST)
	public String addHall(
			@RequestParam("cinemaSelect") int cinemaId ,
			@RequestParam("seats") int seats,
			HttpSession session,HttpServletRequest request, Model springModel) {

		User admin = (User) session.getAttribute("admin");
		try {
			Hall newHall = new Hall(seats, cinemaId);
			springModel.addAttribute("cinemas", cinemaDao.getAllCinemas());
			adminManager.addNewHall(newHall, admin);
			
		} catch (SQLException e) {
			request.setAttribute("exception", e);
			return "error";
		} catch (NotAnAdminException e) {
			request.setAttribute("exception", e);
			return "error";
		} catch (InvalidDataException e) {
			request.setAttribute("exception", e);
			return "error";
		}
		return "adminMain";
	}

	//adminPanel.jsp -> setDiscount.jsp	
	@RequestMapping(value = "/setDiscountPage", method = RequestMethod.GET)
	public String getToSetDiscount() {
		return "setDiscount";
	}
	
	
	@RequestMapping(value = "/setDiscount", method = RequestMethod.POST)
	public String setDiscount(
			@RequestParam("broadcastSelect") int broadcastId,
			@RequestParam("percent") double percentForDiscount,
			HttpSession session,HttpServletRequest request) {
		
		try {
			User admin = (User) session.getAttribute("admin");
			try {
				Broadcast broadcastForDiscount = broadcastDao.getBroadcastById(broadcastId);
				adminManager.setPromoPercent(admin , broadcastForDiscount, percentForDiscount);

			} catch (NotAnAdminException e) {
				request.setAttribute("exception", e);
				return "error";
			} catch (InvalidDataException e) {
				request.setAttribute("exception", e);
				return "error";
			}

		} catch (SQLException e) {
			request.setAttribute("exception", e);
			return "error";
		}
		return "adminMain";
	}

	//adminPanel.jsp -> removeHall.jsp
	@RequestMapping(value = "/removeHallPage", method = RequestMethod.GET)
	public String getToRemoveHall(Model springModel, HttpServletRequest request) {
		try {
			springModel.addAttribute("halls",hallDao.getAllHalls());
			return "removeHall";
		} catch (SQLException | InvalidDataException e) {
			request.setAttribute("exception", e);
			return "error";
		}
	}
	
	
	@RequestMapping(value = "/removeHall", method = RequestMethod.POST)
	public String addHall(
			@RequestParam("hallSelect") int hallId ,
			HttpSession session,HttpServletRequest request) {

		User admin = (User) session.getAttribute("admin");
		try {
			Hall hallToDelete = hallDao.getHallById(hallId);
			adminManager.removeHall(hallToDelete, admin);
			
		} catch (SQLException e) {
			request.setAttribute("exception", e);
			return "error";
		} catch (NotAnAdminException e) {
			request.setAttribute("exception", e);
			return "error";
		} catch (InvalidDataException e) {
			request.setAttribute("exception", e);
			return "error";
		}
		return "adminMain";
	}

	//adminPanel.jsp -> addMovie.jsp
	@RequestMapping(value = "/addMoviePage", method = RequestMethod.GET)
	public String getToAddMovie() {
		return "addMovie";
	}
	

	@RequestMapping(value = "/addMovie", method = RequestMethod.POST)
	public String addMovie(
			@RequestParam("title") String title,
			@RequestParam("description") String description,
			@RequestParam("duration") double duration,
			@RequestParam("file") MultipartFile uploadedFile,
			HttpSession session, Model model,HttpServletRequest request) {
	

		User admin = (User) session.getAttribute("admin");
		try {
	
			Double rating = 0.0;
			
			Movie movie = new Movie(title, description, rating, duration);
			adminManager.addNewMovie(movie, admin);
			
			String file_location = SERVER_FILES_LOCATION+movie.getTitle()+";"+movie.getId()+COVER_FILE_SUFFIX;
			movie.setFile_location(file_location);
			
			movieDao.updateFileLocaiton(movie,file_location);	
			
			File serverFile = new File(file_location);
			Files.copy(uploadedFile.getInputStream(), serverFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

			return "adminMain";
		
		} catch (SQLException e) {
			request.setAttribute("exception", e);
			return "error";
		} catch (NotAnAdminException e) {
			request.setAttribute("exception", e);
			return "error";
		} catch (InvalidDataException e) {
			request.setAttribute("exception", e);
			return "error";
		} catch (IOException e) {
			request.setAttribute("exception", e);
			return "error";
		}

    }

	//adminPanel.jsp -> removeMovie.jsp
		@RequestMapping(value = "/removeMoviePage", method = RequestMethod.GET)
		public String getToRemoveMovie(Model springModel,HttpServletRequest request) {
			try {
				springModel.addAttribute("movies", movieDao.getAllMovies());
				return "removeMovie";
			} catch (SQLException e) {
				request.setAttribute("exception", e);
				return "error";
			} catch (InvalidDataException e) {
				request.setAttribute("exception", e);
				return "error";
			}
		}
		

		@RequestMapping(value = "/removeMovie", method = RequestMethod.POST)
		public String removeMovie(@RequestParam("movieSelect") int movieId,
			HttpSession session, Model model,HttpServletRequest request) {

			User admin = (User) session.getAttribute("admin");
			try {
			    Movie movieToDelete= movieDao.getMovieById(movieId);
				adminManager.removeMovie(movieToDelete, admin);
				
			} catch (SQLException e) {
				request.setAttribute("exception", e);
				return "error";
			} catch (NotAnAdminException e) {
				request.setAttribute("exception", e);
				return "error";
			} catch (InvalidDataException e) {
				request.setAttribute("exception", e);
				return "error";
			}
			return "adminMain";
	    }
	
		
}
