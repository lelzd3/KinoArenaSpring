<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Change broadcast price page</title>
	</head>
	
	<body>
		
		<form action="adminMain" method="get">
			<input type="submit" value ="Back">
		</form>
		<br><br><br><br>
		
		<form action="changeBroadcastProjectionTime" method="post">
			<br>
			Select Broadcast
			<select name="broadcastSelect">
				<c:forEach var="broadcast" items="${broadcasts}">
					<option value="${broadcast.id}">${broadcast.id} , ${broadcast.movieId} , ${broadcast.projectionTime}</option>
				</c:forEach>
			</select>
			<br>
			Date and Time <input type="datetime-local" name="newProjectionTime" required>
			<input type="submit" value="Change Price">
		</form>
		
	</body>
</html>