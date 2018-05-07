package com.kinoarena.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.kinoarena.controller.manager.DBManager;
import com.kinoarena.model.pojo.Movie;
import com.kinoarena.utilities.exceptions.InvalidDataException;

@Component
public class MovieDao implements IMovieDao{

	private Connection connection;

	private MovieDao() {
		connection = DBManager.getInstance().getConnection();
	}
	
	@Override
	public void addMovie(Movie m, ArrayList<String> genres) throws SQLException {
		PreparedStatement s = null;
		try {
		connection.setAutoCommit(false);
		s = connection.prepareStatement("INSERT INTO movies (title, description,rating,duration,file_location) VALUES (?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
		s.setString(1,m.getTitle());
		s.setString(2, m.getDescription());
		s.setDouble(3, m.getRating());
		s.setDouble(4, m.getDuration());
		s.setString(5, m.getFile_location());
		s.executeUpdate();
		
		// set the ID for the instance of Movie m
		ResultSet result = s.getGeneratedKeys();
		result.next(); // we write next cuz it starts from -1
		m.setId((int)result.getLong(1)); // or 1 instead of id
		
		addGenresForAMovie(m.getId(), genres);
		
		connection.commit();
		}
		catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			s.close();
			connection.setAutoCommit(true);
		}
		
		
	}
	
	@Override
	public void deleteMovie(Movie m) throws SQLException {
		PreparedStatement s = connection.prepareStatement("DELETE FROM movies WHERE id = ?");
		s.setInt(1, m.getId());
		s.executeUpdate();
		s.close();
	}
	
	@Override
	public ArrayList<Movie> getAllMovies()  throws SQLException, InvalidDataException {
		PreparedStatement s = connection.prepareStatement("SELECT id,title,description,rating,duration,file_location FROM movies");
		ArrayList<Movie> movies = new ArrayList<>();
		ResultSet result = s.executeQuery();
		while(result.next()) {
			Movie m = new Movie(
						result.getInt("id"),
						result.getString("title"),
						result.getString("description"),
						result.getDouble("rating"),
						result.getDouble("duration"),
						result.getString("file_location"),
						getAllGenresForAMovie(result.getInt("id"))
						);
			movies.add(m);
		}
		return movies;
	}
	
	@Override
	public Movie getMovieById(int id)  throws SQLException, InvalidDataException {
		PreparedStatement s = connection.prepareStatement("SELECT id,title,description,rating,duration,file_location FROM movies WHERE id=?");
		s.setInt(1, id);
		ResultSet result = s.executeQuery();
		result.next();
		Movie movie = new Movie(
					result.getInt("id"),
					result.getString("title"),
					result.getString("description"),
					result.getDouble("rating"),
					result.getDouble("duration"),
					result.getString("file_location"),
					getAllGenresForAMovie(result.getInt("id"))
					);
		return movie;
	}

	
	@Override
	public ArrayList<String> getMoviesContains(String term) throws SQLException {
		
		ArrayList<String> movies = new ArrayList<>();
		String query = "SELECT title FROM movies WHERE title LIKE ?";
		System.out.println("movie");
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setString(1,"%"+ term + "%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
                movies.add(rs.getString("title"));
			}
		}
		return movies;
	}

	@Override
	public ArrayList<String> getAllMoviesNames() throws SQLException {
		
		ArrayList<String> moviesNames = new ArrayList<>();
		String query = "SELECT title FROM movies ";
		System.out.println("movie");
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				moviesNames.add(rs.getString("title"));
			}
		}
		return moviesNames;
	}
	
	@Override
	public Movie getMovieByName(String name) throws InvalidDataException, SQLException {
		
		String query = "SELECT id, title, description, rating ,  duration , file_location FROM movies WHERE title = ?";
		//System.out.println("movie");
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, name);
		ResultSet result = ps.executeQuery();
		result.next();
		Movie movie = new Movie(
					result.getInt("id"),
					result.getString("title"),
					result.getString("description"),
					result.getDouble("rating"),
					result.getDouble("duration"),
					result.getString("file_location"),
					getAllGenresForAMovie(result.getInt("id"))
					);
		
		return movie;
	
	}
	
	@Override
	public void updateFileLocaiton(Movie movie,String file_location) throws SQLException {
		String query = "UPDATE movies SET file_location = ? WHERE id = ?";
		//System.out.println("movie");
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, file_location);
		ps.setInt(2, movie.getId());
		ps.executeUpdate();
		ps.close();
	
	}
	@Override
	public double getMovieRatingById(int movieIdToBeRated) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("SELECT rating FROM movies WHERE id = ?");
		ps.setInt(1, movieIdToBeRated);
		ResultSet result = ps.executeQuery();
		if(result.next()) {
			return result.getDouble("rating");
		}
		return 0.0;
		
	}
	
	@Override
	public ArrayList<Integer> getAllFavouriteMovieIdsForUser(int userId) throws SQLException{
		PreparedStatement ps = connection.prepareStatement("SELECT movies_id FROM users_favorite_movies WHERE users_id = ?");
		ps.setInt(1, userId);
		ResultSet result = ps.executeQuery();
		ArrayList<Integer> favMoviesIds = new ArrayList<Integer>();
		while(result.next()) {
			favMoviesIds.add(result.getInt("movies_id"));
		}
		ps.close();
		return favMoviesIds;
	}

	@Override
	public ArrayList<Integer> getAllWatchlistMovieIdsForUser(int userId) throws Exception {
		PreparedStatement ps = connection.prepareStatement("SELECT movies_id FROM users_watchlist_movies WHERE users_id = ?");
		ps.setInt(1, userId);
		ResultSet result = ps.executeQuery();
		ArrayList<Integer> watchMovieIds = new ArrayList<Integer>();
		while(result.next()) {
			watchMovieIds.add(result.getInt("movies_id"));
		}
		ps.close();
		return watchMovieIds;
	}
	
	@Override
	public ArrayList<String> getAllGenresForAMovie(int movieId) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("SELECT `genres`.`genre_name` FROM `movies_has_genres`"
				+ "JOIN `genres` on `genres`.`id`=`movies_has_genres`.`genres_id`"
				+ " WHERE `movies_has_genres`.`movies_id` = ?");
		ps.setInt(1, movieId);
		ResultSet result = ps.executeQuery();
		ArrayList<String> allGenres = new ArrayList<String>();
		while(result.next()) {
			allGenres.add(result.getString("genre_name"));
		}
		ps.close();
		return allGenres;
	}

	@Override
	public ArrayList<String> getAllGenres() throws SQLException {
		PreparedStatement ps = connection.prepareStatement("SELECT genre_name FROM genres");
		ResultSet result = ps.executeQuery();
		ArrayList<String> allGenres = new ArrayList<String>();
		while(result.next()) {
			allGenres.add(result.getString("genre_name"));
		}
		ps.close();
		return allGenres;
	}
	
	@Override
	public int getGenreIdByName(String genre) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("SELECT id FROM genres WHERE genre_name = ?");
		ps.setString(1, genre);
		ResultSet result = ps.executeQuery();
		result.next();
		int genreId= result.getInt("id");
		ps.close();
		return genreId;
	}
	
	@Override
	public void addGenresForAMovie(int movieId, ArrayList<String> genres) throws SQLException {
		PreparedStatement s = null;
		for(String str:genres) {
			s = connection.prepareStatement("INSERT INTO movies_has_genres (movies_id, genres_id) VALUES (?,?)");
			s.setInt(1, movieId);
			int genreId = getGenreIdByName(str);
			System.out.println(genreId);
			s.setInt(2, genreId);
			s.executeUpdate();
		}
		s.close();
	}

	@Override
	public void deleteGenresFromMovie(int movieId) throws SQLException {
		PreparedStatement s = connection.prepareStatement("DELETE FROM movies_has_genres where movies_id = ?");
		s.setInt(1, movieId);
		s.executeUpdate();
		s.close();
	}

}
