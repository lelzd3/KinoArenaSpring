package com.kinoarena.model.dao;

import java.time.LocalDateTime;
import java.util.Collection;

import com.kinoarena.model.pojo.Broadcast;
import com.kinoarena.model.pojo.Movie;

public interface IBroadcastDao {

	public void deleteBroadcast(int broadcastId) throws Exception;
	
	public void changeBroadcastProjectionTime(int broadcastId,LocalDateTime projectionTime) throws Exception;

	void addBroadcast(Broadcast b) throws Exception;

	public Collection<Broadcast> getAllBroadcastsForAMovie(Movie m) throws Exception;
	
	public Collection<Broadcast> getAllBroadcasts() throws Exception;
	
	public Broadcast getBroadcastById(int id) throws Exception;
	
	public void changeBroadcastPrice(int id,double newPrice) throws Exception;
	
	
}
