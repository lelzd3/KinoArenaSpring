package com.kinoarena.model.pojo;

import com.kinoarena.utilities.exceptions.InvalidDataException;


public class Hall {

	private static int MAX_SEATS = 100;
	private int id;
	private int seats;
	private int cinemaId;
	
	public Hall(int seats,int cinemaId) throws InvalidDataException{
		setSeats(seats);
		setCinemaId(cinemaId);
	}
	
	public Hall(int id,int seats,int cinemaId) throws InvalidDataException{
		this(seats,cinemaId);
		setId(id);
	}

	//getters
	public int getId() {
		return id;
	}

	public int getSeats() {
		return seats;
	}

	public int getCinemaId() {
		return cinemaId;
	}
	
	
	//setters
	public void setId(int id) {
		this.id = id;
	}

	public void setSeats(int seats) throws InvalidDataException {
		if(seats > 0 && seats <= MAX_SEATS) {
			this.seats = seats;
		}
		else {
			throw new InvalidDataException("Invalid seats");
		}
	}

	public void setCinemaId(int cinemaId) {
		this.cinemaId = cinemaId;
	}

	@Override
	public String toString() {
		return "Hall [id=" + id + ", seats=" + seats + ", cinemaId=" + cinemaId + "]";
	}
	

	
	
}
