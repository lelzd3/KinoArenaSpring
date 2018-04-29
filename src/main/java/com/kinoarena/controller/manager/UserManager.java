package com.kinoarena.controller.manager;

import java.sql.Connection;
import java.sql.SQLException;


import com.kinoarena.model.dao.UserDao;
import com.kinoarena.model.pojo.Movie;
import com.kinoarena.model.pojo.User;
import com.kinoarena.utilities.exceptions.InvalidDataException;
import com.kinoarena.utilities.exceptions.WrongCredentialsException;

@SuppressWarnings("unused")
public class UserManager {
	
	
	private static final int MIN_LENGTH_OF_PASSWORD = 8;
	//Singleton and DBconnection
	private static Connection connection;
	private static UserManager instance;

	public synchronized static  UserManager getInstance() {
		if (instance == null) {
			instance = new UserManager();
		}
		return instance;
	}

	private UserManager() {
		connection = DBManager.getInstance().getConnection();
	}
		
	
	public boolean login(String username, String password) throws SQLException, WrongCredentialsException {
		try {
			UserDao.getInstance().loginCheck(username, password);
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
		UserDao.getInstance().rateMovie(u, m, rating);
	}
	

	public static boolean verifyEmail(String email) throws InvalidDataException {
	    if(!validation(email)) {
	    	 throw new InvalidDataException("Invalid e-mail");
	    }
	    
	    if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
	    	 throw new InvalidDataException("Invalid e-mail");
	    }   
	    return true;
	}
	
	public static boolean validation(String text)  throws InvalidDataException {
		if( text != null && !text.trim().isEmpty()){
			return true;
		}
		else {
			throw new InvalidDataException("Invalid User name or password");
		}
	}
	

}
