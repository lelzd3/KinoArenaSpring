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
	public void addHall(Hall h) throws SQLException {
		
		PreparedStatement s = connection.prepareStatement("INSERT INTO halls (cinemas_id) VALUES (?)",Statement.RETURN_GENERATED_KEYS);
		s.setInt(1, h.getCinemaId());
		s.executeUpdate();
		
		// set the ID for the instance of Hall h
		ResultSet result = s.getGeneratedKeys();
		result.next(); // we write next cuz it starts from -1
		h.setId((int)result.getLong(1)); // or 1 instead of id
		
		s.close();
		
	}
	
	@Override
	public void deleteHall(Hall h) throws SQLException {
		PreparedStatement s = connection.prepareStatement("DELETE FROM halls WHERE id = ?");
		s.setInt(1, h.getId());
		s.executeUpdate();
		s.close();
	}
	
	@Override
	public Collection<Hall> getAllHalls()  throws SQLException, InvalidDataException {
		PreparedStatement s = connection.prepareStatement("SELECT id,cinemas_id FROM halls");
		ArrayList<Hall> halls = new ArrayList<>();
		ResultSet result = s.executeQuery();
		while(result.next()) {
			Hall h = new Hall(
							  result.getInt("id"),
							  result.getInt("cinemas_id")
							  );
			halls.add(h);
		}
		return halls;
	}

	@Override
	public Collection<Hall> getAllHallsForACinema(Cinema c) throws Exception {
		PreparedStatement s = connection.prepareStatement("SELECT id,cinemas_id FROM halls WHERE cinemas_id = ?");
		s.setInt(1, c.getId());
		ArrayList<Hall> halls = new ArrayList<>();
		ResultSet result = s.executeQuery();
		while(result.next()) {
			Hall h = new Hall(
							  result.getInt("id"),
							  result.getInt("cinemas_id")
							  );
			halls.add(h);
		}
		return halls;
	}
	
	@Override
	public Hall getHallById(int id) throws SQLException, InvalidDataException  {
		PreparedStatement s = connection.prepareStatement("SELECT id,cinemas_id FROM halls WHERE id = ?");
		s.setInt(1, id);
		ResultSet result = s.executeQuery();
		result.next();
		Hall hall = new Hall(
						result.getInt("id"),
						result.getInt("cinemas_id")
						 );
		
		return hall;
	}
}
