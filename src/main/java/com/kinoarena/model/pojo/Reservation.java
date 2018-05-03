package com.kinoarena.model.pojo;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.kinoarena.utilities.exceptions.InvalidDataException;


public class Reservation {
	//TODO change later
	private static final int MAX_SEATS_FOR_A_RESERVATIONS = 80000;
	private int id;
	private int userId;
	private int broadcastId;
	private int seatsNumber;
	private ArrayList<Seat> allSeatsReserved;
	private LocalDateTime timeReservationIsMade;
	
	
	public Reservation(int userId, int broadcastId) {
		setUserId(userId);
		setBroadcastId(broadcastId);
		this.allSeatsReserved = new ArrayList<Seat>();
		this.timeReservationIsMade = LocalDateTime.now();
	}
	
	public Reservation(int userId, int broadcastId, ArrayList<Seat> allSeatsReserved) throws InvalidDataException {
		setUserId(userId);
		setBroadcastId(broadcastId);
		setAllSeatsReserved(allSeatsReserved);
		setSeatsNumber(allSeatsReserved.size());
		this.timeReservationIsMade = LocalDateTime.now();
	}
	
	public Reservation(int id,int userId, int broadcastId, ArrayList<Seat> allSeatsReserved) throws InvalidDataException {
		this(userId, broadcastId, allSeatsReserved);
		setId(id);
	}

	public Reservation(int id, int userId, int broadcastId, ArrayList<Seat> allSeatsReserved,LocalDateTime timeReservationIsMade) throws InvalidDataException {
		setId(id);
		setUserId(userId);
		setBroadcastId(broadcastId);
		setAllSeatsReserved(allSeatsReserved);
		setSeatsNumber(allSeatsReserved.size());
		setTimeReservationIsMade(timeReservationIsMade);
	}
	
	public Reservation(int id, int userId, int broadcastId,int seats_number,LocalDateTime timeReservationIsMade) throws InvalidDataException {
		setId(id);
		setUserId(userId);
		setBroadcastId(broadcastId);
		setSeatsNumber(seats_number);
		setTimeReservationIsMade(timeReservationIsMade);
	}

	//getters:
	
	public int getId() {
		return id;
	}
	
	
	public int getUserId() {
		return userId;
	}
	
	public int getBroadcastId() {
		return broadcastId;
	}

	public LocalDateTime getTimeReservationIsMade() {
		return timeReservationIsMade;
	}

	public ArrayList<Seat> getAllSeatsReserved() {
		return allSeatsReserved;
	}
	
	public int getSeatsNumber() {
		return seatsNumber;
	}
	
	//setters:

	public void setId(int id) {
		this.id = id;
	}
	
	public void setBroadcastId(int broadcastId) {
		this.broadcastId = broadcastId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public void setTimeReservationIsMade(LocalDateTime timeReservationIsMade) throws InvalidDataException {
		if(timeReservationIsMade == null) {
			throw new InvalidDataException("Invalid data entered in reservation pojo");
		}
		this.timeReservationIsMade = timeReservationIsMade;
	}


	public void setAllSeatsReserved(ArrayList<Seat> allSeatsReserved) throws InvalidDataException {
		if(allSeatsReserved == null || allSeatsReserved.isEmpty()) {
			throw new InvalidDataException("Oops, error in setting reserved seats in reservation ");
		}
		this.allSeatsReserved = allSeatsReserved;
	}
	

	public void setSeatsNumber(int seatsNumber) throws InvalidDataException {
		if(seatsNumber < 0 || seatsNumber > MAX_SEATS_FOR_A_RESERVATIONS) {
			throw new InvalidDataException("Invalid number of seats");
		}
		else {
			this.seatsNumber = seatsNumber;
		}
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", userId=" + userId + ", broadcast_id=" + broadcastId + ", seats_number="
				+ seatsNumber + ", timeReservationIsMade=" + timeReservationIsMade + "]";
	}


	


	


	

	


	

}
