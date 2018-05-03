<%@page import="com.kinoarena.model.pojo.Hall"%>
<%@page import="com.kinoarena.model.pojo.Cinema"%>
<%@page import="com.kinoarena.model.pojo.Movie"%>
<%@page import="com.kinoarena.model.pojo.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Add Broadcast page</title>
	</head>
	
	<body>
	
		<form action="adminMain" method="get">
			<input type="submit" value ="Back">
		</form>
		<br><br><br><br>
		
		<form action="addBroadcast" method="post" id="addBroadcastForm" name="addBroadcastForm">
			<br>
			Select Movie
			<select name="movieSelect">
				<c:forEach var="movie" items="${movies}"  >
			 		 <option value="${movie.id}">${movie.title}</option>
				</c:forEach>
			</select>
			<br>
			Select Cinema
			<select name="cinemaSelect">
				<c:forEach var="cinema" items="${cinemas}"  >
			 		 <option value="${cinema.id}">${cinema.name} , ${cinema.id}</option>
				</c:forEach>
			</select>
			<br>
			Select Hall
			<select name="hallSelect">
				<c:forEach var="hall" items="${halls}"  >
			 		 <option value="${hall.id}">${hall.id} , ${hall.cinemaId}</option>
				</c:forEach>
			</select>
			<br>
			Date and Time <input type="datetime-local" name="projection_time" required>
			<br>
			Price <input type="number" name= "price" step="any" required>
			<br>
			<input type="submit" value="addBroadcast">
		</form> 	
		<br>
		
	</body>
</html>