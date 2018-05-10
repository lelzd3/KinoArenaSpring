package com.kinoarena.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import com.kinoarena.controller.manager.DBManager;
import com.kinoarena.model.pojo.Broadcast;
import com.kinoarena.model.pojo.Movie;
import com.kinoarena.utilities.exceptions.InvalidDataException;

@Component
public class BroadcastDao implements IBroadcastDao {

	private Connection connection;

	private BroadcastDao() {
		connection = DBManager.getInstance().getConnection();
	}

	@Override
	public void addBroadcast(Broadcast b) throws InvalidDataException, SQLException {
		String query = "INSERT INTO broadcasts(cinemas_id , movies_id, halls_id , projection_time ,price) VALUES( ?, ? , ? , ?,?)";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setInt(1, b.getCinemaId());
			ps.setInt(2, b.getMovieId());
			ps.setInt(3, b.getHallId());
			Timestamp ts = Timestamp.valueOf(b.getProjectionTime());
			ps.setTimestamp(4, ts);
			ps.setDouble(5, b.getPrice());
			ps.executeUpdate();
		}
	}

	@Override
	public void deleteBroadcast(int broadcastId) throws InvalidDataException, SQLException {
		String query = "DELETE FROM `broadcasts` WHERE id = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setInt(1, broadcastId);
			ps.executeUpdate();
		}
		catch (SQLException e) {
			throw new SQLException("Cannot delete broadcast, because of exisiting reservation!");
		}
	}

	@Override
	public void changeBroadcastProjectionTime(int broadcastId, LocalDateTime projectionTime) throws SQLException   {
		String query = "UPDATE broadcasts SET projection_time = ? WHERE id = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			Timestamp time = Timestamp.valueOf(projectionTime);
			ps.setTimestamp(1, time);
			ps.setInt(2, broadcastId);
			ps.executeUpdate();
		}
	}

	@Override
	public Collection<Broadcast> getAllBroadcastsForAMovie(Movie m) throws SQLException, InvalidDataException  {
		String query = "SELECT id,cinemas_id,movies_id,halls_id,projection_time, price FROM broadcasts WHERE movies_id = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setInt(1, m.getId());
			ArrayList<Broadcast> broadcasts = new ArrayList<>();
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				LocalDateTime time = result.getTimestamp("projection_time").toLocalDateTime();
				Broadcast b = new Broadcast(
						result.getInt("id"),
						result.getInt("cinemas_id"),
						result.getInt("movies_id"),
						result.getInt("halls_id"),
						time,
						result.getDouble("price")
						);
				broadcasts.add(b);
			}
			return broadcasts;
		}
	}

	@Override
	public Collection<Broadcast> getAllBroadcasts() throws InvalidDataException, SQLException  {
		String query = "SELECT id,cinemas_id,movies_id,halls_id,projection_time, price FROM broadcasts";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ArrayList<Broadcast> broadcasts = new ArrayList<>();
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				LocalDateTime time = result.getTimestamp("projection_time").toLocalDateTime();
				Broadcast b = new Broadcast(
						result.getInt("id"),
						result.getInt("cinemas_id"),
						result.getInt("movies_id"),
						result.getInt("halls_id"),
						time,
						result.getDouble("price"));
				broadcasts.add(b);
			}
			return broadcasts;
		}
	}

	public Broadcast getBroadcastById(int id) throws SQLException, InvalidDataException {
		String query = "SELECT id,cinemas_id,movies_id,halls_id,projection_time,price FROM broadcasts WHERE id = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setInt(1, id);
			ResultSet result = ps.executeQuery();
			try {
				result.next();
				LocalDateTime time = result.getTimestamp("projection_time").toLocalDateTime();
				Broadcast b = new Broadcast(
						result.getInt("id"),
						result.getInt("cinemas_id"),
						result.getInt("movies_id"),
						result.getInt("halls_id"),
						time,
						result.getDouble("price"));
				return b;
		
			} catch (Exception e) {
	            throw new InvalidDataException("No such broadcast");
			}
		}
	}

	@Override
	public void changeBroadcastPrice(int id,double newPrice) throws SQLException{
		String query = "UPDATE broadcasts SET price= ? WHERE id = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setDouble(1, newPrice);
			ps.setInt(2, id);
			ps.executeUpdate();
		}
	}

}
