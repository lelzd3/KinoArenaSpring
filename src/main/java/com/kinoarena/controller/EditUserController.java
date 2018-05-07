package com.kinoarena.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kinoarena.controller.manager.UserManager;
import com.kinoarena.model.pojo.User;
import com.kinoarena.utilities.exceptions.InvalidDataException;

@Controller
public class EditUserController {

	@Autowired
	private UserManager userManager;
	
	//adminPanel.jsp -> addBroadcast.jsp
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public String getToEditPage(Model springModel,HttpSession session,HttpServletRequest request) {
			User user = (User) session.getAttribute("user");		
			springModel.addAttribute("user", user);
			return "editProfile";
		}
			
		

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editProfile(@RequestParam(required=false,name="oldPassword") String oldPassword,
			@RequestParam(required=false,name="newPassword") String newPassword,
			@RequestParam(required=false,name="confirmPassword") String confirmPassword,
			@RequestParam(required=false,name="firstName") String firstName, 
			@RequestParam(required=false,name="lastName") String lastName,
			@RequestParam(required=false,name="email") String email,
			HttpSession session, Model springModel) throws SQLException {

		User user = (User) session.getAttribute("user");
		try {
			userManager.updateProfileInfo(user, oldPassword, newPassword, confirmPassword, firstName, lastName, email);
		} catch (InvalidDataException e) {
			springModel.addAttribute("error", e.getMessage());
			e.printStackTrace();
			return "editProfile";
		}

		return "main";
	}
	
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public String goToMainPage() {
		return "main";
	}

}
