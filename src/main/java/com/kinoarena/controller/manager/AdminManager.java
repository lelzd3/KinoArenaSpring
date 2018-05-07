package com.kinoarena.controller.manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
import com.kinoarena.utilities.exceptions.InvalidDataException;
import com.kinoarena.utilities.exceptions.NotAnAdminException;

@Component
public class AdminManager {

	@Autowired
	private MovieDao movieDao;
	
	@Autowired	
	private BroadcastDao broadcastDao;
	
	@Autowired
	private HallDao hallDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CinemaDao cinemaDao;
	
	//Singleton and DBconnection
	private static Connection connection;

	private AdminManager() {
		connection = DBManager.getInstance().getConnection();
	}
	
	public void addNewMovie(Movie m, User admin,ArrayList<String> genres) throws NotAnAdminException, SQLException {
			if(admin.getIsAdmin()){
			movieDao.addMovie(m,genres);
		}else{
			throw new NotAnAdminException();
		}
	}
	

	public void addNewBroadcast(Broadcast b,User admin) throws SQLException, NotAnAdminException, InvalidDataException {
		if(admin.getIsAdmin()){
			broadcastDao.addBroadcast(b);
		}else{
			throw new NotAnAdminException();
		}	
	}

	public void addNewHall(Hall h, User admin) throws SQLException, NotAnAdminException {
		if(admin.getIsAdmin()){
			hallDao.addHall(h);
		}else{
			throw new NotAnAdminException();
		}
	}
	
	public void addNewCinema(Cinema c, User admin) throws SQLException, NotAnAdminException {
		if(admin.getIsAdmin()){
			cinemaDao.addCinema(c);
		}else{
			throw new NotAnAdminException();
		}
	}


	public void removeMovie(Movie m, User admin) throws SQLException, NotAnAdminException {
		if(admin.getIsAdmin()){
			movieDao.deleteMovie(m);;
		}else{
			throw new NotAnAdminException();
		}	
	}


	public void removeBroadcast(Broadcast b, User admin) throws SQLException, NotAnAdminException, InvalidDataException {	
		if(admin.getIsAdmin()){
			broadcastDao.deleteBroadcast(b);;
		}else{
			throw new NotAnAdminException();
		}
	}
	
	public void removeCinema(Cinema c, User admin) throws SQLException, NotAnAdminException, InvalidDataException {	
		if(admin.getIsAdmin()){
			cinemaDao.deleteCinema(c);
		}else{
			throw new NotAnAdminException();
		}
	}

	public void removeHall(Hall h, User admin) throws SQLException, NotAnAdminException, InvalidDataException {	
		if(admin.getIsAdmin()){
			hallDao.deleteHall(h);
		}else{
			throw new NotAnAdminException();
		}
	}

	public void changeUserIsAdminStatus(User admin, String email) throws NotAnAdminException, SQLException, InvalidDataException {
		if(admin.getIsAdmin()){
			userDao.createAdmin(email.trim());
		}else{
			throw new NotAnAdminException();
		}
	}

	public void changeBroadcastPrice(User admin,int broadcastId, double newPrice) throws SQLException, NotAnAdminException, InvalidDataException {
		if(admin.getIsAdmin()){
			broadcastDao.changeBroadcastPrice(broadcastId, newPrice);
		}else{
			throw new NotAnAdminException();
		}
	}
	
	public void changeBroadcastProjectionTime(User admin,int broadcastId,LocalDateTime newProjectionTime) throws SQLException, NotAnAdminException {
		if(admin.getIsAdmin()){
			broadcastDao.changeBroadcastProjectionTime(broadcastId, newProjectionTime);
		}else{
			throw new NotAnAdminException();
		}
	}
	
	public void deleteGenresFromMovie(User admin,int movieId) throws SQLException, NotAnAdminException {
		if(admin.getIsAdmin()){
			movieDao.deleteGenresFromMovie(movieId);
		}else{
			throw new NotAnAdminException();
		}
	}
}
