package com.kinoarena.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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
		PreparedStatement ps = connection.prepareStatement("INSERT INTO broadcasts(cinemas_id , movies_id, halls_id , projection_time ,price) VALUES( ?, ? , ? , ?,?)");
		ps.setInt(1, b.getCinemaId());
		ps.setInt(2, b.getMovieId());
		ps.setInt(3, b.getHallId());
		Timestamp ts = Timestamp.valueOf(b.getProjectionTime());
		// or Timestamp ts = Timestamp.valueOf(b.getProjectionTime());
		ps.setTimestamp(4, ts);
		ps.setDouble(5, b.getPrice());
		ps.executeUpdate();
		ps.close();

	}

	@Override
	public void deleteBroadcast(Broadcast b) throws InvalidDataException, SQLException {
		PreparedStatement ps = connection.prepareStatement("DELETE FROM `broadcasts` WHERE id = ?");
		ps.setInt(1, b.getId());
		ps.executeUpdate();
	}

	@Override
	public void changeBroadcastProjectionTime(int broadcastId, LocalDateTime projectionTime) throws SQLException   {
		PreparedStatement ps = connection.prepareStatement("UPDATE broadcasts SET projection_time = ? WHERE id = ?");
		Timestamp time = Timestamp.valueOf(projectionTime);
		ps.setTimestamp(1, time);
		ps.setInt(2, broadcastId);
		ps.executeUpdate();
		ps.close();
	}

	@Override
	public Collection<Broadcast> getAllBroadcastsForAMovie(Movie m) throws SQLException, InvalidDataException  {
		PreparedStatement s = connection.prepareStatement("SELECT id,cinemas_id,movies_id,halls_id,projection_time, price FROM broadcasts WHERE movies_id = ?");
		s.setInt(1, m.getId());
		ArrayList<Broadcast> broadcasts = new ArrayList<>();
		ResultSet result = s.executeQuery();
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

	@Override //delete this
	public void setPromoPercent(Broadcast b, double promoPercent) throws SQLException, InvalidDataException {

		if(promoPercent <= 0) {
			throw new InvalidDataException("Invalid entered percent. It should be a positive number!");
		}
		PreparedStatement ps = connection.prepareStatement("Select price FROM broadcasts WHERE id = ? ");
		ps.setInt(1, b.getId());
		ResultSet rs = ps.executeQuery();
		rs.next();
		double price = rs.getDouble("price");
		price = price - (price * promoPercent * 0.01);

		ps = connection.prepareStatement("UPDATE broadcasts SET price = ? WHERE id = ?");
		ps.setDouble(1, price);
		ps.setLong(2, b.getId());
		ps.executeUpdate();
		ps.close();

	}

	@Override
	public Collection<Broadcast> getAllBroadcasts() throws InvalidDataException, SQLException  {
		PreparedStatement s = connection.prepareStatement("SELECT id,cinemas_id,movies_id,halls_id,projection_time, price FROM broadcasts");
		ArrayList<Broadcast> broadcasts = new ArrayList<>();
		ResultSet result = s.executeQuery();
		while (result.next()) {
			LocalDateTime time = result.getTimestamp("projection_time").toLocalDateTime();
			Broadcast b = new Broadcast(result.getInt("id"), result.getInt("cinemas_id"), result.getInt("movies_id"),
					result.getInt("halls_id"), time, result.getDouble("price"));
			broadcasts.add(b);
		}
		return broadcasts;
	}

	public Broadcast getBroadcastById(int id) throws SQLException, InvalidDataException {
		// should test this method
		PreparedStatement s = connection.prepareStatement("SELECT id,cinemas_id,movies_id,halls_id,projection_time,price FROM broadcasts WHERE id = ?");
		s.setInt(1, id);

		ResultSet result = s.executeQuery();
		try {
			if (result.next()) {
				LocalDateTime time = result.getTimestamp("projection_time").toLocalDateTime();
				Broadcast b = new Broadcast(
						result.getInt("id"),
						result.getInt("cinemas_id"),
						result.getInt("movies_id"),
						result.getInt("halls_id"),
						time,
						result.getDouble("price"));
				return b;
			}
		} catch (Exception e) {
            throw new InvalidDataException("No such a broadcast");
		}
		return null;

	}

	@Override
	public void changeBroadcastPrice(int id,double newPrice) throws SQLException{
		PreparedStatement ps = connection.prepareStatement("UPDATE broadcasts SET price= ? WHERE id = ?");
		ps.setDouble(1, newPrice);
		ps.setInt(2, id);
		ps.executeUpdate();
		ps.close();
	}

}
