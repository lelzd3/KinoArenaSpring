<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Add Cinema</title>
		
	</head>
	
	<body>
	<jsp:include page="header.jsp"/>
	
	<div class="wrapper bgded overlay light" >
		<div id="pageintro" class="hoc clear"> <!-- toq div gi prai da sa centralno -->
			
			<h1 align="center"><strong>Add Cinema</strong></h1>
			<br>
			<form action="addCinema" method="post">		
				Name <input type="text" name="name" required maxlength="45">
				<br><br>
				Address <input type="text" name="address" required maxlength="60">
				<br><br>
				<input type="submit" value="addCinema">
			</form> 	

		</div>
	</div>		
	</body>
</html>