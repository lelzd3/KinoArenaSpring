package com.kinoarena.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
		String query = "INSERT INTO movies (title, description,rating,duration,file_location) VALUES (?,?,?,?,?)";
		PreparedStatement ps = null;
		try {
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1,m.getTitle());
			ps.setString(2, m.getDescription());
			ps.setDouble(3, m.getRating());
			ps.setDouble(4, m.getDuration());
			ps.setString(5, m.getFile_location());
			ps.executeUpdate();
			
			ResultSet result = ps.getGeneratedKeys();
			result.next();
			m.setId((int)result.getLong(1));
			
			addGenresForAMovie(m.getId(), genres);
			
			connection.commit();
		}
		catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			ps.close();
			connection.setAutoCommit(true);
		}
		
		
	}
	
	@Override
	public void deleteMovie(Movie m) throws SQLException {
		String query = "DELETE FROM movies WHERE id = ?";
		PreparedStatement ps = null;
		try{
			connection.setAutoCommit(false);
			deleteGenresFromMovie(m.getId());
			ps = connection.prepareStatement(query);

			ps.setInt(1, m.getId());
			ps.executeUpdate();
			connection.commit();
		}
		catch(SQLException e) {
			connection.rollback();
			throw new SQLException("Cannot delete movie, because of exisiting broadcast or reservation!");
		}
		finally {
			ps.close();
			connection.setAutoCommit(true);
		}
	}
	
	@Override
	public ArrayList<Movie> getAllMovies()  throws SQLException, InvalidDataException {
		String query ="SELECT id,title,description,rating,duration,file_location FROM movies";
		PreparedStatement ps = null;
		try {
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(query);
			ArrayList<Movie> movies = new ArrayList<>();
			ResultSet result = ps.executeQuery();
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
			connection.commit();
			return movies;
		}
		catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			ps.close();
			connection.setAutoCommit(true);
		}
		
	}
	
	@Override
	public Movie getMovieById(int id)  throws SQLException, InvalidDataException {
		String query = "SELECT id,title,description,rating,duration,file_location FROM movies WHERE id=?";
		PreparedStatement ps = null;
		try {
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(query);
			ps.setInt(1, id);
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
			connection.commit();
			return movie;
		}
		catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			ps.close();
			connection.setAutoCommit(true);
		}
		
	}

	@Override
	public ArrayList<String> getMoviesContains(String term) throws SQLException {
		ArrayList<String> movies = new ArrayList<>();
		String query = "SELECT title FROM movies WHERE title LIKE ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setString(1,"%"+ term + "%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
                movies.add(rs.getString("title"));
			}
			return movies;
		}
		
	}

	@Override
	public ArrayList<String> getAllMoviesNames() throws SQLException {
		ArrayList<String> moviesNames = new ArrayList<>();
		String query = "SELECT title FROM movies ";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				moviesNames.add(rs.getString("title"));
			}
			return moviesNames;
		}
		
	}
	
	@Override
	public Movie getMovieByName(String name) throws InvalidDataException, SQLException {
		String query = "SELECT id, title, description, rating ,  duration , file_location FROM movies WHERE title = ?";
		PreparedStatement ps = null;
		try {
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(query);
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
			connection.commit();
			return movie;
		}
		catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			ps.close();
			connection.setAutoCommit(true);
		}
	
	}
	
	@Override
	public void updateFileLocaiton(Movie movie,String file_location) throws SQLException {
		String query = "UPDATE movies SET file_location = ? WHERE id = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setString(1, file_location);
			ps.setInt(2, movie.getId());
			ps.executeUpdate();
		}
	}
	
	@Override
	public double getMovieRatingById(int movieIdToBeRated) throws SQLException {
		String query = "SELECT rating FROM movies WHERE id = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setInt(1, movieIdToBeRated);
			ResultSet result = ps.executeQuery();
			if(result.next()) {
				return result.getDouble("rating");
			}
			return 0.0;
		}
		
	}
	
	@Override
	public ArrayList<Integer> getAllFavouriteMovieIdsForUser(int userId) throws SQLException{
		String query = "SELECT movies_id FROM users_favorite_movies WHERE users_id = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setInt(1, userId);
			ResultSet result = ps.executeQuery();
			ArrayList<Integer> favMoviesIds = new ArrayList<Integer>();
			while(result.next()) {
				favMoviesIds.add(result.getInt("movies_id"));
			}
			return favMoviesIds;
		}
	}

	@Override
	public ArrayList<Integer> getAllWatchlistMovieIdsForUser(int userId) throws Exception {
		String query = "SELECT movies_id FROM users_watchlist_movies WHERE users_id = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setInt(1, userId);
			ResultSet result = ps.executeQuery();
			ArrayList<Integer> watchMovieIds = new ArrayList<Integer>();
			while(result.next()) {
				watchMovieIds.add(result.getInt("movies_id"));
			}
			return watchMovieIds;
		}
	}
	
	@Override
	public ArrayList<String> getAllGenresForAMovie(int movieId) throws SQLException {
		String query = "SELECT `genres`.`genre_name` FROM `movies_has_genres`"
				+ "JOIN `genres` on `genres`.`id`=`movies_has_genres`.`genres_id`"
				+ " WHERE `movies_has_genres`.`movies_id` = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setInt(1, movieId);
			ResultSet result = ps.executeQuery();
			ArrayList<String> allGenres = new ArrayList<String>();
			while(result.next()) {
				allGenres.add(result.getString("genre_name"));
			}
			return allGenres;
		}
	}

	@Override
	public ArrayList<String> getAllGenres() throws SQLException {
		String query = "SELECT genre_name FROM genres";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ResultSet result = ps.executeQuery();
			ArrayList<String> allGenres = new ArrayList<String>();
			while(result.next()) {
				allGenres.add(result.getString("genre_name"));
			}
			return allGenres;
		}
	}
	
	@Override
	public int getGenreIdByName(String genre) throws SQLException {
		String query = "SELECT id FROM genres WHERE genre_name = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setString(1, genre);
			ResultSet result = ps.executeQuery();
			result.next();
			int genreId= result.getInt("id");
			return genreId;
		}
	}
	
	//NotTransaction because it is used IN Transaction add movie
	@Override
	public void addGenresForAMovie(int movieId, ArrayList<String> genres) throws SQLException {
		String query = "INSERT INTO movies_has_genres (movies_id, genres_id) VALUES (?,?)";
		PreparedStatement ps = null;
		for(String genreName: genres) {
			ps = connection.prepareStatement(query);
			ps.setInt(1, movieId);
			int genreId = getGenreIdByName(genreName);
			ps.setInt(2, genreId);
			ps.executeUpdate();
		}

		ps.close();
		
		
	}

	@Override
	public void deleteGenresFromMovie(int movieId) throws SQLException {
		String query = "DELETE FROM movies_has_genres where movies_id = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setInt(1, movieId);
			ps.executeUpdate();
		}
	}

}
