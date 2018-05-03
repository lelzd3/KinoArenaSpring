package com.kinoarena.controller.manager;

import java.sql.Connection;
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
	private Validations validator;
	
	@Autowired
	private UserDao userDao;
	
	private static final int MIN_LENGTH_OF_PASSWORD = 8;
	
	private static Connection connection;

	private UserManager() {
		connection = DBManager.getInstance().getConnection();
	}
		
	
	public boolean login(String username, String password) throws SQLException, WrongCredentialsException {
		try {
			userDao.loginCheck(username, password);
			return true;
		} catch (SQLException e) {
			System.out.println("SQLExcp in login " + e.getMessage());
			throw e;
		} catch (WrongCredentialsException e) {
			System.out.println("WrongCrExcp in login " + e.getMessage() );
			throw e;
		}
	}
	
	public void rateMovie(User u, Movie m,int rating) throws SQLException, InvalidDataException {
		userDao.rateMovie(u, m, rating);
	}
	
	
	 public void updateProfileInfo(User user, String oldPassword, String newPassword, String confirmedPassword, String firstName, String lastName, String email) throws SQLException, InvalidDataException {
		 	//getting old data
	        String editedPassword = oldPassword;
	        String editedEmail = user.getEmail();
	       
	        if(oldPassword.length()==0 || oldPassword.isEmpty()) {
	        	throw new InvalidDataException("If you want to make any changes please enter your password first!");
	        }
	        
	        System.out.println(oldPassword);
	        System.out.println(user.getPassword());    
	        //Check if this user is entered correct password
	        if (! BCrypt.checkpw(oldPassword, user.getPassword() )) {
	        	System.out.println(oldPassword);
	        	System.out.println(user.getPassword());
	            throw new InvalidDataException("You have entered wrong old password.");
	        }

	        //Check if a new password is entered correct
	        if (!oldPassword.isEmpty() && !newPassword.isEmpty()) {
	            if (!newPassword.equals(confirmedPassword)) {
	                throw new InvalidDataException("Passwords don't match.");
	            }
	            if (!validator.verifyPassword(newPassword)) {
	                throw new InvalidDataException("Your new password is weak.");
	            }
	            editedPassword = newPassword;
	        }

	        //Check email
	        if (!user.getEmail().equals(email)) {
	                if (!validator.verifyEmail(email)) {
	                    throw new InvalidDataException("Not valid entered email");
	                }
	            
	            editedEmail = email;
	        }

	        userDao.changeFirstName(user, validateName(user.getFirstname(), firstName));
	        userDao.changeLastName(user ,validateName(user.getLastname(), lastName));
	        userDao.changeEmail(user , editedEmail);
	        userDao.changePassword(user ,editedPassword);
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
	            if (!validator.verifyName(name)) {
	                throw new InvalidDataException("Not valid entered name!");
	            }
	            editedName = name;
	        }
	        return editedName;
	    }
	 
}
