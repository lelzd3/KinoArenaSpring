package com.kinoarena.model.dao;

import java.util.ArrayList;

import com.kinoarena.model.pojo.Movie;

public interface IMovieDao {

	public void addMovie(Movie m) throws Exception;

	public void deleteMovie(Movie m) throws Exception;

	public ArrayList<Movie> getAllMovies() throws Exception;

	public Movie getMovieById(int id) throws Exception;
	
	public ArrayList<String> getMoviesContains(String term) throws Exception;
	
	public ArrayList<String> getAllMoviesNames() throws Exception;
	
	public Movie getMovieByName(String name) throws Exception;
	
	public void updateFileLocaiton(Movie movie,String file_location) throws Exception;
	
	public double getMovieRatingById(int movieIdToBeRated) throws Exception;

	public ArrayList<Integer> getAllFavouriteMovieIdsForUser(int userId) throws Exception;
	
	public ArrayList<Integer> getAllWatchlistMovieIdsForUser(int userId) throws Exception;
}
