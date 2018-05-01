package com.kinoarena.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import com.kinoarena.model.dao.UserDao;
import com.kinoarena.model.pojo.Movie;
import com.kinoarena.model.pojo.User;
import com.kinoarena.utilities.exceptions.InvalidDataException;

@Controller
public class UserFavoriteController {
	
//	@Autowired
//	private UserDAO userDAO;
//	
	
	@RequestMapping(value = "/viewUserFavourites", method = RequestMethod.GET)
	public String viewUserFavourites(HttpSession session,
			Model model,HttpServletRequest request){
		User user = (User) session.getAttribute("user");
		try {
			ArrayList<Movie> movie = UserDao.getInstance().viewFavourite(user);
		} catch (SQLException e) {
			request.setAttribute("exception", e);
			return "error";
		} catch (InvalidDataException e) {
			request.setAttribute("exception", e);
			return "error";
		}
		return "user_favourites";
	}
	
	@RequestMapping(value = "/addInFavorite", method = RequestMethod.GET)
	public String addInFavorite(@RequestParam("hiddenMovieId") Integer movieId,
			Model model,	HttpSession session,HttpServletRequest request){
		try {
			User user = (User) session.getAttribute("user");
			
			UserDao.getInstance().addInFavorite(user.getId(), movieId);
			
			return "viewAllMovies";
		} catch (SQLException e) {
			request.setAttribute("exception", e);
			return "error";
		}
	}
	
	@RequestMapping(value = "/removeFromFavorite", method = RequestMethod.POST)
	public String removeFromFavorite(@RequestParam("hiddenMovieId") Integer movieId,
			Model model,
			HttpSession session,HttpServletRequest request){

		try {
			
			User user = (User) session.getAttribute("user");
     		UserDao.getInstance().removeFavouriteMovie(user.getId(), movieId);

			return "viewAllMovies";
		} catch (SQLException e) {
			request.setAttribute("exception", e);
			return "error";
		}
	}
	

	
	@RequestMapping(value = "/removeFromFavorite2", method = RequestMethod.POST)
	public String removeFromFavorite2(@RequestParam("hiddenMovieId") Integer movieId,
			Model model,
			HttpSession session,HttpServletRequest request){

		try {
			
			User user = (User) session.getAttribute("user");
     		UserDao.getInstance().removeFavouriteMovie(user.getId(), movieId);

			return "user_favourites";
		} catch (SQLException e) {
			request.setAttribute("exception", e);
			return "error";
		}
	}

	
	@RequestMapping(value = "/addInFavorite3", method = RequestMethod.GET)
	public String addInFavorite3(@RequestParam("hiddenMovieId") Integer movieId,
			Model model,	HttpSession session,HttpServletRequest request){
		try {
			User user = (User) session.getAttribute("user");
			
			UserDao.getInstance().addInFavorite(user.getId(), movieId);
			
			return "viewAMovie";
		} catch (SQLException e) {
			request.setAttribute("exception", e);
			return "error";
		}
	}
	
	@RequestMapping(value = "/removeFromFavorite3", method = RequestMethod.POST)
	public String removeFromFavorite3(@RequestParam("hiddenMovieId") Integer movieId,
			Model model,
			HttpSession session,HttpServletRequest request){

		try {
			
			User user = (User) session.getAttribute("user");
     		UserDao.getInstance().removeFavouriteMovie(user.getId(), movieId);

			return "viewAMovie";
		} catch (SQLException e) {
			request.setAttribute("exception", e);
			return "error";
		}
	}
}
