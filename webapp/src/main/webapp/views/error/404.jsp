<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="/css/main.css" rel="stylesheet" media="screen">
<link href="${context}/css/flags.min.css" rel="stylesheet" media="screen">
</head>
<body>

	<%@include file="../navbar.jsp"%>

	<section id="main">
		<div class="container">
			<div class="alert alert-danger">
				<spring:message code="error.404.message" /><br />
			</div>
		</div>
	</section>

	<script src="/js/jquery.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<script src="/js/dashboard.js"></script>

</body>
</html>