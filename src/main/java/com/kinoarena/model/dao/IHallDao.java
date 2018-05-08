package com.kinoarena.model.dao;

import java.util.Collection;

import com.kinoarena.model.pojo.Cinema;
import com.kinoarena.model.pojo.Hall;

public interface IHallDao {

	public void addHall(Hall hall) throws Exception;

	public void deleteHall(Hall hall) throws Exception;

	public Collection<Hall> getAllHalls() throws Exception;

	public Collection<Hall> getAllHallsForACinema(Cinema cinema) throws Exception;

	public Hall getHallById(int id) throws Exception;
	
}
