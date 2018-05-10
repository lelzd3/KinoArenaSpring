<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Register</title>
	      <link rel="stylesheet" href="css/loginStyle.css">

	
	</head>
<body>

  <body>
<div class="container">
	<section id="content">
		<form action="register" method="post">
			<h1>Register Form</h1>
			
	  	<c:if test="${ message != null }">
	  		<h3 style="color: red; text-align: center">${ message }</h3>
		</c:if>	
			<div>
				<input type="text" name="username" value="" placeholder="Username" required id="username"  maxlength="45"/>
			</div>
			<div>
				<input type="password" name="password1" value="#12345678sS" placeholder="Password" required id="password" maxlength="255"/>
			</div>
			<div>
				<input type="password" name="password2" value="#12345678sS" placeholder="Confirm Password" required id="password" maxlength="255" />
			</div>
			<div>
				<input type="email" name="email" value="" placeholder="Email" required id="username" maxlength="45"/>
			</div>
			<div>
				<input type="text" name="firstName" value="" placeholder="First Name" required id="username" maxlength="45" />
			</div>
			<div>
				<input type="text" name="lastName" value="" placeholder="Last Name" required id="username" maxlength="45" />
			</div>
			<div>
				<input type="number" name="age" value="" placeholder="Age" required id="username" min="1" max="120"/>
			</div>
			<div>
				<input type="submit" value="Register" />
				<a href="loginPage">Already have an account? Click here to login.</a>
			</div>
		</form><!-- form -->
	</section><!-- content -->
</div><!-- container -->
</body>
  
  

    <script  src="js/index.js"></script>




</body>

</html>
