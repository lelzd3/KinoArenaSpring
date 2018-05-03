package com.kinoarena.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kinoarena.model.dao.BroadcastDao;
import com.kinoarena.model.dao.CinemaDao;
import com.kinoarena.model.dao.HallDao;
import com.kinoarena.model.dao.MovieDao;
import com.kinoarena.model.dao.ReservationDao;
import com.kinoarena.model.dao.UserDao;
import com.kinoarena.model.pojo.Movie;
import com.kinoarena.model.pojo.User;
import com.kinoarena.utilities.exceptions.InvalidDataException;

@Controller
public class UserWatchlistController {
	
	@Autowired 
	private UserDao userDao;
	
	@RequestMapping(value = "/viewUserWatchlist", method = RequestMethod.GET)
	public String infoUserFavourites(HttpSession session,
			Model model,HttpServletRequest request){
		User user = (User) session.getAttribute("user");
		try {
			ArrayList<Movie> movie = userDao.viewWatchlist(user);
		} catch (SQLException e) {
			request.setAttribute("exception", e);
			return "error";
		} catch (InvalidDataException e) {
			request.setAttribute("exception", e);
			return "error";
		}
		return "user_watchlist";
	}
	
	@RequestMapping(value = "/addInWatchlist", method = RequestMethod.POST)
	public String addInFavorite(@RequestParam("hiddenMovieId") Integer movieId,
			Model model,	HttpSession session,HttpServletRequest request){
		try {
			User user = (User) session.getAttribute("user");
			
			String jspName = request.getParameter("hiddenJspName");
			
			userDao.addInWatchlist(user.getId(), movieId);
			
			return jspName;
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
			
			String jspName = request.getParameter("hiddenJspName");
			
     		userDao.removeFavouriteWatchlist(user.getId(), movieId);

			return jspName;
		} catch (SQLException e) {
			request.setAttribute("exception", e);
			return "error";
		}
	}
	

}
