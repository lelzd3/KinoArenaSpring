package com.kinoarena.controller;


import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kinoarena.model.dao.BroadcastDao;
import com.kinoarena.model.dao.CinemaDao;
import com.kinoarena.model.dao.HallDao;
import com.kinoarena.model.dao.MovieDao;
import com.kinoarena.model.dao.ReservationDao;
import com.kinoarena.model.dao.UserDao;
import com.kinoarena.model.pojo.Movie;
import com.kinoarena.model.pojo.Reservation;
import com.kinoarena.model.pojo.User;
import com.kinoarena.utilities.exceptions.InvalidDataException;
import com.kinoarena.utilities.exceptions.WrongCredentialsException;


@Controller
public class UserController {
	
	@Autowired
	private MovieDao movieDao;
	
	@Autowired	
	private BroadcastDao broadcastDao;
	
	@Autowired
	private HallDao hallDao;
	
	@Autowired
	private CinemaDao cinemaDao;
	
	@Autowired
	private ReservationDao reservationDao;
	
	@Autowired 
	private UserDao userDao; // change later all UserDao to UserManager (add methods in manager)
	
	
	@RequestMapping(value="/index.html", method=RequestMethod.GET)
	public String sendIndex() {
		return "index2";
	}
	
	//from index,error and register.jsp -> login.jsp
	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public String getLoginPage(){
		return "login";
	}
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(HttpServletRequest request,HttpSession session, Model springModel) {
		session.setAttribute("movieDao",movieDao);
		session.setAttribute("userDao",userDao);
		session.setAttribute("broadcastDao",broadcastDao);
		session.setAttribute("reservationDao", reservationDao);
		session.setAttribute("cinemaDao", cinemaDao);
		session.setAttribute("hallDao", hallDao);
		
		String username = request.getParameter("username");
		String password = request.getParameter("password"); 
		try {
		userDao.loginCheck(username, password);
		User user = userDao.getUser(username);

		if(user.getIsAdmin()) {

			session.setAttribute("admin", user);
			session.setAttribute("user", user);
			return "testMain";
		}
		else {
			session.setAttribute("user", user);
			return "main";
		}
		
		
		
		}catch(WrongCredentialsException | SQLException | InvalidDataException e) {
			springModel.addAttribute("error", e.getMessage());
			return "login";
		}
		
	}
	@RequestMapping(value = "/register", method = RequestMethod.POST )
	public String register(HttpServletRequest request,HttpSession s, Model springModel){
	try {
		String username = request.getParameter("username");
		String pass1 = request.getParameter("password1");
		String pass2 = request.getParameter("password2");
		String email = request.getParameter("email");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		int age = Integer.valueOf(request.getParameter("age"));
		System.out.println(age);
			
		if (username.isEmpty() || username.length() < 5) {
			throw new WrongCredentialsException("Username must be at least 5 chars long");
		}
		if (!pass1.equals(pass2)) {
			throw new WrongCredentialsException("Passwords missmatch");
		}
		if (!email.contains("@") || email.isEmpty()) {
			throw new WrongCredentialsException("Invalid entered email");
		}
		if (firstName.isEmpty()) {
			throw new WrongCredentialsException("Invalid entered first name");
		}
		if (lastName.isEmpty()) {
			throw new WrongCredentialsException("Invalid entered last name");
		}
		// creating new user with these details
		User user = new User(age , username, pass1, firstName, lastName, email);
		// adding to db
		userDao.addUser(user);
		
		s.setAttribute("user", user);
		return "login";
	}catch(WrongCredentialsException | SQLException | InvalidDataException e) {
		springModel.addAttribute("error", e.getMessage());
		return "register";
	}
	
	}
	
	
	//logout
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request,HttpSession s){
		s.invalidate();
		return "login";
	}
	
	//main.jsp -> viewAllMovies.jsp
	@RequestMapping(value = "/viewAllMoviesPage", method = RequestMethod.GET)
	public String viewAllMoviesPage(HttpServletRequest request,HttpSession session){
		return "viewAllMovies";
	}
	
	
	//login.jsp -> register.jsp
	@RequestMapping(value = "/getRegisterPage", method = RequestMethod.GET)
	public String getRegisterPage(){
		return "register";
	}
	
	
	//main.jsp -> viewAllReseravtions.jsp
	//this method is not used?
	@RequestMapping(value = "/reservationsPage", method = RequestMethod.GET)
	public String viewAllReservations(){
		return "viewAllReservations";
	}

	@RequestMapping(value = "/reservations", method = RequestMethod.GET)
	public String viewUserReservations(HttpSession session,Model model,HttpServletRequest request) throws SQLException{
		
		User user = (User) session.getAttribute("user");

		ArrayList<String> reservations = reservationDao.getAllReservationsForUser(user);
		model.addAttribute("reservations", reservations);
		return "viewAllReservations";

	}
	
	@RequestMapping(value = "/reservations", method = RequestMethod.POST)
	public String cancelReservation(HttpSession session, HttpServletRequest request) throws SQLException{
		User user = (User) session.getAttribute("user");
	
		String reservationSelected = request.getParameter("selectedReservation");
		reservationDao.deleteReservation(reservationSelected);
		return "viewAllReservations";
	
	}
	
	

}
