package com.kinoarena.model.pojo;

import java.time.LocalDateTime;

import com.kinoarena.utilities.exceptions.InvalidDataException;

public class Broadcast {

	private int id;
	private int cinemaId;
    private int movieId;
    private int hallId;
    private LocalDateTime projectionTime;
	private int freePlaces;
	private double price;
	
	private static final int TOTAL_PLACES_IN_HALL=80;

	public Broadcast(int cinemaId, int movieId, int hallId, LocalDateTime projectionTime,double price) throws InvalidDataException {
		setCinemaId(cinemaId);
		setMovieId(movieId);
		setHallId(hallId);
		setProjectionTime(projectionTime);
		setPrice(price);
		this.freePlaces = TOTAL_PLACES_IN_HALL;
	}
	
	public Broadcast(int id, int cinemaId, int movieId, int hallId, LocalDateTime projectionTime,double price) throws InvalidDataException {
		this(cinemaId, movieId, hallId, projectionTime,price);
		setId(id);
	}

	//all setters:
	public void setId(int id) {
		this.id = id;
	}
	
	public void setMovieId(int movieId) throws InvalidDataException  {
		this.movieId = movieId;
	}
	
	public void setCinemaId(int cinemaId) throws InvalidDataException {
		this.cinemaId = cinemaId;
	}

	public void setFreePlaces(int freePlaces) throws InvalidDataException {
		if(freePlaces<0) {
			throw new InvalidDataException("Oops , exception in broadcast pojo");
		}
		this.freePlaces = freePlaces;
	}

	public void setProjectionTime(LocalDateTime projectionTime) throws InvalidDataException {
		if(projectionTime == null) {
			throw new InvalidDataException("Oops , exception in broadcast pojo");
		}
		this.projectionTime = projectionTime;
	}

	public void setHallId(int hallId) throws InvalidDataException {
		
		this.hallId = hallId;
	}
	
	public void setPrice(double price) throws InvalidDataException {
		if(price < 0) {
			throw new InvalidDataException("Oops , exception in broadcast pojo");
		}
		this.price = price;
	}


	//all getters:
	public int getId() {
		return id;
	}
	
	public int getCinemaId() {
		return cinemaId;
	}

	public int getMovieId() {
		return movieId;
	}

	public int getFreePlaces() {
		return freePlaces;
	}

	public LocalDateTime getProjectionTime() {
		return projectionTime;
	}

	public int getHallId() {
		return hallId;
	}

	public double getPrice() {
		return price;
	}
	
	@Override
	public String toString() {
		return "Broadcast [id=" + id + ", cinemaId=" + cinemaId + ", movieId=" + movieId + ", hallId=" + hallId
				+ ", projectionTime=" + projectionTime + ", freePlaces=" + freePlaces + "]";
	}

}
