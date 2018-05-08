<%@page import="com.kinoarena.model.pojo.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	
	<head>
	<title>Home</title>
	
	</head>
	
	<body id="top">
	<!-- ################################################################################################ -->
	
		<jsp:include page="header.jsp"/>
		<!-- ################################################################################################ -->
		<div class="wrapper bgded overlay light" >
		  <div id="pageintro" class="hoc clear"> 
		    <!-- ################################################################################################ -->
		    <article>
		      <h3 class="heading">Home page</h3>
		      	<p></p><br><br>
		    	<p>Dont miss our new promo! -20% off for each broadcast on Thursday!</p><br><br>
		    	<c:if test="${ sessionScope.user.getAge() <= 18 }">
			      	<h3 style="color: red; text-align: center">You are ${sessionScope.user.getAge()} years old , so you got 20% extra discount!  </h3>
			    </c:if>
						
		    </article>
		    <!-- ################################################################################################ -->
		  </div>
		</div>

	</body>	
</html>