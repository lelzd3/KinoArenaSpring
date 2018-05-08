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
import com.kinoarena.model.pojo.Hall;
import com.kinoarena.utilities.exceptions.InvalidDataException;

@Component
public class HallDao implements IHallDao{

	private Connection connection;
	
	private HallDao() {
		connection = DBManager.getInstance().getConnection();
	}
	
	
	@Override
	public void addHall(Hall hall) throws SQLException {
		String query = "INSERT INTO halls (cinemas_id) VALUES (?)";
		try(PreparedStatement ps = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)){
			ps.setInt(1, hall.getCinemaId());
			ps.executeUpdate();
			
			// set the ID for the instance of Hall h
			ResultSet result = ps.getGeneratedKeys();
			result.next(); // we write next cuz it starts from -1
			hall.setId((int)result.getLong(1)); // or 1 instead of id
		}
		
	}
	
	@Override
	public void deleteHall(Hall hall) throws SQLException {
		String query = "DELETE FROM halls WHERE id = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setInt(1, hall.getId());
			ps.executeUpdate();
		}
	}
	
	@Override
	public Collection<Hall> getAllHalls()  throws SQLException, InvalidDataException {
		ArrayList<Hall> halls = new ArrayList<>();
		String query = "SELECT id,cinemas_id FROM halls";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				Hall h = new Hall(
								  result.getInt("id"),
								  result.getInt("cinemas_id")
								  );
				halls.add(h);
			}
			return halls;
		}
	}

	@Override
	public Collection<Hall> getAllHallsForACinema(Cinema cinema) throws Exception {
		ArrayList<Hall> halls = new ArrayList<>();
		String query = "SELECT id,cinemas_id FROM halls WHERE cinemas_id = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setInt(1, cinema.getId());
			
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				Hall h = new Hall(
								  result.getInt("id"),
								  result.getInt("cinemas_id")
								  );
				halls.add(h);
			}
			return halls;
		}
	}
	
	@Override
	public Hall getHallById(int id) throws SQLException, InvalidDataException  {
		String query = "SELECT id,cinemas_id FROM halls WHERE id = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setInt(1, id);
			ResultSet result = ps.executeQuery();
			result.next();
			Hall hall = new Hall(
							result.getInt("id"),
							result.getInt("cinemas_id")
							 );
			
			return hall;
		}
	}
}
