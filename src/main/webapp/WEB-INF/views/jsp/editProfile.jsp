<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Edit profile</title>
	</head>
	
	<body>
	<jsp:include page="header.jsp"/>
	
	<div class="wrapper bgded overlay light" >
		<div id="pageintro" class="hoc clear"> <!-- toq div gi prai da sa centralno -->
		
			<h1 align="center"><strong>View and change your details</strong></h1>
			<br>
			
			<form action="edit" method="post" id="editProfileForm">
				<br><br>
				Current password
	            <input name="oldPassword" type="password" value="">
	            <br><br>
				New password
	            <input name="newPassword" type="password" value="">
	            <br><br>
				Confirm new password
	            <input name="confirmPassword" type="password" value="">
	            <br><br>
				First name
	            <input name="firstName" type="text" value="${user.firstname}">
	            <br><br>
				Last name
	            <input name="lastName" type="text" value="${user.lastname}">
	            <br><br>
				Email
	            <input name="email" type="text" value="${user.email}">
	            <br><br>
	            <input type="submit" class="button" value="Edit">
	        </form>
    
		</div>
	</div>
	</body>
</html>