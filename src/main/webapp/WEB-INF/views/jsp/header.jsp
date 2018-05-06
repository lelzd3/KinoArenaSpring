<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Header jsp</title>
		
		<link href="css/layout.css" rel="stylesheet" type="text/css">
		<script src="js/jquery.backtotop.js"></script>
		<script src="js/jquery.mobilemenu.js"></script>
	</head>
	
	<body>
	
		<div class="wrapper row0">
		  <nav id="mainav" class="hoc clear"> 
		    <!-- ################################################################################################ -->
		    <ul class="clear">
			    <li class="active"><a href="getTestMain">HOME</a></li>
			    <c:if test="${empty sessionScope.user}">
		    		<li><a href="viewAllMoviesPage">ADMIN PANEL</a></li>
		    	</c:if>
			    <li><a href="viewAllMoviesPage">ALL MOVIES</a></li>
			    <li><a href="viewUserFavourites">YOUR FAVOURITES</a></li>
			    <li><a href="viewUserWatchlist">YOUR WATCHLIST</a></li>
			    <li><a href="reservations">YOUR RESERVATIONS</a></li>
			    <li><a href="edit">EDIT PROFILE</a></li>  
			    <li><a href="logout">LOGOUT</a></li>
		    </ul>
		    <!-- ################################################################################################ -->
		  </nav>
		</div>
		
		
	</body>
</html>