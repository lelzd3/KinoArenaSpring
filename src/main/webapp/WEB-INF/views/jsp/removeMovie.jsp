<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>
<%@page import="com.kinoarena.model.dao.MovieDao"%>
<%@page import="com.kinoarena.model.pojo.Movie"%>
<%@page import="java.util.ArrayList"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Remove Movie page</title>
	</head>
	
	<body>
	<jsp:include page="header.jsp"/>
	
	<div class="wrapper bgded overlay light" >
		<div id="pageintro" class="hoc clear"> <!-- toq div gi prai da sa centralno -->
			
			<h1 align="center"><strong>Remove Movie</strong></h1>
			<br>
			<form action="removeMovie" method="post" >
				<br>
				<select name=movieSelect>
					<c:forEach var="movie" items="${movies}">
					<option value="${movie.id}">${movie.id} , ${movie.title} , ${movie.duration} </option>
					</c:forEach>
				</select>
				<br>
				<input type="submit" value="removeMovie">
			</form>
		</div>
	</div>
	</body>
</html>