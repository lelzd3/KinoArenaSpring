package com.kinoarena.controller.manager;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kinoarena.model.dao.UserDao;
import com.kinoarena.model.pojo.Movie;
import com.kinoarena.model.pojo.User;
import com.kinoarena.utilities.exceptions.InvalidDataException;
import com.kinoarena.utilities.exceptions.WrongCredentialsException;

@Component
public class UserManager {
	
	
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

}
