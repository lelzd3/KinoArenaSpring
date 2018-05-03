package com.kinoarena.model.pojo;

import com.kinoarena.utilities.exceptions.InvalidDataException;


public class Hall {

	private static int MAX_SEATS = 80;
	private int id;
	private int seats;
	private int cinemaId;
	
	public Hall(int cinemaId) throws InvalidDataException{
		setSeats(MAX_SEATS);
		setCinemaId(cinemaId);
	}
	
	public Hall(int id, int cinemaId) throws InvalidDataException{
		this(cinemaId);
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
		this.seats = seats;
	}

	public void setCinemaId(int cinemaId) {
		this.cinemaId = cinemaId;
	}

	@Override
	public String toString() {
		return "Hall [id=" + id + ", seats=" + seats + ", cinemaId=" + cinemaId + "]";
	}
	

	
	
}
