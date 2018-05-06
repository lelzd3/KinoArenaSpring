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
		<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css"><title>All Movies page</title>
		<script src="//code.jquery.com/jquery-1.10.2.js"></script>
		<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
		
		<style>
		.one {
			border: 1px solid darkslategray;
			font-size: 150%;
			border-style: outset;
			margin-bottom: 5%;
		}
		</style>
		<%
			User user = (User) session.getAttribute("user");
			
			MovieDao movieDao = (MovieDao)session.getAttribute("movieDao");
			BroadcastDao broadcastDao =  (BroadcastDao) session.getAttribute("broadcastDao");
			
			ArrayList<Movie> movies = (ArrayList<Movie>)movieDao.getAllMovies();
			ArrayList<Integer> favMovies = movieDao.getAllFavouriteMovieIdsForUser(user.getId());
			ArrayList<Integer> watchMovies = movieDao.getAllWatchlistMovieIdsForUser(user.getId());
		%>
		
	</head>

	<body>
	
	<jsp:include page="header.jsp"/>
	
	<div class="wrapper bgded overlay light" >
		<div id="pageintro" class="hoc clear"> <!-- toq div gi prai da sa centralno -->
			<h1 align="center"><strong>ALL MOVIES</strong></h1>
			<form action="search" method="post">
				<button type="submit" class="w3-bar-item w3-button w3-padding-large w3-right w3-theme-d4">Search</button>
				<input type="text" id="search" name="movie" class="w3-bar-item w3-button w3-padding-large w3-right w3-theme-d4" required>
			</form>
			<br><br><br><br>
		
			<%
			for (Movie movie : movies) {
			%>
			<div class="one">
				<br>
				<h2 align="center"><strong><%=movie.getTitle()%></strong></h2>
				<br>
				<br> 
				<img src="getCover?file=<%=movie.getTitle() + ";" + movie.getId()%>" height="500" width="500" style="margin-left: 25%"> <br>
				<br>
				<p><%=movie.getDescription()%></p>
				<br>
				Duration: <%=movie.getDuration()%>
				<br><br>
				<div id="l<%=movie.getId()%>">Rating: <%=movie.getRating()%></div>
				<br>
				
				 <select id="r<%=movie.getId()%>" name="ratingSelect"> 
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

				<input type="button" onclick="rateMovieClick(<%=movie.getId()%>)" value="Rate Movie">
	
				<br>
				<ul>
				<%for(int i = 0 ; i < movieDao.getAllGenresForAMovie(movie.getId()).size(); i++){ %>
				<li><%=movieDao.getAllGenresForAMovie(movie.getId()).get(i)%></li>
				<%} %>
				</ul>
		
				
				<% if(!favMovies.contains(movie.getId())){ %>
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
					
				<% if(!watchMovies.contains(movie.getId())){ %>
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
				<br>
			</div>
			<%
			}
			%>
		</div>
	</div>
	</body>

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
			
			
			//elementId is the select field id which is like  "r<movie.id>"
			//ratingFieldId is the field id, which shows the rating, which is like "l<movie.id>"
			function rateMovieClick(movieId){
				var elementId = "r"+movieId; 
				var newRating = document.getElementById(elementId).value;
				
				var xmlHttp = new XMLHttpRequest();
				xmlHttp.onreadystatechange = function(){
					if(xmlHttp.readyState == 4 && xmlHttp.status == 200){

						var respText = xmlHttp.responseText;
						
						var ratingFieldId = "l"+movieId;
						
						document.getElementById(ratingFieldId).innerHTML = "Rating: "+respText;
					}
				}
				
				xmlHttp.open("POST","rateMovie");
				xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
				xmlHttp.send("id="+movieId+"&rating="+newRating);
			}
		</script>
</html>