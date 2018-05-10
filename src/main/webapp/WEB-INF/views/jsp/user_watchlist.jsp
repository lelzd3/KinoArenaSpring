<%@page import="java.time.format.DateTimeFormatter"%>
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
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Watchlisted movies</title>
		<%
		User user = (User) session.getAttribute("user");
		
		UserDao userDao = (UserDao)application.getAttribute("userDao");
		BroadcastDao broadcastDao = (BroadcastDao)application.getAttribute("broadcastDao");
		MovieDao movieDao = (MovieDao)application.getAttribute("movieDao");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		
		ArrayList<Movie> movies = (ArrayList<Movie>) userDao.viewWatchlist(user);
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
			<h1 align="center"><strong>WATCHLISTED MOVIES</strong></h1>
			<br><br>
			<%
				if(movies.size()==0){
			%>
				<h1 align="center"><strong>You dont have any movies in watchlist yet!</strong></h1>			
			<%
			}
			else{
			%>		
		
		
			<%
			for (Movie movie : movies) {

				String genres =  movieDao.getAllGenresForAMovie(movie.getId()).toString();
				String genresToShow = genres.substring(1, genres.length()-1);
			%>
			<br>
			<br>
			<div class="one" align="center">
				<br>
				<h2 align="center"><strong><%=movie.getTitle()%></strong></h2>
				<br>
				<br> 
				<img src="getCover?file=<%=movie.getTitle() + ";" + movie.getId()%>" height="300" width="300" > <br>
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
				<form action ="removeFromWatchlist" method="post">
					<input type="hidden" name="hiddenJspName" value ="user_watchlist">
					<input type = "hidden" name = "hiddenMovieId" value = "<%=movie.getId()%>"/>
					<input type = "image" src="img/minus.png" style="width: 5%;" value = "Remove from watchlist">
				</form> 
				
				
				<br><br>
				Genres: <%=genresToShow%>
				<br><br>
						
				<form action="reserveInterim" method="post">
					<select name="broadcastSelect">
					<%
					for (Broadcast broadcast : (ArrayList<Broadcast>) broadcastDao.getAllBroadcastsForAMovie(movie)) {
					%>
						<option value="<%=broadcast.getId()%>"><%="Projection Time: " + broadcast.getProjectionTime().format(formatter) %></option>
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
			}}
			%>
			
		</div>	
	</div>
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
	
	</body>
</html>