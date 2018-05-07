<%@page import="com.kinoarena.model.pojo.User"%>
<%@page import="java.util.ArrayList"%>
<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Admin Page</title>
		<%
			User admin = (User) request.getSession().getAttribute("admin"); 
			String s = session.getId();
		%>
	</head>
	
	<body>
	
		<h1 align="center">Hello admin, <%= admin.getUsername()  %></h1>
		<h2 align="center">Session id is <%= s %></h2>
		<br>
		<c:if test="${ message != null }">
	  		<h3 style="color: red; text-align: center">${ message }</h3>
		</c:if>	

		<form action="addMoviePage" method="get">
			<button type="submit">Add Movie</button>
		</form>
		<br>
		
		<form action="addHallPage" method="get">
			<button type="submit">Add Hall</button>
		</form>
		<br>
		
		<form action="addCinemaPage" method="get">
			<button type="submit">Add Cinema</button>
		</form>
		<br>
		
		<form action="addBroadcastPage" method="get">
			<button type="submit">Add Broadcast</button>
		</form>
		<br>
		
		<form action="removeBroadcastPage" method="get">
			<button type="submit">Remove Broadcast</button>
		</form>
		<br>
		
		<form action="removeMoviePage" method="get">
			<button type="submit">Remove Movie</button>
		</form>
		<br>
		
		<form action="removeCinemaPage" method="get">
			<button type="submit">Remove Cinema</button>
		</form>
		<br>
		
		<form action="removeHallPage" method="get">
			<button type="submit">Remove Hall</button>
		</form>
		<br>
		
		<form action="makeAdminPage" method="get">
			<button type="submit">Make someone an admin</button>
		</form>
		<br>
		
		<form action="changeBroadcastPricePage" method="get">
			<button type="submit">Change price on a broadcast</button>
		</form>
		<br>
		
		<form action="changeBroadcastProjectionTimePage" method="get">
			<button type="submit">Change price on a broadcast</button>
		</form>
		<br>
		
		<form action="logout" method="get">
			<input type="submit" value="Logout"><br>
		</form>
	
	</body>
</html>