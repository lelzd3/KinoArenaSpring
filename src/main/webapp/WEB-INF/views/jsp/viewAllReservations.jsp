<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>
<%@page import="com.kinoarena.model.dao.ReservationDao"%>
<%@page import="com.kinoarena.model.dao.UserDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.kinoarena.model.pojo.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>My reservations</title>
		<%
			User user = (User) request.getSession().getAttribute("user");
			ReservationDao reservationDao = (ReservationDao)session.getAttribute("reservationDao");
			ArrayList<String> reservations = (ArrayList<String>) reservationDao.getAllReservationsForUser(user);
		%>
		<style>
		table {
		    counter-reset: tableCount;     
		}
		.counterCell:before {              
		    content: counter(tableCount); 
		    counter-increment: tableCount; 
		}
		</style>
	</head>
	
	<body>
	<jsp:include page="header.jsp"/>
	
	<div class="wrapper bgded overlay light" >
		<div id="pageintro" class="hoc clear"> <!-- toq div gi prai da sa centralno -->

			
			<h1 align="center"><strong>My reservations</strong></h1>
				
			<%
			if(reservations.size()==0){
			%>
			<h1 align="center"><strong>You dont have any reservations yet!</strong></h1>			
			<%
			}
			else{
			%>
			<table>
			  <tr style="border-bottom: 1px solid #000" >
			    <th>Reservation No</th>
			    <th>Reservation info</th>
			    <th></th>
			  </tr>
				<%
				for (String string : reservations) {
					String[] arr = string.split(",");
					String reservationId = arr[0];
					reservationId = reservationId.replaceAll("\\D+","");
					//geting id of each reservation
				%>
				<tr style="border-bottom: 1px solid #000">
					<td><%=reservationId%></td>
					<td><%=string%></td>
					<td>
					<form action="reservations" method="post">
				        <input type = "hidden" name = "selectedReservation" value = "<%=reservationId%>"/>
				    	<input type=submit value="cancel reservation" style="width:100%">	    	
				    </form>
				    </td>
				</tr>
			
				<%
				} 
				%>
			</table> 
			<%
			}
			%>
		</div>
	</div>	
	</body>
</html>