package com.kinoarena.model.dao;

import java.util.Collection;

import com.kinoarena.model.pojo.Cinema;

public interface ICinemaDao {

	public void addCinema(Cinema cinema) throws Exception;

	public void deleteCinema(Cinema cinema) throws Exception;

	public Collection<Cinema> getAllCinemas() throws Exception;

	public Cinema getCinemaById(int id) throws Exception;

}
