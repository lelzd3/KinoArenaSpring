<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>
<%@page import="com.kinoarena.model.dao.BroadcastDao"%>
<%@page import="com.kinoarena.model.pojo.Broadcast"%>
<%@page import="java.util.LinkedHashSet"%>
<%@page import="com.kinoarena.model.dao.UserDao"%>
<%@page import="com.kinoarena.model.dao.MovieDao"%>
<%@page import="com.kinoarena.model.pojo.Movie"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.kinoarena.model.pojo.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Favorite movies</title>
		<%
			User user = (User) request.getSession().getAttribute("user");
			UserDao userDao = (UserDao)session.getAttribute("userDao");
			BroadcastDao broadcastDao = (BroadcastDao)session.getAttribute("broadcastDao");
			ArrayList<Movie> movies = (ArrayList<Movie>) userDao.viewFavourite(user);
			MovieDao movieDao = (MovieDao)session.getAttribute("movieDao");
		%>
		<style>
			.one{
				border: 1px solid darkslategray;
				font-size: 150%;
			}
		</style>
	</head>
	
	<body>
	<jsp:include page="header.jsp"/>
	
	<div class="wrapper bgded overlay light" >
		<div id="pageintro" class="hoc clear"> <!-- toq div gi prai da sa centralno -->
			<h1 align="center"><strong>YOUR FAVOURITE MOVIES</strong></h1>
			<br><br>
			
			<%
			for (Movie movie : movies) {
			%>
			<br>
			<br>
			<div class="one" align="center">
				<br>
				<h2 align="center"><strong><%=movie.getTitle()%></strong></h2>
				<br>
				<br> 
				<img src="getCover?file=<%=movie.getTitle() + ";" + movie.getId()%>" height="300" width="300"> <br>
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
				
				<br><br>
				<form action ="removeFromFavorite" method="post">
					<input type="hidden" name="hiddenJspName" value ="user_favourites">
					<input type = "hidden" name = "hiddenMovieId" value = "<%=movie.getId()%>"/>
					<input type = "image" src="img/favourite.png" style="width: 7.5%;" value = "Remove from favourites">
				</form> 
	
					<br>
					<ul>
					<%for(int i = 0 ; i < movieDao.getAllGenresForAMovie(movie.getId()).size(); i++){ %>
					<li><%=movieDao.getAllGenresForAMovie(movie.getId()).get(i)%></li>
					<%} %>
					</ul>			
				<br><br>
				<form action="reserveInterim" method="post">
					<select name="broadcastSelect">
					<%
					for (Broadcast broadcast : (ArrayList<Broadcast>) broadcastDao.getAllBroadcastsForAMovie(movie)) {
					%>
						<option value="<%=broadcast.getId()%>"><%="Cinema_id: " + broadcast.getCinemaId() + ", Broadast_id: " + broadcast.getId()+ ", Movie_id: " + broadcast.getMovieId()%></option>
					<%
					}
					%>
					</select> 
					<input type="submit" value="Choose Broadcast to book seats for">
				</form>
				<br>
		
			</div>
			<br>
			<br>
			<%
				}
			%>
		</div>
	</div>

	</body>
	
	<script>
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