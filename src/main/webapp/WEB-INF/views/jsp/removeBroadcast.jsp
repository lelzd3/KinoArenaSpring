<%@page import="com.kinoarena.model.pojo.Broadcast"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Remove Broadcast page</title>
	</head>
	<body>
	
		
		
		<form action="removeBroadcast" method="post" id="removeBroadcastForm" name="removeBroadcastForm">
			<br>
			<select name="broadcastSelect">
				<c:forEach var="broadcast" items="${broadcasts}">
				<option value="${broadcast.id}">${broadcast.id} , ${broadcast.movieId} , ${broadcast.projectionTime} </option>
				</c:forEach>
			</select>
			<br>
			<input type="submit" value="removeBroadcast">
		</form> 
	
	</body>
</html>