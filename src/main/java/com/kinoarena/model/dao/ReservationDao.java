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

		PreparedStatement s = null;
		try {
			connection.setAutoCommit(false);
			s = connection.prepareStatement("INSERT INTO reservations (users_id,broadcasts_id,seats_number,time) VALUES (?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			s.setInt(1, reservation.getUserId());
			s.setInt(2, reservation.getBroadcastId());
			s.setInt(3, reservation.getAllSeatsReserved().size());
			Timestamp time = Timestamp.valueOf(reservation.getTimeReservationIsMade());
			s.setTimestamp(4, time);
			s.executeUpdate();
			
			ResultSet result = s.getGeneratedKeys();
			result.next();
			reservation.setId((int)result.getLong(1));

			for (Seat seat : seats) {
				s = connection.prepareStatement("INSERT INTO `reservations_seats` (`ticket_reservations_id` ,`row_number` ,`column_number`) VALUES (?,?,?)");
				s.setInt(1, reservation.getId());
				s.setInt(2, seat.getRow());
				s.setInt(3, seat.getColumn());
				s.executeUpdate();
			}
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			s.close();
			connection.setAutoCommit(true);
		}
	}

	@SuppressWarnings("resource")
	@Override
	public void deleteReservation(String reservationId) throws SQLException {
		// Transaction remove from reservations table
		// then remove all from reservations_seats where id = reservation id
        //here we use string due to cancelReservation in UserController
		PreparedStatement s = null;
		try {
			connection.setAutoCommit(false);
			
			s = connection.prepareStatement("DELETE FROM reservations_seats WHERE ticket_reservations_id = ?");
			s.setString(1,reservationId);
			s.executeUpdate();
			
			
			s = connection.prepareStatement("DELETE FROM reservations WHERE id = ?");
			s.setString(1,reservationId);
			s.executeUpdate();

			
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			s.close();
			connection.setAutoCommit(true);
		}

	}

	@Override
	public Collection<Reservation> getAllReservationsForABroadcast(Broadcast b) throws SQLException, InvalidDataException {
		PreparedStatement s = connection.prepareStatement("SELECT id,users_id,broadcasts_id,seats_number,time FROM reservations WHERE broadcasts_id = ?");
		s.setInt(1, b.getId());
		ArrayList<Reservation> reservations = new ArrayList<>();
		ResultSet result = s.executeQuery();
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

	// TODO put in interface
	public ArrayList<String> getAllOccupiedSeatsForABroadcast(Broadcast broadcast) throws SQLException, InvalidDataException {

		ArrayList<String> allSeats = new ArrayList<String>();
		PreparedStatement ps = null;
		try {
			connection.setAutoCommit(false);

			ArrayList<Reservation> reservations = (ArrayList<Reservation>) getAllReservationsForABroadcast(broadcast);

			// TODO optimise with 1 query , dynamically apend ? to string
			for (Reservation reservation : reservations) {
				// do a select query WHERE reservation_id = reservation.getId
				ps = connection.prepareStatement("SELECT `row_number` , `column_number` FROM `reservations_seats` WHERE `ticket_reservations_id` = ?");
				ps.setInt(1, reservation.getId());
				ResultSet result = ps.executeQuery();
				// make a resultset and while through him and append to allSeats
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

	public void bookSelectedSeats(int row, int col, int ticketReservId) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = connection
					.prepareStatement("INSERT INTO reservations_seats (row_number,column_number,ticket_reservations_id) VALUES (?,?,?) ");
			ps.setInt(1, row);
			ps.setInt(2, col);
			ps.setInt(3, ticketReservId);
			ps.executeUpdate();

		} catch (SQLException e) {
			throw e;
		} finally {
			ps.close();
		}
	}

	public ArrayList<String> getAllReservationsForUser(User user) throws SQLException {
		ArrayList<String> allReservations = new ArrayList<String>();
		PreparedStatement ps = null;
		try {
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
		} catch (SQLException e) {
			throw e;
		} finally {
			ps.close();
		}
		return allReservations;

	}
}
