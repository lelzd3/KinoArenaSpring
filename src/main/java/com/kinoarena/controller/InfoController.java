package com.kinoarena.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kinoarena.model.dao.UserDao;
import com.kinoarena.model.pojo.User;

@Controller
public class InfoController {
	
	
	@RequestMapping(value = "/isMovieInFavorite", method = RequestMethod.GET)
	public void infoForProduct(@RequestParam(value = "value") String productId,
			HttpSession session, Model model) {
		
			try {
				User user = (User) session.getAttribute("user");
				if(user != null){
					boolean isMovieInFavourite = UserDao.getInstance().isMovieInFavourite(String.valueOf(user.getId()), productId);
				    model.addAttribute("isMovieInFavourite", isMovieInFavourite);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//TODO for watchlist too
			
			
		}
		
	
	
	

}
