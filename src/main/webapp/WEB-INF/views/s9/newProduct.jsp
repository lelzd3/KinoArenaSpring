<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<a href="?language=bg">Български</a>
	<a href="?language=en">English</a>
	<a href="?language=es">Espanol</a>

	<f:form commandName="nov_product" >
		<spring:message code="product_name" /><f:input path="name"/><br>
		<spring:message code="product_price" /><f:input path="price"/><br>
		<spring:message code="product_quantity" /><f:input path="quantity"/><br>
		<input type="submit" value="add product">
	</f:form>
</body>
</html>