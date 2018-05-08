package com.kinoarena.controller;

import java.sql.SQLException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.kinoarena.model.dao.BroadcastDao;
import com.kinoarena.model.dao.CinemaDao;
import com.kinoarena.model.dao.HallDao;
import com.kinoarena.model.dao.MovieDao;
import com.kinoarena.model.dao.ReservationDao;
import com.kinoarena.model.dao.UserDao;

@Component
public class StartUpController {
    @Autowired
    ServletContext context;

	@Autowired
	private MovieDao movieDao;
	
	@Autowired	
	private BroadcastDao broadcastDao;
	
	@Autowired
	private HallDao hallDao;
	
	@Autowired
	private CinemaDao cinemaDao;
	
	@Autowired
	private ReservationDao reservationDao;
	
	@Autowired
	private UserDao userDao;

  @EventListener(ContextRefreshedEvent.class)
  public void contextRefreshedEvent() throws SQLException {
	  context.setAttribute("movieDao",movieDao);
	  context.setAttribute("userDao",userDao);
	  context.setAttribute("broadcastDao",broadcastDao);
	  context.setAttribute("reservationDao", reservationDao);
	  context.setAttribute("cinemaDao", cinemaDao);
	  context.setAttribute("hallDao", hallDao);
	  context.setAttribute("genres", movieDao.getAllGenres());
  }
}