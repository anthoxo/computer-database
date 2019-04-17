<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><spring:message code="title" /></title>
<c:set var="context" value="${pageContext.request.contextPath}" />
<link href="${context}/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="${context}/css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="${context}/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="${context}/index"><spring:message code="title" /></a>
		</div>
	</header>

	<div class="container">
		<a class="btn btn-primary" href="${context}/company">
			<spring:message code="index.company_list" />
		</a> 
		<a class="btn btn-primary" href="${context}/computer">
			<spring:message code="index.computer_list" />
		</a> 
		<a class="btn btn-primary" href="/fr">FRANCAIS</a> <a
			class="btn btn-primary" href="/en">ENGLISH</a>
	</div>
</body>
</html>