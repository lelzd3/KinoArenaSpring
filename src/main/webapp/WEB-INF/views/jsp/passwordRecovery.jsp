<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Recover password</title>
<link rel="stylesheet" href="css/loginStyle.css">
</head>
<body>

  <body>
<div class="container">
	<section id="content">
		<form action="passwordRecovery" method="post">
		
		     <h1><strong>Recovery</strong></h1>
		
	  	<c:if test="${ error != null }">
	  		<h3 style="color: red; text-align: center">${ error }</h3>
		</c:if>	
			<div>
				<input type="email" name="email" value="" placeholder="Email" required="" id="email" />
			</div>
			<div>
				<input type="submit" value="Submit" />
				 <a href="loginPage">Back to login page.</a><br/>
	           	<a href="getRegisterPage">Don't have an account? Click here to register.</a>
			</div>
		</form><!-- form -->
	</section><!-- content -->
</div><!-- container -->
</body>
  
  

    <script  src="js/index.js"></script>




</body>

</html>