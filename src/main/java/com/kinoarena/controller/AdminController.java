package com.kinoarena.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kinoarena.controller.manager.AdminManager;
import com.kinoarena.controller.manager.UserManager;
import com.kinoarena.model.dao.BroadcastDao;
import com.kinoarena.model.dao.HallDao;
import com.kinoarena.model.dao.MovieDao;
import com.kinoarena.model.dao.UserDao;
import com.kinoarena.model.pojo.Broadcast;
import com.kinoarena.model.pojo.Cinema;
import com.kinoarena.model.pojo.Hall;
import com.kinoarena.model.pojo.User;
import com.kinoarena.utilities.exceptions.IlligalAdminActionException;
import com.kinoarena.utilities.exceptions.InvalidDataException;
import com.kinoarena.utilities.exceptions.NotAnAdminException;



@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private HallDao hallDao;
	@Autowired
	private UserManager userManager;
	@Autowired
	private UserDao userDao;
	@Autowired
	private MovieDao movieDao;
	@Autowired
	private BroadcastDao broadcastDao;
	@Autowired
	private AdminManager adminManager;

	@RequestMapping(value = "/removeBroadcastPage", method = RequestMethod.GET)
	public String getToRemoveBroadcast() {
		return "removeBroadcast";
	}
	
	
	@RequestMapping(value = "/removeBroadcast", method = RequestMethod.POST)
	public String removeBroadcast(@RequestParam("broadcastSelect") int broadcastId, HttpSession session) {
		// maybe here should change int to long cuz of id in db
		try {
			User admin = (User) session.getAttribute("admin");
			try {
				Broadcast broadcast = broadcastDao.getBroadcastById(broadcastId);
				adminManager.removeBroadcast(broadcast, admin);
				;

			} catch (NotAnAdminException e) {
				e.printStackTrace();
				// should we check if its right to redirect error page here
				return "error";
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "adminMain";
			}

		} catch (SQLException e) {
			System.out.println("SQL Exception in /admin/confirmed");
			e.printStackTrace();
			return "error";
		}
		return "adminMain";
	}

	@RequestMapping(value = "/addBroadcastPage", method = RequestMethod.GET)
	public String getToAddBroadcast() {
		return "addBroadcast";
	}
	
	
	@RequestMapping(value = "/addBroadcast", method = RequestMethod.POST)
	public String addBroadcast(@RequestParam("movieId") int movieId, @RequestParam("cinemaId") int cinemaId,
			@RequestParam("hallId") int hallId, @RequestParam("projectionTime") String time,
			@RequestParam("freeSits") int freeSits, @RequestParam("price") double price, HttpSession session) {
		// maybe here should change int to long cuz of id in db
		try {
			User admin = (User) session.getAttribute("admin");
			try {
				LocalDateTime projectionTime = LocalDateTime.parse(time);
				Broadcast newBroadcast = new Broadcast(cinemaId, movieId, hallId, projectionTime, price);
				adminManager.addNewBroadcast(newBroadcast, admin);

			} catch (NotAnAdminException e) {
				e.printStackTrace();
				// should we check if its right to redirect error page here
				return "error";
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "error";
			}

		} catch (SQLException e) {
			System.out.println("SQL Exception in /admin/confirmed");
			e.printStackTrace();
			return "error";
		}
		return "adminMain";
	}

	@RequestMapping(value = "/makeAdminPage", method = RequestMethod.GET)
	public String getToMakeAdmin() {
		return "makeAdmin";
	}
	
	
	@RequestMapping(value = "/makeAdmin", method = RequestMethod.POST)
	public String infoAdminCreateAdmin(@RequestParam("usersSelect") String email, HttpSession session, Model model) {

		User user = (User) session.getAttribute("admin");
		if (user != null && user.getIsAdmin()) {
			try {

				if (!userManager.verifyEmail(email)) {
					model.addAttribute("invalidEmail", true);
					return "admin_create_admin";
				}
				adminManager.changeUserIsAdminStatus(user, email.trim());
			} catch (SQLException e) {
				System.out.println("SQLException /createAdmin ");
				e.printStackTrace();
				return "error";
			} catch (InvalidDataException e) {
				e.printStackTrace();
				model.addAttribute("invalidEmail", true);
				return "admin_create_admin";
			} catch (NotAnAdminException e) {
				model.addAttribute("notAdmin", true);
				e.printStackTrace();
				return "error";
			}
		}
		model.addAttribute("create", "Admin is create");
		return "admin_create_admin";
	}
	
	@RequestMapping(value = "/addCinemaPage", method = RequestMethod.GET)
	public String getToAddCinema() {
		return "addCinema";
	}
	

	@RequestMapping(value = "/addCinema", method = RequestMethod.POST)
	public String addCinema(@RequestParam("address") String address, @RequestParam("name") String name,
			HttpSession session, Model model) {

		User admin = (User) session.getAttribute("admin");
		try {
			Cinema newCinema = new Cinema(name, address);
			adminManager.addNewCinema(newCinema, admin);

		} catch (SQLException e) {
			System.out.println("SQL Exception in /admin/confirmed");
			e.printStackTrace();
			return "error";
		} catch (NotAnAdminException e) {
			return "error";
		} catch (InvalidDataException e) {
			e.printStackTrace();
			return "error";
		}
		return "adminMain";
    }
	
	@RequestMapping(value = "/addHallPage", method = RequestMethod.GET)
	public String getToAddHall() {
		return "addHall";
	}
	
	
	@RequestMapping(value = "/addHall", method = RequestMethod.POST)
	public String addHall(@RequestParam("cinemaSelect") int cinemaId , @RequestParam("seats") int seats,
			HttpSession session, Model model) {

		User admin = (User) session.getAttribute("admin");
		try {
			Hall newHall = new Hall(seats, cinemaId);
			adminManager.addNewHall(newHall, admin);
			
		} catch (SQLException e) {
			System.out.println("SQL Exception in /admin/confirmed");
			e.printStackTrace();
			return "error";
		} catch (NotAnAdminException e) {
			return "error";
		} catch (InvalidDataException e) {
			e.printStackTrace();
			return "error";
		}
		return "adminMain";
	}

	@RequestMapping(value = "/setDiscountPage", method = RequestMethod.GET)
	public String getToSetDiscount() {
		return "setDiscount";
	}
	
	
	@RequestMapping(value = "/setDiscount", method = RequestMethod.POST)
	public String setDiscount(@RequestParam("broadcastSelect") int broadcastId, @RequestParam("percent") double percentForDiscount,
			HttpSession session) {
		
		try {
			User admin = (User) session.getAttribute("admin");
			try {
				Broadcast broadcastForDiscount = BroadcastDao.getInstance().getBroadcastById(broadcastId);
				adminManager.setPromoPercent(admin , broadcastForDiscount, percentForDiscount);

			} catch (NotAnAdminException e) {
				e.printStackTrace();
				// should we check if its right to redirect error page here
				return "error";
			} catch (InvalidDataException e) {
				e.printStackTrace();
				return "adminMain";
			}

		} catch (SQLException e) {
			System.out.println("SQL Exception in /admin/confirmed");
			e.printStackTrace();
			return "error";
		}
		return "adminMain";
	}

	@RequestMapping(value = "/removeHallPage", method = RequestMethod.GET)
	public String getToRemoveHall() {
		return "removeHall";
	}
	
	
	@RequestMapping(value = "/removeHall", method = RequestMethod.POST)
	public String addHall(@RequestParam("hallSelect") int hallId ,
			HttpSession session) {

		User admin = (User) session.getAttribute("admin");
		try {
			Hall hallToDelete = hallDao.getHallById(hallId);
			adminManager.removeHall(hallToDelete, admin);
			
		} catch (SQLException e) {
			System.out.println("SQL Exception in /admin/confirmed");
			e.printStackTrace();
			return "error";
		} catch (NotAnAdminException e) {
			return "error";
		} catch (InvalidDataException e) {
			e.printStackTrace();
			return "error";
		}
		return "adminMain";
	}
	

}
