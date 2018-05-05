<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Add Movie page</title>
	</head>
	
	<body>
	
		<form action="adminMain" method="get">
			<input type="submit" value ="Back">
		</form>
		<br><br><br><br>
		
		<form method="post" action="addMovie" enctype="multipart/form-data">
			Title <input type="text" name="title" required>
			<br><br>
			Description <input type="text" name="description" required>
			<br><br>
			Duration <input type="number" name="duration" step="any" required>
			<br><br>
			File <input type="file" accept="image/*" name="file">
			<br><br>
			Select genres 
			<select name="genresSelect" multiple>
				<c:forEach var="genre" items="${genres}"  >
			 		 <option value="${genre}">${genre}</option>
				</c:forEach>
			</select>
			
			<input type="submit">
		</form>
		
	</body>
</html>