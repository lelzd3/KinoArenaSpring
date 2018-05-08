package com.kinoarena.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kinoarena.controller.manager.DBManager;
import com.kinoarena.model.pojo.Movie;
import com.kinoarena.model.pojo.User;
import com.kinoarena.utilities.BCrypt;
import com.kinoarena.utilities.exceptions.InvalidDataException;
import com.kinoarena.utilities.exceptions.WrongCredentialsException;

@Component
public class UserDao implements IUserDao{
	
	private Connection connection;

	private UserDao() {
		connection = DBManager.getInstance().getConnection();
	}

	@Autowired
	private MovieDao movieDao;
	
	@Override
	public void loginCheck(String username, String password) throws SQLException, WrongCredentialsException {
		String query = "SELECT password FROM users WHERE username = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (!rs.next() ) {
				// if there is nothing in result set -> username doesnt exists
				throw new WrongCredentialsException("Invalid username or password");
			}
			if (! BCrypt.checkpw(password, rs.getString("password") )) { //TODO in servlet?
				// if there is username but the pass doesnt match -> invalid password
				throw new WrongCredentialsException("Invalid username or password");
			}
		}
	}

	@Override
	public void addUser(User user) throws SQLException {
		String query = "INSERT INTO users(username, password, email, first_name, last_name , is_Admin, age) VALUES(?, ?, ?, ?, ?, ?, ?)";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setString(1, user.getUsername());
			String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
			ps.setString(2, hashedPassword);
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getFirstname());
			ps.setString(5, user.getLastname());
			ps.setInt(6, 0);
			ps.setInt(7, user.getAge());
			ps.executeUpdate();
		}
	}


	@Override
	public void addMovieToFavoriteList(User user, Movie movie) throws SQLException {
		String query = "INSERT INTO users_favorite_movies(users_id , movies_id) VALUES(?, ?)";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setInt(1, user.getId());
			ps.setInt(2, movie.getId());
			ps.executeUpdate();
		}
		
	}

	@Override
	public void addMovieToWatchList(User u, Movie m) throws SQLException {
		String query = "INSERT INTO users_watchlist_movies(users_id , movies_id) VALUES(?, ?)";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setInt(1, u.getId());
			ps.setInt(2, m.getId());
			ps.executeUpdate();
		}	
	}

	@SuppressWarnings("resource")
	@Override
	public void rateMovie(User user, Movie movie,int rating) throws SQLException, InvalidDataException {
		
		double newRating = 0;
		PreparedStatement ps = null;
		try {
			connection.setAutoCommit(false);
			
			//check if user has rated and put value in oldRatingGiven
			ps = connection.prepareStatement("SELECT rating FROM users_rated_movies WHERE users_id = ? AND movies_id= ?");
			ps.setInt(1, user.getId());
			ps.setInt(2, movie.getId());
			ResultSet r = ps.executeQuery();
			if(r.next()) {
				//if hasNext->user has rated before -> update old entry of user rating in users_rated_movies
				ps = connection.prepareStatement("UPDATE users_rated_movies SET rating = ? WHERE users_id = ? AND movies_id= ?");
				ps.setInt(1, rating);
				ps.setInt(2, user.getId());
				ps.setInt(3, movie.getId());
				ps.executeUpdate();
			}
			else {
				//put the new entry of user rating in users_rated_movies
				ps = connection.prepareStatement("INSERT INTO users_rated_movies (users_id,movies_id,rating) VALUES(?,?,?)");
				ps.setInt(1, user.getId());
				ps.setInt(2, movie.getId());
				ps.setInt(3,rating);
				ps.executeUpdate();
			}
			
			
			//get AVG rating for movies_id = m.getId()
			ps = connection.prepareStatement("SELECT AVG(rating) FROM users_rated_movies WHERE movies_id = ?");
			ps.setInt(1, movie.getId());
			ResultSet result2 = ps.executeQuery();
			result2.next();
			newRating = result2.getDouble(1);
			
			// updates the movie
			ps = connection.prepareStatement("UPDATE movies SET rating = ? WHERE id = ?");
			ps.setDouble(1, newRating);
			ps.setInt(2, movie.getId());
			ps.executeUpdate();
			
			connection.commit();
		}
		catch(SQLException e){
			connection.rollback();
			throw e;
		}
		finally {
			ps.close();
			connection.setAutoCommit(true);
		}

	}

	@Override
	public void existingUserNameCheck(String username) throws InvalidDataException, SQLException {
	
		String query = "SELECT username FROM users WHERE username = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				throw new InvalidDataException("This username is already used");
			}
		}
	}
	
	@Override
	public void existingEmailCheck(String email) throws InvalidDataException, SQLException {
		String query = "SELECT email FROM users WHERE email = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				throw new InvalidDataException("This email is already used");
			}
		}
	}


	@Override
	public User getUser(String username) throws InvalidDataException, SQLException {
		String query = "SELECT id, first_name, last_name, username, password, email ,is_Admin, age FROM users WHERE username = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setString(1, username);
			ResultSet result = ps.executeQuery();
			result.next();
			
			boolean isAdmin = result.getInt("is_Admin") == 1;
			
			return new User(
						result.getInt("id"),
						result.getInt("age"),
						result.getString("username"),
						result.getString("password"),
						result.getString("first_name"),
						result.getString("last_name"),
						result.getString("email"),
						isAdmin
						);
		}
		
	}
	
	//notUSED
	@Override
	public void createAdmin(String email) throws SQLException, InvalidDataException{
		PreparedStatement ps = null;
		try {
			connection.setAutoCommit(false);
			ps = connection.prepareStatement("SELECT id FROM users WHERE email = ? AND is_Admin = ?");
			ps.setString(1, email);
			ps.setInt(2, 0);
			ResultSet result = ps.executeQuery();
			
			String userId = null;
			while(result.next()){
				userId = result.getString(1);
			}
			
			if(userId == null){
				throw new InvalidDataException("Oops, problem in creating an admin! The email maybe is incorrect or user is already an admin");
			}
			
			ps = connection.prepareStatement("UPDATE  users SET is_Admin = ? WHERE id = ?;");
			ps.setInt(1, 1);
			ps.setString(2, userId);
			ps.executeUpdate();
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
	
	//NOT IN INTERFACE
	@Override
	public User getUserByEmail(String email) throws InvalidDataException, SQLException {
		String query = "SELECT id, first_name, last_name, username, password, email , is_Admin, age FROM users WHERE email = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setString(1, email);
			ResultSet result = ps.executeQuery();
			result.next();
			
			boolean isAdmin = result.getInt("is_Admin") == 1;
			
			return new User(
						result.getInt("id"),
						result.getInt("age"),
						result.getString("username"),
						result.getString("password"),
						result.getString("first_name"),
						result.getString("last_name"),
						result.getString("email"),
						isAdmin
						);
		}
	}

	@Override
	public Collection<User> getAllUsersButNoAdmins() throws SQLException, InvalidDataException {
		ArrayList<User> users = new ArrayList<User>();
		String query = "SELECT id, first_name, last_name, username, password, email , is_Admin,age FROM users  WHERE is_Admin = 0";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ResultSet result = ps.executeQuery();

			while(result.next()) {
				boolean isAdmin = result.getInt("is_Admin") == 1;
				User user = new User(
						result.getInt("id"),
						result.getInt("age"),
						result.getString("username"),
						result.getString("password"),
						result.getString("first_name"),
						result.getString("last_name"),
						result.getString("email"),
						isAdmin
						);
				users.add(user);
			}
			return users;
		}
	}
	
	@Override
	public void addInFavorite(int userId, int movieId) throws SQLException {
		String query = "INSERT INTO users_favorite_movies (users_id, movies_id) VALUES (?,?)";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setLong(1, userId);
			ps.setLong(2, movieId);
			ps.executeUpdate();
		}
		catch (SQLException e) {
			throw new SQLException("You cant add a single movie in favorite list twice!");
		}
	}
	
	@Override
	public void removeFavouriteMovie(int userId, int  movieId) throws SQLException {
		String query = "DELETE FROM users_favorite_movies WHERE users_id = ? AND movies_id = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setInt(1, userId);
			ps.setInt(2, movieId);
			ps.executeUpdate();
		}
		catch (SQLException e) {
			throw new SQLException("You cant remove a single movie in favorite list twice!");
		}
	}

	@Override
	public ArrayList<Movie> viewFavourite(User user) throws InvalidDataException, SQLException {
		ArrayList<Movie> movies = new ArrayList<>();
		PreparedStatement ps  = null;
		String query = "SELECT id,title,description,rating,duration,file_location  FROM movies AS m JOIN users_favorite_movies AS f"
				+ " ON( m.id = f.movies_id )WHERE f.users_id = ?";
		try {
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(query);
			ps.setInt(1, user.getId());
			ResultSet result = ps.executeQuery();
			ps.setInt(1,user.getId());
			while (result.next()) {
				Movie m = new Movie(
						result.getInt("id"),
						result.getString("title"),
						result.getString("description"),
						result.getDouble("rating"),
						result.getDouble("duration"),
						result.getString("file_location"),
						movieDao.getAllGenresForAMovie(result.getInt("id"))
						);
				movies.add(m);
			}
			connection.commit();
			return movies;
		}
		catch(SQLException e){
			connection.rollback();
			throw e;
		}
		finally {
			ps.close();
			connection.setAutoCommit(true);
		}
	
	}

	//notused
	@Override
	public boolean isMovieInFavourite(int userId, int movieId) throws SQLException {
		String query = "SELECT users_id, movies_id FROM users_favorite_movies WHERE users_id = ? AND movies_id = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setInt(1, userId);
			ps.setInt(2, movieId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				return true;
			}else{
				return false;
			}
		}
	}

	@Override
	public ArrayList<Movie> viewWatchlist(User user) throws InvalidDataException, SQLException {
		String query = "SELECT id,title,description,rating,duration,file_location  FROM movies AS m JOIN users_watchlist_movies AS f"
				+ " ON( m.id = f.movies_id )WHERE f.users_id = ?";
		ArrayList<Movie> movies = new ArrayList<>();
		PreparedStatement ps = null;
		try {	
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(query);
			ps.setInt(1, user.getId());
			ResultSet result = ps.executeQuery();
			
			while (result.next()) {
				Movie m = new Movie(
						result.getInt("id"),
						result.getString("title"),
						result.getString("description"),
						result.getDouble("rating"),
						result.getDouble("duration"),
						result.getString("file_location"),
						movieDao.getAllGenresForAMovie(result.getInt("id"))
						);
				movies.add(m);
			}
			
			connection.commit();
			return movies;
		}
		catch(SQLException e){
			connection.rollback();
			throw e;
		}
		finally {
			ps.close();
			connection.setAutoCommit(true);
		}
	}

	@Override
	public void addInWatchlist(int userId, Integer movieId) throws SQLException {
		String query = "INSERT INTO users_watchlist_movies (users_id, movies_id) VALUES (?,?)";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setLong(1, userId);
			ps.setLong(2, movieId);
			ps.executeUpdate();
		}catch (SQLException e) {
			throw new SQLException("You cant add a single movie in watchlist twice!");
		}
	}

	@Override
	public void removeFavouriteWatchlist(int userId, Integer movieId) throws SQLException {
		String query = "DELETE FROM users_watchlist_movies WHERE users_id = ? AND movies_id = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setInt(1, userId);
			ps.setInt(2, movieId);
			ps.executeUpdate();
		}catch (SQLException e) {
			throw new SQLException("You cant remove a single movie in watchlist twice!");
		}
	}
	
	//notused
	@Override
	public boolean isMovieInWatchlist(int userId, int movieId) throws SQLException {
		String query = "SELECT users_id, movies_id FROM users_watchlist_movies WHERE users_id = ? AND movies_id = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setInt(1, userId);
			ps.setInt(2, movieId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				return true;
			}else{
				return false;
			}
		}
	}

	@Override
	public void changeFirstName(User user, String validateName) throws SQLException, InvalidDataException {

		String currentName = user.getFirstname();
		if(!currentName.equals(validateName)) {
			String query = "UPDATE users SET first_name= ? WHERE id=?";
			try(PreparedStatement ps = connection.prepareStatement(query)){
				ps.setString(1, validateName);
				ps.setLong(2, user.getId());
				ps.executeUpdate();
			}

			user.setFirstName(validateName);
		}
	}

	@Override
	public void changeLastName(User user, String validateName) throws SQLException, InvalidDataException {

		String currentName = user.getLastname();
		if(!currentName.equals(validateName)) {
			String query = "UPDATE users SET last_name= ? WHERE id=?";
			try(PreparedStatement ps = connection.prepareStatement(query)){
				ps.setString(1, validateName);
				ps.setLong(2, user.getId());
				ps.executeUpdate();
			}
            user.setLastName(validateName);
		}
	}

	@Override
	public void changeEmail(User user, String editedEmail) throws InvalidDataException, SQLException {

		String email = user.getEmail();
		if(!email.equals(editedEmail)) {
			String query = "UPDATE users SET email= ? WHERE id=?";
			try(PreparedStatement ps = connection.prepareStatement(query)){
				ps.setString(1, editedEmail);
				ps.setLong(2, user.getId());
				ps.executeUpdate();
			}
			catch(SQLException e) {
				throw new SQLException("Email is already used");
			}
			user.setEmail(editedEmail);
		}
	}

	@Override
	public void changePassword(User user, String editedPassword) throws SQLException, InvalidDataException {
		
		String hashedPassword = BCrypt.hashpw(editedPassword, BCrypt.gensalt());
		String password = user.getPassword();
		if(!password.equals(hashedPassword)) {
			String query = "UPDATE users SET password= ? WHERE id=?";
			try(PreparedStatement ps = connection.prepareStatement(query)){
				ps.setString(1, hashedPassword);
				ps.setLong(2, user.getId());
				ps.executeUpdate();
			}
			user.setPassword(hashedPassword);
		}
	}

	@Override
	public void setNewPasswod(String receiverEmail, String randomPassword) throws SQLException {
		String query = "UPDATE users SET password = ? WHERE email = ?";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			String hashedPassword = BCrypt.hashpw(randomPassword, BCrypt.gensalt());
			ps.setString(1, hashedPassword);
			ps.setString(2, receiverEmail);
			ps.executeUpdate();
		}
		
	}
		
	
}
