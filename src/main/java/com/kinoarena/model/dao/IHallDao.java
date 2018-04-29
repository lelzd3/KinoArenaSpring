package com.kinoarena.model.dao;

import java.util.Collection;

import com.kinoarena.model.pojo.Cinema;
import com.kinoarena.model.pojo.Hall;

public interface IHallDao {

	public void addHall(Hall h) throws Exception;

	public void deleteHall(Hall h) throws Exception;

	public Collection<Hall> getAllHalls() throws Exception;

	public Collection<Hall> getAllHallsForACinema(Cinema c) throws Exception;

	public Hall getHallById(int id) throws Exception;
	
}
