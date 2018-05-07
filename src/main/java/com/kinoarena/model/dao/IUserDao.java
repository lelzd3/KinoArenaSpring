package com.kinoarena.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.kinoarena.model.pojo.Movie;
import com.kinoarena.model.pojo.User;
import com.kinoarena.utilities.exceptions.InvalidDataException;

public interface IUserDao {
	
	public void loginCheck(String username,String password) throws Exception;
	
	public void addUser(User u) throws Exception;
	
	public void deleteUser(User u) throws Exception;
	
	public void addMovieToFavoriteList(User u , Movie m) throws Exception;
	
	public void addMovieToWatchList(User u , Movie m) throws Exception;
	
	public void rateMovie(User u , Movie m, int rating) throws Exception;
	
	public void existingUserNameCheck(String username) throws Exception;
	//TODO add phone number method

	public Collection<User> getAllUsers() throws Exception;
	
	public void addInFavorite(int userId, int productId) throws Exception;
	
	public void removeFavouriteMovie(int userId, int movieId) throws Exception;

	public Collection<User> getAllUsersButNoAdmins() throws Exception;

	public ArrayList<Movie> viewFavourite(User user) throws Exception;

	public boolean isMovieInFavourite(int userId, int movieId) throws Exception;

	public void changePassword(User user, String editedPassword) throws Exception;

	public void changeEmail(User user, String editedEmail) throws Exception;

	public void changeLastName(User user, String validateName) throws Exception;

	public void changeFirstName(User user, String validateName) throws Exception;

	public boolean isMovieInWatchlist(int userId, int movieId) throws Exception;

	public void removeFavouriteWatchlist(int userId, Integer movieId) throws Exception;

	public void addInWatchlist(int userId, Integer movieId) throws Exception;

	public ArrayList<Movie> viewWatchlist(User user) throws Exception;
	
	public void existingEmailCheck(String email) throws InvalidDataException, SQLException;



}
