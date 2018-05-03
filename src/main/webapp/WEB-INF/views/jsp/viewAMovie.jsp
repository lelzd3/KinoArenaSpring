<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>
<%@page import="com.kinoarena.model.dao.UserDao"%>
<%@page import="com.kinoarena.model.dao.BroadcastDao"%>
<%@page import="com.kinoarena.model.pojo.Broadcast"%>
<%@page import="com.kinoarena.model.pojo.Movie"%>
<%@page import="com.kinoarena.model.pojo.User"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>View a movie</title>
		<%
			User user = (User) request.getSession().getAttribute("user");
			UserDao userDao = (UserDao)session.getAttribute("userDao");
			BroadcastDao broadcastDao = (BroadcastDao)session.getAttribute("broadcastDao");
			Movie movie =(Movie) request.getSession().getAttribute("selectedMovie");
		%>
		<style>
		.one{
			border: 1px solid darkslategray;
			font-size: 150%;
		}
		</style>
	
	</head>

<body>

<br><br>
			<div class="one">
				<strong><%= movie.getTitle() %></strong>
				<br><br>
				<img src="getCover?file=<%=movie.getTitle() + ";" + movie.getId()%>" height="250" width="250">
				<br><br>
				<p><%= movie.getDescription() %></p>
				<br>
				<p>Duration: <%=movie.getDuration() %></p>
				Rating: <%=movie.getRating() %>
				<br>
				<form action="rateMovie" method="post">
					<input type="hidden" name="movieIdToBeRated" value="<%= movie.getId() %>">
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
					<input type="hidden" name="hiddenJspName" value ="viewAMovie">
					<input type="submit" value="rateMovie">
				</form>
				<br>
				
			<% if(!userDao.isMovieInFavourite(user.getId(),movie.getId())){ %>

			<form action = "addInFavorite" method = "post">
				<input type="hidden" name="hiddenJspName" value ="viewAMovie">
				<input type = "hidden" name = "hiddenMovieId" value = "<%=movie.getId()%>"/>
				<input type = "submit" value = "Add in favorites"/>
			</form>
			<% } else{ %>
			<form action = "removeFromFavorite" method="post">
				<input type="hidden" name="hiddenJspName" value ="viewAMovie">
				<input type = "hidden" name = "hiddenMovieId" value = "<%=movie.getId()%>"/>
				<input type = "submit" value = "Remove from favourites">
			</form> 

			<% }  %>
			
			 <% if(!userDao.isMovieInWatchlist(user.getId(),movie.getId())){ %>

			<form action = "addInWatchlist" method = "post">
				<input type="hidden" name="hiddenJspName" value ="viewAMovie">
				<input type = "hidden" name = "hiddenMovieId" value = "<%=movie.getId()%>"/>
				<input type = "submit" value = "Add in watchlist"/>
			</form>
			<% } else{ %>
			<form action = "removeFromWatchlist" method="post">
				<input type="hidden" name="hiddenJspName" value ="viewAMovie">
				<input type = "hidden" name = "hiddenMovieId" value = "<%=movie.getId()%>"/>
				<input type = "submit" value = "Remove from watchlist">
			</form> 

			<% }  %>
				
			<br>
			<form action="reserveInterim" method="post">
				<select name="broadcastSelect">
				<%
				for(Broadcast broadcast : (ArrayList<Broadcast>)broadcastDao.getAllBroadcastsForAMovie(movie)) { %>
					<option value="<%= broadcast.getId() %>"><%="Cinema_id: "+  broadcast.getCinemaId() + ", Broadast_id: " + broadcast.getId() + ", Movie_id: "+ broadcast.getMovieId() %></option>
				<%} %>
			</select>
			<input type="submit" value="Choose Broadcast to book seats for">
		</form>
		<br>
				
		</div>
	<br><br>
</body>
</html>