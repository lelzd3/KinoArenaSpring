<%@page import="com.kinoarena.model.pojo.Broadcast"%>
<%@page import="com.kinoarena.model.pojo.User"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Set discount on a broadcast </title>
		<%
			User admin = (User) request.getSession().getAttribute("admin");	
			ArrayList<Broadcast> broadcasts = (ArrayList<Broadcast>) application.getAttribute("broadcasts");
		%>
		</head>
	<body>
		
		<form action="adminMain" method="get">
			<input type="submit" value ="Back">
		</form>
		<br><br><br><br>
		
		<form action="setDiscount" method="post" id="setDiscountForm" name="setDiscountForm">
			<br>
			Select Broadcast
			<select name="broadcastSelect">
				<% for(Broadcast b : broadcasts){ %>
			 		 <option value="<%= b.getId() %>"><%= b.getId() +" "+ b.getMovieId() + " " + b.getProjectionTime()+" " %></option>
				<% } %>
			</select>
			<br>
			Discount Percent <input type="number" name="percent" step="any" required>
			<input type="submit" value="setDiscount">
		<form>
		
	</body>
</html>