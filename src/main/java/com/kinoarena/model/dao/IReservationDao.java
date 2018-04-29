package com.kinoarena.model.dao;

import java.util.ArrayList;
import java.util.Collection;

import com.kinoarena.model.pojo.Broadcast;
import com.kinoarena.model.pojo.Reservation;
import com.kinoarena.model.pojo.Seat;


public interface IReservationDao {

	public void addReservation(Reservation r,ArrayList<Seat> seats) throws Exception;
	
	public void deleteReservation(Reservation r) throws Exception;
	
	public Collection<Reservation> getAllReservationsForABroadcast(Broadcast b) throws Exception;

}
