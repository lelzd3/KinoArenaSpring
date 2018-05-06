<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>login page</title>
        <link rel="stylesheet" href="css/loginStyle.css">
	</head>
	<body>

  <body>
<div class="container">
	<section id="content">
		<form action="login" method="post">
		
		     <h1><strong> Login </strong></h1>
		
	  	<c:if test="${ error != null }">
	  		<h3 style="color: red; text-align: center">${ error }</h3>
		</c:if>	
			<div>
				<input type="text" name="username" value="Stanislav1" placeholder="Username" required="" id="username" />
			</div>
			<div>
				<input type="password" name="password" value="#12345678sS" placeholder="Password" required="" id="password" />
			</div>
			<div>
				<input type="submit" value="Log in" />
				<a href="getRegisterPage">Register</a>
			</div>
		</form><!-- form -->
	</section><!-- content -->
</div><!-- container -->
</body>
  
  

    <script  src="js/index.js"></script>




</body>

</html>
