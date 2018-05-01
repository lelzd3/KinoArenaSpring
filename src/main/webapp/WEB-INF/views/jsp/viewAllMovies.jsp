<%@page import="com.kinoarena.model.dao.UserDao"%>
<%@page import="com.kinoarena.model.dao.BroadcastDao"%>
<%@page import="com.kinoarena.model.pojo.Broadcast"%>
<%@page import="com.kinoarena.model.dao.MovieDao"%>
<%@page import="com.kinoarena.model.pojo.Movie"%>
<%@page import="com.kinoarena.model.pojo.User"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
	//can also be User user = (User) session.getAttribute("user")
	User user = (User) request.getSession().getAttribute("user");
	//
	//	ArrayList<Movie> movies = (ArrayList<Movie>) application.getAttribute("movies");
	ArrayList<Movie> movies = (ArrayList<Movie>) MovieDao.getInstance().getAllMovies();
%>
</head>

<body>


	<form action="search" method="post">
		<button type="submit"
			class="w3-bar-item w3-button w3-padding-large w3-right w3-theme-d4">Search</button>
		<input type="text" id="search" name="movie"
			class="w3-bar-item w3-button w3-padding-large w3-right w3-theme-d4"
			required>
	</form>



	<%
		for (Movie movie : movies) {
	%>
	<br>
	<br>
	<div class="one">
		<strong><%=movie.getTitle()%></strong> <br>
		<br> <img
			src="getCover?file=<%=movie.getTitle() + ";" + movie.getId()%>"
			height="250" width="250"> <br>
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
			<input type="hidden" name="movieIdToBeRated"
				value="<%=movie.getId()%>"> <select name="ratingSelect">
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
			</select> <input type="submit" value="rateMovie">
		</form>
		
<<<<<<< HEAD

			<c:if test="${isMovieInFavourite == false}">
=======
		        <% if(UserDao.getInstance().isMovieInFavourite(user.getId() , movie.getId())){ %>
>>>>>>> 5c79bc933f4e103daff68aafade1f2970d24bbf3
				<form action = "<c:url value='/favourite/addInFavorite'/>" method = "get">
					<input type = "hidden" name = "value" value = "<% movie.getId();%>"/>
					<input type = "submit" value = "Add in favorites"/>
				</form>
				<% } else{ %>
			<form action = "<c:url value='/favourite/removeFromFavorite'/>" method="post">
				<input type = "submit" value = "Remove from favroites">
				<input type = "hidden" name = "value" value = "<% movie.getId();%>"/>
			</form> 
<<<<<<< HEAD
			</c:if>
=======
			<% }  %>
>>>>>>> 5c79bc933f4e103daff68aafade1f2970d24bbf3
		
		<br> <br>
		<form action="reserveInterim" method="post">
			<select name="broadcastSelect">
				<%
					//TODO make it to show Cinema Name and Movie Name
						//and make it to redirect to servlet
						for (Broadcast broadcast : (ArrayList<Broadcast>) BroadcastDao.getInstance()
								.getAllBroadcastsForAMovie(movie)) {
				%>
				<option value="<%=broadcast.getId()%>"><%="Cinema_id: " + broadcast.getCinemaId() + ", Broadast_id: " + broadcast.getId()
							+ ", Movie_id: " + broadcast.getMovieId()%></option>
				<%
					}
				%>
			</select> <input type="submit" value="Choose Broadcast to book seats for">
			
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