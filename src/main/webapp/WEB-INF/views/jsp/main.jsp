<%@page import="com.kinoarena.model.pojo.User"%>
<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Main Page</title>
		<%
			User user = (User) request.getSession().getAttribute("user"); 
			String s = session.getId();	
		%>
	</head>
	<body>
		<h1 align="center">Hello, <%= user.getUsername()  %></h1>
		<h2 align="center">Session id is <%= s %></h2>
		<br>
		
		<form action="viewAllMoviesPage" method="get">
			<input type="submit" value="Movies"><br>
		</form>
		<br>
		<form action="viewAllWatchlisterMoviesPage" method="get">
			<input type="submit" value="Movies in watchlist"><br>
		</form>
		<br>
		<form action = "<c:url value='/favourite/infoUserFavourites'/>" method="get">
			<input type="submit" value="Favorite movies"><br>
		</form>
		
		<br>
		<form action=logout method="get">
			<input type="submit" value="Logout"><br>
		</form>
		
		
	</body>
</html>