<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>
<%@page import="com.kinoarena.model.pojo.User"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Make someone an admin</title>
	</head>
	<body>
		
		<form action="adminMain" method="get">
			<input type="submit" value ="Back">
		</form>
		<br><br><br><br>
		
		<form action="makeAdmin" method="post" id="makeAdminForm" name="makeAdminForm">
			<br>
			<select name="usersSelect">
				<c:forEach var="user" items="${usersThatAreNotAdmin}"  >
			 		 <option value="${user.email}">${user.username}</option>
				</c:forEach>
			</select>
			<br>
			<input type="submit" value="makeAdmin">
		</form>
	</body>
</html>