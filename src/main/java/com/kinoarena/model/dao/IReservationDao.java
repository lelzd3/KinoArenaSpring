package com.kinoarena.model.dao;

import java.util.ArrayList;
import java.util.Collection;

import com.kinoarena.model.pojo.Broadcast;
import com.kinoarena.model.pojo.Reservation;
import com.kinoarena.model.pojo.Seat;
import com.kinoarena.model.pojo.User;


public interface IReservationDao {

	public void addReservation(Reservation r,ArrayList<Seat> seats) throws Exception;
	
	public void deleteReservation(String reservationId) throws Exception;
	
	public Collection<Reservation> getAllReservationsForABroadcast(Broadcast broadcast) throws Exception;

	public ArrayList<String> getAllOccupiedSeatsForABroadcast(Broadcast broadcast) throws Exception;

	public void bookSelectedSeats(int row, int col, int ticketReservId) throws Exception;

	public ArrayList<String> getAllReservationsForUser(User user) throws Exception;

}
