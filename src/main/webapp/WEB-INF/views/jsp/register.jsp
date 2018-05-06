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
			<h2>tuk moje da slojim eventualen invalid message</h2>
			<div>
				<input type="text" name="username" value="Stanislav1" placeholder="Username" required="" id="username" />
			</div>
			<div>
				<input type="password" name="password1" value="#12345678sS" placeholder="Password" required="" id="password" />
			</div>
			<div>
				<input type="password" name="password2" value="#12345678sS" placeholder="Confirm Password" required="" id="password" />
			</div>
			<div>
				<input type="email" name="email" value="stan123@abv.bg" placeholder="Email" required="" id="username" />
			</div>
			<div>
				<input type="text" name="firstName" value="Stanchy" placeholder="First Name" required="" id="username" />
			</div>
			<div>
				<input type="text" name="lastName" value="Kaliparski" placeholder="Last Name" required="" id="username" />
			</div>
			<div>
				<input type="number" name="age" value="22" placeholder="Age" required="" id="username" />
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
