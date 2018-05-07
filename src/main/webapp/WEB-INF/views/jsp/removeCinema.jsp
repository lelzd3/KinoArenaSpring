<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>
<%@page import="com.kinoarena.model.dao.CinemaDao"%>
<%@page import="com.kinoarena.model.pojo.Cinema"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Remove Cinema page</title>
	</head>
	
	<body>
	<jsp:include page="header.jsp"/>
	
	<div class="wrapper bgded overlay light" >
		<div id="pageintro" class="hoc clear"> <!-- toq div gi prai da sa centralno -->
		
			<h1 align="center"><strong>Remove Cinema</strong></h1>
			<br>
			<form action="removeCinema" method="post" >
				<br>
				<select name=cinemaSelect>
					<c:forEach var="cinema" items="${cinemas}">
					<option value="${cinema.id}">${cinema.id} , ${cinema.name} , ${cinema.address} </option>
					</c:forEach>
				</select>
				<br>
				<input type="submit" value="removeCinema">
			</form> 
		</div>
	</div>
	</body>
</html>