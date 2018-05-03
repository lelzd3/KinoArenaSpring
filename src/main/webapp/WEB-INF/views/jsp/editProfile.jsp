<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit profile</title>
</head>
<body>

	<h1> View and change your details</h1>
		<form action="edit" method="post" id="editProfileForm">
			<br>
			Current password
            <input name="oldPassword" type="password" value="">
            <br>
			New password
            <input name="newPassword" type="password" value="">
            <br>
			Confirm new password
            <input name="confirmPassword" type="password" value="">
            <br>
			First name
            <input name="firstName" type="text" value="${user.firstname}">
            <br>
			Last name
            <input name="lastName" type="text" value="${user.lastname}">
            <br>
			Email
            <input name="email" type="text" value="${user.email}">
            <br><br>
            <input type="submit" value="Edit">
        </form>
        
        <form action="cancel" method="get" id="cancelForm">
        <br><br>
        	<input type="submit" value="Back">
        </form>

</body>
</html>