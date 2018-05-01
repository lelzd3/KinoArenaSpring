package com.kinoarena.model.dao;

import java.sql.SQLException;
import java.util.Collection;

import com.kinoarena.model.pojo.Movie;
import com.kinoarena.model.pojo.User;
import com.kinoarena.utilities.exceptions.WrongCredentialsException;

public interface IUserDao {
	
	public void loginCheck(String username,String password) throws WrongCredentialsException, SQLException;
	
	public void addUser(User u) throws Exception;
	
	public void deleteUser(User u) throws Exception;
	
	public void addMovieToFavoriteList(User u , Movie m) throws Exception;
	
	public void addMovieToWatchList(User u , Movie m) throws Exception;
	
	public void rateMovie(User u , Movie m, int rating) throws Exception;
	
	public void existingUserNameCheck(String username) throws Exception;
	//TODO add phone number method

	public Collection<User> getAllUsers() throws Exception;
	
	public void addInFavorite(int userId, int productId) throws SQLException;
	
	public void removeFavouriteProduct(int userId, int  productId) throws SQLException;



}
