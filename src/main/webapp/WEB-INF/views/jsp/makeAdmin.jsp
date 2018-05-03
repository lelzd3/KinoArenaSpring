<%@page import="com.kinoarena.model.pojo.User"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Make someone an admin</title>
		<%
			User admin = (User) request.getSession().getAttribute("admin");
			//TODO make it so it gets all USERS- not admin
			ArrayList<User> users = (ArrayList<User>) application.getAttribute("usersButNotAdmins");	
		%>
	</head>
	<body>
		
		<form action="makeAdmin" method="post" id="makeAdminForm" name="makeAdminForm">
			<br>
			<select name="usersSelect">
				<c:forEach var="user" items="${usersThatAreNotAdmin}"  >
			 		 <option value="${user.id}">${user.username}</option>
				</c:forEach>
			</select>
			<br>
			<input type="submit" value="makeAdmin">
		</form>
	</body>
</html>