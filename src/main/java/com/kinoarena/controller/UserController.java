package com.kinoarena.controller;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	
	private String mailUsername = "kinoArenaSpring@gmail.com";
	private String mailPassword = "kinoarena123";
	
	@RequestMapping(value = "/passwordRecovery", method = RequestMethod.GET)
	public String passwordRecovery() {
		return "passwordRecovery";
	}
	
	@RequestMapping(value = "/passwordRecovery", method = RequestMethod.POST)
	public String passwordRecovery(HttpServletRequest req, HttpServletResponse resp, 
			Model springModel) throws Exception {
		String receiverEmail = req.getParameter("email");
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(mailUsername,mailPassword);
				}
			});

		try {
			User user = userDao.getUserByEmail(receiverEmail);
			String username = user.getEmail();
			String randomPassword = generateRandomPassword();
			
			userDao.setNewPasswod(receiverEmail, randomPassword);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailUsername));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(receiverEmail));
			message.setSubject("Kino Arena email recovery");
			message.setText("Dear "+ username +"," +
					"Your new password has been set to " + randomPassword);
			Transport.send(message);
			return "login";
		} catch (Exception e) {
			e.printStackTrace();
			springModel.addAttribute("error", "Invalid email entered");
			return passwordRecovery();
		}
	}

	private String generateRandomPassword() {
		String upperLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerLetters = upperLetters.toLowerCase();
		String numbers = "0123456789";
		String specialChars = "@#$%^&+=";
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 5; i++) {
			sb.append(upperLetters.charAt(random.nextInt(upperLetters.length())));
			sb.append(lowerLetters.charAt(random.nextInt(lowerLetters.length())));
			sb.append(numbers.charAt(random.nextInt(numbers.length())));
			sb.append(specialChars.charAt(random.nextInt(specialChars.length())));
		}
		return sb.toString();
	}
	
	
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
			springModel.addAttribute("message", e.getMessage());
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
			
		userDao.existingEmailCheck(email);
		userDao.existingUserNameCheck(username);
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
		springModel.addAttribute("message", "Successfull registration");
		return "login";
	}catch(WrongCredentialsException | SQLException | InvalidDataException e) {
		springModel.addAttribute("message", e.getMessage());
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
	public String viewAllMoviesPage(HttpServletRequest request,HttpSession session, Model springModel){
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
