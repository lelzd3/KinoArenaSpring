<%@page import="com.kinoarena.model.dao.ReservationDao"%>
<%@page import="com.kinoarena.model.dao.UserDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.kinoarena.model.pojo.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My reservations</title>
<%
	User user = (User) request.getSession().getAttribute("user");
	ArrayList<String> reservations = (ArrayList<String>) ReservationDao.getInstance().getAllReservationsForUser(user);
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


	<h1 align="center">My reservations</h2>

<table>
  <tr>
    <td>Reservation No</td>
    <td>Reservation info</td>
  </tr>
	<%
		for (String string : reservations) {
			int count = 1;
			String[] arr = string.split(",");
			String reservationId = arr[0];
			reservationId = reservationId.replaceAll("\\D+","");
			//geting id of each reservation
			System.out.println(reservationId);
	        
			
	%>
  <tr>
    <td class="counterCell"></td>
    <td>
	    <form>
	       <%=string%>
	    </form>
    </td>
    <td align='center'>
	    <form action="reservations" method="post">
	        <input type = "hidden" name = "selectedReservation" value = "<%=reservationId%>"/>
	    	<input type=submit value="cancel reservation" style="width:100%">	    	
	    </form>
	</td>
  </tr>
	
	<%
		}
	%>

</body>
</html>