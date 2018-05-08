package com.kinoarena.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import com.kinoarena.controller.manager.DBManager;
import com.kinoarena.model.pojo.Broadcast;
import com.kinoarena.model.pojo.Reservation;
import com.kinoarena.model.pojo.Seat;
import com.kinoarena.model.pojo.User;
import com.kinoarena.utilities.exceptions.InvalidDataException;

@Component
public class ReservationDao implements IReservationDao {

	private Connection connection;

	private ReservationDao() {
		connection = DBManager.getInstance().getConnection();
	}

	@Override
	public void addReservation(Reservation reservation, ArrayList<Seat> seats) throws SQLException {

		PreparedStatement ps = null;
		try {
			connection.setAutoCommit(false);
			ps = connection.prepareStatement("INSERT INTO reservations (users_id,broadcasts_id,seats_number,time) VALUES (?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, reservation.getUserId());
			ps.setInt(2, reservation.getBroadcastId());
			ps.setInt(3, reservation.getAllSeatsReserved().size());
			Timestamp time = Timestamp.valueOf(reservation.getTimeReservationIsMade());
			ps.setTimestamp(4, time);
			ps.executeUpdate();
			
			ResultSet result = ps.getGeneratedKeys();
			result.next();
			reservation.setId((int)result.getLong(1));

			for (Seat seat : seats) {
				ps = connection.prepareStatement("INSERT INTO `reservations_seats` (`ticket_reservations_id` ,`row_number` ,`column_number`) VALUES (?,?,?)");
				ps.setInt(1, reservation.getId());
				ps.setInt(2, seat.getRow());
				ps.setInt(3, seat.getColumn());
				ps.executeUpdate();
			}
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw new SQLException("Sorry, but seat is already booked :(");
		} finally {
			ps.close();
			connection.setAutoCommit(true);
		}
	}

	@SuppressWarnings("resource")
	@Override
	public void deleteReservation(String reservationId) throws SQLException {
		// Transaction remove from reservations table
		// then remove all from reservations_seats where id = reservation id
        //here we use string due to cancelReservation in UserController
		PreparedStatement ps = null;
		try {
			connection.setAutoCommit(false);
			
			ps = connection.prepareStatement("DELETE FROM reservations_seats WHERE ticket_reservations_id = ?");
			ps.setString(1,reservationId);
			ps.executeUpdate();
			
			
			ps = connection.prepareStatement("DELETE FROM reservations WHERE id = ?");
			ps.setString(1,reservationId);
			ps.executeUpdate();

			
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			ps.close();
			connection.setAutoCommit(true);
		}

	}

	@Override
	public Collection<Reservation> getAllReservationsForABroadcast(Broadcast broadcast) throws SQLException, InvalidDataException {
		ArrayList<Reservation> reservations = new ArrayList<>();
		String query = "SELECT id,users_id,broadcasts_id,seats_number,time FROM reservations WHERE broadcasts_id = ?";
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.setInt(1, broadcast.getId());
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				LocalDateTime time = result.getTimestamp("time").toLocalDateTime();
				Reservation r = new Reservation(
						result.getInt("id"),
						result.getInt("users_id"),
						result.getInt("broadcasts_id"),
						result.getInt("seats_number"),
						time
						);
				
				reservations.add(r);
			}
			return reservations;
		}
	}


	@Override
	public ArrayList<String> getAllOccupiedSeatsForABroadcast(Broadcast broadcast) throws SQLException, InvalidDataException {

		ArrayList<String> allSeats = new ArrayList<String>();
		PreparedStatement ps = null;
		try {
			connection.setAutoCommit(false);

			ArrayList<Reservation> reservations = (ArrayList<Reservation>) getAllReservationsForABroadcast(broadcast);

			for (Reservation reservation : reservations) {
				ps = connection.prepareStatement("SELECT `row_number` , `column_number` FROM `reservations_seats` WHERE `ticket_reservations_id` = ?");
				ps.setInt(1, reservation.getId());
				ResultSet result = ps.executeQuery();
				
				while (result.next()) {
					allSeats.add(result.getInt("row_number") + "_" + result.getInt("column_number"));
				}

			}

			connection.commit();

		} catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			ps.close();
			connection.setAutoCommit(true);
		}
		return allSeats;

	}

	@Override
	public ArrayList<String> getAllReservationsForUser(User user) throws SQLException {
		ArrayList<String> allReservations = new ArrayList<String>();
		PreparedStatement ps = null;
		try {
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(
					"Select r.id, u.username , m.title , c.name, seats_number , time from reservations AS r"
					+ " JOIN users AS u on r.users_id = u.id"
					+ " JOIN broadcasts as b on b.id = r.broadcasts_id"
					+ " JOIN movies as m  on b.movies_id = m.id"
					+ " JOIN cinemas as c on b.cinemas_id = c.id WHERE r.users_id = ?;");
			ps.setInt(1, user.getId());
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				allReservations.add(
					"Reservation id: "+result.getInt("id")+", Username: "+result.getString("u.username")
					+", Movie title: "+result.getString("m.title")+ ", Cinema name: "+result.getString("c.name")
					+", Seats reserved "+result.getInt("r.seats_number")+", Reservation made: "+result.getTimestamp("time"));
			}
			connection.commit();
		} catch (SQLException e) {
			throw e;
		} finally {
			ps.close();
		}
		return allReservations;

	}
}
