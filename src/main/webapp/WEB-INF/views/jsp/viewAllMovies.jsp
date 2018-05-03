<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>
<%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@page import="com.kinoarena.model.dao.UserDao"%>
<%@page import="com.kinoarena.model.dao.BroadcastDao"%>
<%@page import="com.kinoarena.model.pojo.Broadcast"%>
<%@page import="com.kinoarena.model.dao.MovieDao"%>
<%@page import="com.kinoarena.model.pojo.Movie"%>
<%@page import="com.kinoarena.model.pojo.User"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="ISO-8859-1">
		<script src="//code.jquery.com/jquery-1.10.2.js"></script>
		<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
		<link rel="stylesheet" 
		  href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css"><title>All Movies page</title>
	
		<style>
		.one {
			border: 1px solid darkslategray;
			font-size: 150%;
		}
		</style>
		<%
			User user = (User) session.getAttribute("user");
			
			MovieDao movieDao = (MovieDao)session.getAttribute("movieDao");
			UserDao userDao = (UserDao) session.getAttribute("userDao");
			BroadcastDao broadcastDao =  (BroadcastDao) session.getAttribute("broadcastDao");
			
			ArrayList<Movie> movies = (ArrayList<Movie>)movieDao.getAllMovies();
		%>
	</head>

	<body>

		<form action="search" method="post">
			<button type="submit" class="w3-bar-item w3-button w3-padding-large w3-right w3-theme-d4">Search</button>
			<input type="text" id="search" name="movie" class="w3-bar-item w3-button w3-padding-large w3-right w3-theme-d4" required>
		</form>
	
		<%
		for (Movie movie : movies) {
		%>
			<br>
			<br>
			<div class="one">
				<strong><%=movie.getTitle()%></strong>
				<br>
				<br> 
				<img src="getCover?file=<%=movie.getTitle() + ";" + movie.getId()%>" height="250" width="250"> <br>
				<br>
				<p><%=movie.getDescription()%></p>
				<br>
				<p>
					Duration:
					<%=movie.getDuration()%></p>
					Rating:
					<%=movie.getRating()%>
				<br>
				<form action="rateMovie" method="post">
					<input type="hidden" name="movieIdToBeRated"value="<%=movie.getId()%>">
					 <select name="ratingSelect">
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">6</option>
						<option value="7">7</option>
						<option value="8">8</option>
						<option value="9">9</option>
						<option value="10">10</option>
					</select>
					<input type="hidden" name="hiddenJspName" value ="viewAllMovies">
					<input type="submit" value="rateMovie">
				</form>
		
				<% if(!userDao.isMovieInFavourite(user.getId(),movie.getId())){ %>
		
				<form action = "addInFavorite" method = "post">
					<input type="hidden" name="hiddenJspName" value ="viewAllMovies">
					<input type = "hidden" name = "hiddenMovieId" value = "<%=movie.getId()%>"/>
					<input type = "submit" value = "Add in favorites"/>
				</form>
				<% } else{ %>
				<form action = "removeFromFavorite" method="post">
					<input type="hidden" name="hiddenJspName" value ="viewAllMovies">
					<input type = "hidden" name = "hiddenMovieId" value = "<%=movie.getId()%>"/>
					<input type = "submit" value = "Remove from favourites">
				</form> 
		
				<% }  %>
					
				<% if(!userDao.isMovieInWatchlist(user.getId(),movie.getId())){ %>
		
				<form action = "addInWatchlist" method = "post">
					<input type="hidden" name="hiddenJspName" value ="viewAllMovies">
					<input type = "hidden" name = "hiddenMovieId" value = "<%=movie.getId()%>"/>
					<input type = "submit" value = "Add in watchlist"/>
				</form>
				<% } else{ %>
				<form action = "removeFromWatchlist" method="post">
					<input type="hidden" name="hiddenJspName" value ="viewAllMovies">
					<input type = "hidden" name = "hiddenMovieId" value = "<%=movie.getId()%>"/>
					<input type = "submit" value = "Remove from watchlist">
				</form> 
		
				<% }  %>
				
				<br> <br>
				<form action="reserveInterim" method="post">
					<select name="broadcastSelect">
					<%
					for (Broadcast broadcast : (ArrayList<Broadcast>) broadcastDao.getAllBroadcastsForAMovie(movie)) {
					%>
						<option value="<%=broadcast.getId()%>"><%="Cinema_id: " + broadcast.getCinemaId() + ", Broadast_id: " + broadcast.getId() + ", Movie_id: " + broadcast.getMovieId()%></option>
					<%
					}
					%>
					</select>
					<input type="hidden" name="hiddenJspName" value ="viewAllMovies">
					<input type="submit" value="Choose Broadcast to book seats for">
						
				</form>
				<br>
			
			</div>
				<br>
				<br>
		<%
		}
		%>
	
	
	
	
		<script>
			//search
			$(document).ready(function() {
				$(function() {
					$("#search").autocomplete({
						source : function(request, response) {
							$.ajax({
								url : "searchAutoComplete",
								type : "GET",
								data : {
									term : request.term
								},
								dataType : "json",
								success : function(data) {
									response(data);
								}
							});
						}
					});
				});
			})
		</script>
	
	</body>

</html>