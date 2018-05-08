package com.kinoarena.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import com.kinoarena.controller.manager.DBManager;
import com.kinoarena.model.pojo.Cinema;
import com.kinoarena.utilities.exceptions.InvalidDataException;

@Component
public class CinemaDao implements ICinemaDao{

	private Connection connection;
	
	private CinemaDao() {
		connection = DBManager.getInstance().getConnection();
	}
	
	@Override
	public void addCinema(Cinema cinema) throws SQLException {
		String query= "INSERT INTO cinemas (name, address) VALUES (?,?)";
		try(PreparedStatement ps = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)){
			ps.setString(1, cinema.getName());
			ps.setString(2, cinema.getAddress());
			ps.executeUpdate();
			
			// set the ID for the instance of Cinema c
			ResultSet result = ps.getGeneratedKeys();
			result.next(); // we write next cuz it starts from -1
			cinema.setId((int)result.getLong(1)); // or 1 instead of id
		}
		
	}
	
	@Override
	public void deleteCinema(Cinema c) throws SQLException {
		String query = "DELETE FROM cinemas WHERE id = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setInt(1, c.getId());
			ps.executeUpdate();
		}
		catch (SQLException e) {
			throw new SQLException("Cannot delete cinema, because of exisiting hall!");
		}
	}
	
	@Override
	public Collection<Cinema> getAllCinemas()  throws SQLException, InvalidDataException {
		ArrayList<Cinema> cinemas = new ArrayList<>();
		String query = "SELECT id,name,address FROM cinemas";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ResultSet result = ps.executeQuery();
			while(result.next()) {
			Cinema cinema = new Cinema(
								  result.getInt("id"),
								  result.getString("name"),
								  result.getString("address")
								  );
				cinemas.add(cinema);
			}
			return cinemas;
		}
	}
	
	@Override
	public Cinema getCinemaById(int id)  throws SQLException, InvalidDataException {
		String query = "SELECT id,name,address FROM cinemas WHERE id=?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setInt(1, id);
			ResultSet result = ps.executeQuery();
			result.next();
			Cinema cinema = new Cinema(
								  result.getInt("id"),
								  result.getString("name"),
								  result.getString("address")
								  );
			return cinema;
		}
	}
}
