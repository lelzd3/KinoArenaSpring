package com.kinoarena.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kinoarena.model.dao.UserDao;
import com.kinoarena.model.pojo.Movie;
import com.kinoarena.model.pojo.User;
import com.kinoarena.utilities.exceptions.InvalidDataException;

@Controller
public class UserWatchlistController {
	
	@RequestMapping(value = "/viewUserWatchlist", method = RequestMethod.GET)
	public String infoUserFavourites(HttpSession session,
			Model model,HttpServletRequest request){
		User user = (User) session.getAttribute("user");
		try {
			ArrayList<Movie> movie = UserDao.getInstance().viewWatchlist(user);
		} catch (SQLException e) {
			request.setAttribute("exception", e);
			return "error";
		} catch (InvalidDataException e) {
			request.setAttribute("exception", e);
			return "error";
		}
		return "user_watchlist";
	}
	
	@RequestMapping(value = "/addInWatchlist", method = RequestMethod.GET)
	public String addInFavorite(@RequestParam("hiddenMovieId") Integer movieId,
			Model model,	HttpSession session,HttpServletRequest request){
		try {
			User user = (User) session.getAttribute("user");
			
			UserDao.getInstance().addInWatchlist(user.getId(), movieId);
			
			return "viewAllMovies";
		} catch (SQLException e) {
			request.setAttribute("exception", e);
			return "error";
		}
	}
	
	@RequestMapping(value = "/removeFromWatchlist", method = RequestMethod.POST)
	public String removeFromFavorite(@RequestParam("hiddenMovieId") Integer movieId,
			Model model,
			HttpSession session,HttpServletRequest request){

		try {
			
			User user = (User) session.getAttribute("user");
     		UserDao.getInstance().removeFavouriteWatchlist(user.getId(), movieId);

			return "viewAllMovies";
		} catch (SQLException e) {
			request.setAttribute("exception", e);
			return "error";
		}
	}
	
	@RequestMapping(value = "/removeFromWatchlist2", method = RequestMethod.POST)
	public String removeFromFavorite2(@RequestParam("hiddenMovieId") Integer movieId,
			Model model,
			HttpSession session,HttpServletRequest request){

		try {
			
			User user = (User) session.getAttribute("user");
     		UserDao.getInstance().removeFavouriteWatchlist(user.getId(), movieId);

			return "viewAllMovies";
		} catch (SQLException e) {
			request.setAttribute("exception", e);
			return "user_watchlist";
		}
	}

}
