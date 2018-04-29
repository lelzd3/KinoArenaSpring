package com.kinoarena.model.dao;

import com.kinoarena.model.pojo.Broadcast;
import com.kinoarena.model.pojo.Hall;
import com.kinoarena.model.pojo.Movie;
import com.kinoarena.model.pojo.User;

public interface IAdminDao {

	public void addNewMovie(Movie m , User admin) throws Exception;
	
	public void addNewBroadcast(Broadcast b ,User admin) throws Exception;
	
	public void removeMovie(Movie m , User admin) throws Exception;
	
	public void removeBroadcast(Broadcast b , User admin) throws Exception;
	
	public void changeUserIsAdminStatus(User admin, String email) throws Exception;
	
	public void changeUserIsBannedStatus(User admin, User u, boolean isBanned) throws Exception;
	
	// we will use it to make discount on some day/days of the week
	public void setPromoPercent(User admin, Broadcast b, double promoPercent) throws Exception;

	void addNewHall(Hall h, User admin) throws Exception;

	
	

}
