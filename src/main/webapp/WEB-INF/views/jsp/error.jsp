<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style>
.center {
    display: block;
    margin-left: auto;
    margin-right: auto;
    width: 68%;
}
</style>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>error</title>
	</head>
	<body>
		<h1 align="center">Error occured</h1>
		<span><img src='img/error.png' align="middle" class="center" /><h1  style='display:inline;'></h1></span>
	</body>
</html>