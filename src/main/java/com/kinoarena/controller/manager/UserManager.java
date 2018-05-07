package com.kinoarena.controller.manager;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kinoarena.model.dao.UserDao;
import com.kinoarena.model.pojo.Movie;
import com.kinoarena.model.pojo.User;
import com.kinoarena.utilities.BCrypt;
import com.kinoarena.utilities.Validations;
import com.kinoarena.utilities.exceptions.InvalidDataException;
import com.kinoarena.utilities.exceptions.WrongCredentialsException;

@Component
public class UserManager {
	
	@Autowired
	private UserDao userDao;
	
	private UserManager() {}
		
	
	public boolean login(String username, String password) throws SQLException, WrongCredentialsException {
		try {
			userDao.loginCheck(username, password);
			return true;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void rateMovie(User u, Movie m,int rating) throws SQLException, InvalidDataException {
		try {
			userDao.rateMovie(u, m, rating);
		} catch(Exception e){
			throw e;
		}
		
	}
	
	
	 public void updateProfileInfo(User user, String oldPassword, String newPassword, String confirmedPassword, String firstName, String lastName, String email) throws SQLException, InvalidDataException {
		 	//getting old data
	        String editedPassword = oldPassword;
	        String editedEmail = user.getEmail();
	       
	        if(oldPassword.length()==0 || oldPassword.isEmpty()) {
	        	throw new InvalidDataException("If you want to make any changes please enter your password first!");
	        }
  
	        //Check if this user is entered correct password
	        if (! BCrypt.checkpw(oldPassword, user.getPassword() )) {
	            throw new InvalidDataException("You have entered wrong old password.");
	        }

	        //Check if a new password is entered correct
	        if (!oldPassword.isEmpty() && !newPassword.isEmpty()) {
	            if (!newPassword.equals(confirmedPassword)) {
	                throw new InvalidDataException("Passwords don't match.");
	            }
	            if (!Validations.verifyPassword(newPassword)) {
	                throw new InvalidDataException("Your new password is weak.");
	            }
	            editedPassword = newPassword;
	        }

	        //Check email
	        if (!user.getEmail().equals(email)) {
	                if (!Validations.verifyEmail(email)) {
	                    throw new InvalidDataException("Not valid entered email");
	                }
	            
	            editedEmail = email;
	        }

	        try {
	        	userDao.changeFirstName(user, validateName(user.getFirstname(), firstName));
		        userDao.changeLastName(user ,validateName(user.getLastname(), lastName));
		        userDao.changeEmail(user , editedEmail);
		        userDao.changePassword(user ,editedPassword);
	        } catch(Exception e){
	        	throw e;
	        }
	        
	 }
	 
	  private String validateName(String oldName, String name) throws InvalidDataException {
		  
	        String editedName = oldName;
	        boolean ifNameIsChanged = false;
	        if (oldName != null) {
	            if (!oldName.equals(name)) {
	                ifNameIsChanged = true;
	            }
	        } else {
	            if (!name.isEmpty()) {
	                ifNameIsChanged = true;
	            }
	        }
	        if (ifNameIsChanged) {
	            if (!Validations.verifyName(name)) {
	                throw new InvalidDataException("Not valid entered name!");
	            }
	            editedName = name;
	        }
	        return editedName;
	    }
	 
}
