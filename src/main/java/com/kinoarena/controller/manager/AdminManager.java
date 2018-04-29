package com.kinoarena.controller.manager;

import java.sql.Connection;
import java.sql.SQLException;

import com.kinoarena.model.dao.BroadcastDao;
import com.kinoarena.model.dao.CinemaDao;
import com.kinoarena.model.dao.HallDao;
import com.kinoarena.model.dao.MovieDao;
import com.kinoarena.model.dao.UserDao;
import com.kinoarena.model.pojo.Broadcast;
import com.kinoarena.model.pojo.Cinema;
import com.kinoarena.model.pojo.Hall;
import com.kinoarena.model.pojo.Movie;
import com.kinoarena.model.pojo.User;
import com.kinoarena.utilities.exceptions.IlligalAdminActionException;
import com.kinoarena.utilities.exceptions.InvalidDataException;
import com.kinoarena.utilities.exceptions.NotAnAdminException;

public class AdminManager {


	//Singleton and DBconnection
	private static Connection connection;
	private static AdminManager instance;

	public synchronized static  AdminManager getInstance() {
		if (instance == null) {
			instance = new AdminManager();
		}
		return instance;
	}

	private AdminManager() {
		connection = DBManager.getInstance().getConnection();
	}
	
	public void addNewMovie(Movie m, User admin) throws SQLException, NotAnAdminException {
		if(admin.getIsAdmin()){
			MovieDao.getInstance().addMovie(m);
		}else{
			throw new NotAnAdminException();
		}
	}
	

	public void addNewBroadcast(Broadcast b,User admin) throws SQLException, NotAnAdminException, InvalidDataException {
		if(admin.getIsAdmin()){
			BroadcastDao.getInstance().addBroadcast(b);
		}else{
			throw new NotAnAdminException();
		}	
	}

	public void addNewHall(Hall h, User admin) throws SQLException, NotAnAdminException {
		if(admin.getIsAdmin()){
			HallDao.getInstance().addHall(h);
		}else{
			throw new NotAnAdminException();
		}
	}
	
	public void addNewCinema(Cinema c, User admin) throws SQLException, NotAnAdminException {
		if(admin.getIsAdmin()){
			CinemaDao.getInstance().addCinema(c);
		}else{
			throw new NotAnAdminException();
		}
	}


	public void removeMovie(Movie m, User admin) throws SQLException, NotAnAdminException {
		if(admin.getIsAdmin()){
			MovieDao.getInstance().deleteMovie(m);;
		}else{
			throw new NotAnAdminException();
		}	
	}


	public void removeBroadcast(Broadcast b, User admin) throws SQLException, NotAnAdminException, InvalidDataException {	
		if(admin.getIsAdmin()){
			BroadcastDao.getInstance().deleteBroadcast(b);;
		}else{
			throw new NotAnAdminException();
		}
	}
	
	public void removeCinema(Cinema c, User admin) throws SQLException, NotAnAdminException, InvalidDataException {	
		if(admin.getIsAdmin()){
			CinemaDao.getInstance().deleteCinema(c);
		}else{
			throw new NotAnAdminException();
		}
	}

	public void removeHall(Hall h, User admin) throws SQLException, NotAnAdminException, InvalidDataException {	
		if(admin.getIsAdmin()){
			HallDao.getInstance().deleteHall(h);
		}else{
			throw new NotAnAdminException();
		}
	}

	public void changeUserIsAdminStatus(User admin, String email) throws NotAnAdminException, SQLException, InvalidDataException {
		if(admin.getIsAdmin()){
			UserDao.getInstance().createAdmin(email.trim());
		}else{
			throw new NotAnAdminException();
		}
	}

	//not used
	public void changeUserIsBannedStatus(User admin, User u, boolean isBanned) throws NotAnAdminException, SQLException, IlligalAdminActionException {
		if(admin.getIsAdmin()){
			UserDao.getInstance().changeUserIsBannedStatus(u, isBanned);
		}else{
			throw new NotAnAdminException();
		}
	}


	public void setPromoPercent(User admin, Broadcast b, double promoPercent) throws SQLException, NotAnAdminException {
		if(admin.getIsAdmin()){
			BroadcastDao.getInstance().setPromoPercent(b, promoPercent);
		}else{
			throw new NotAnAdminException();
		}
	}
	
}
