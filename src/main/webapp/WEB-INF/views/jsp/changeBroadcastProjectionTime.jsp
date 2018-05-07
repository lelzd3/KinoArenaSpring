<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>
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
	<jsp:include page="header.jsp"/>
	
	<div class="wrapper bgded overlay light" >
		<div id="pageintro" class="hoc clear"> <!-- toq div gi prai da sa centralno -->
		
			<h1 align="center"><strong>Change Broadcast Projection Time</strong></h1>
			<br>
			<c:if test="${ message != null }">
	  		<h3 style="color: red; text-align: center">${ message }</h3>
			</c:if>
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
		</div>
	</div>	
	</body>
</html>