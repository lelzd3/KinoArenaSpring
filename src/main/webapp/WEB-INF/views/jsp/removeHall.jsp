<%@page import="com.kinoarena.model.pojo.Hall"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Remove Hall page</title>

	</head>
	
	<body>
	
		<form action="removeHall" method="post" >
			<br>
			<select name=hallSelect>
				<c:forEach var="hall" items="${halls}">
				<option value="${hall.id}">${hall.id} , ${hall.cinemaId}</option>
				</c:forEach>
			</select>
			<br>
			<input type="submit" value="removeHall">
		</form> 
	
	</body>
</html>