package com.kinoarena.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

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
public class UserFavoriteController {
	
//	@Autowired
//	private UserDAO userDAO;
//	
	
	@RequestMapping(value = "/infoUserFavourites", method = RequestMethod.GET)
	public String infoUserFavourites(HttpSession session,
			Model model){
		User user = (User) session.getAttribute("user");
		try {
			ArrayList<Movie> movie = UserDao.getInstance().viewFavourite(user);
			model.addAttribute("movie", movie);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL Exception in /info/infoUserFavourites");
			return "error";
		} catch (InvalidDataException e) {
			e.printStackTrace();
			System.out.println("InvalidData exception");
			return "error";
		}
		return "user_favourites";
	}
	
	@RequestMapping(value = "/addInFavorite", method = RequestMethod.GET)
	public String addInFavorite(@RequestParam("value") int movieId,
			Model model,	HttpSession session){
		if(session.getAttribute("user") == null){
			return "login";
		}
		User user = (User) session.getAttribute("user");
		try {
			UserDao.getInstance().addInFavorite(user.getId(), movieId);
		} catch (SQLException e) {
			System.out.println("SQL Exception in /info/addInFavorite");
			e.printStackTrace();
			return "error";
		}
		return "forward:/favourite/infoUserFavourites";
	}
	
	@RequestMapping(value = "/removeFromFavorite", method = RequestMethod.POST)
	public String removeFromFavorite(@RequestParam("value") int movieId,
			Model model,
			HttpSession session){
		
		User user = (User) session.getAttribute("user");
		try {
     		UserDao.getInstance().removeFavouriteProduct(user.getId(), movieId);
     		ArrayList<Movie> movie = UserDao.getInstance().viewFavourite(user);
			model.addAttribute("movie", movie);
		} catch (SQLException e) {
			System.out.println("SQL Exception in info/removeFromFavorite");
			e.printStackTrace();
			return "errorPage";
		} catch (InvalidDataException e) {
			e.printStackTrace();
			System.out.println("InvalidData exception");
			return "error";
			
		}
		return "user_favourites";
	}

}
