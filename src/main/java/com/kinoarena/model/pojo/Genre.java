package com.kinoarena.model.pojo;

import com.kinoarena.utilities.exceptions.InvalidDataException;

public class Genre {

	private int movieId;
	private int genreId;
	
	
	public Genre(int movieId, int genreId) throws InvalidDataException {
		setMovieId(movieId);
		setGenreId(genreId);
	}
	
	
	public int getMovieId() {
		return movieId;
	}
	
	public void setMovieId(int movieId) throws InvalidDataException {
		
		if(movieId<0) {
			throw new InvalidDataException("Not valid movie id entered");
		}
		this.movieId = movieId;
		
	}
	
	public int getGenreId() {
		
		return genreId;
	}
	
	public void setGenreId(int genreId) throws InvalidDataException {
		if(genreId<0) {
			throw new InvalidDataException("Not valid movie id entered");
		}
		this.genreId = genreId;
	}
	
	
}
