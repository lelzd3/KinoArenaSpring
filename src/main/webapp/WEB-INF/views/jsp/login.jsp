<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
			<h1>Login Form</h1>
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
