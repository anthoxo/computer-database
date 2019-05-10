<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
<link href="${context}/css/flags.min.css" rel="stylesheet"
	media="screen">

</head>
<body>

	<%@include file="navbar.jsp"%>

	<!-- Error alert (front validator) -->
	<div class="container">
		<div id="errEmail" class="alert alert-danger" role="alert">
			<strong><spring:message code="login.form.error.name" /></strong>
		</div>
		<div id="errPassword" class="alert alert-danger" role="alert">
			<strong><spring:message code="login.form.error.password" /></strong>
		</div>
		<div id="errUsername" class="alert alert-danger" role="alert">
			<strong><spring:message code="login.form.error.username" /></strong>
		</div>

	</div>
	<!-- End error alert (front validator) -->
	<div class="container">
		<h1>
			<spring:message code="signup" />
		</h1>
		<form:form action="/signup" method="POST" modelAttribute="userDTO">
			<fieldset>
			<legend></legend>
				<div class="form-group">
					<form:label path="email">
						<spring:message code="login.email" var="login_email" />
						<spring:message code="login.email" />
					</form:label>
					<form:input name="email" id="email" class="form-control"
						path="email" placeholder="${login_email}" />
				</div>
				<div class="form-group">
					<form:label path="password">
						<spring:message code="login.password" var="login_password" />
						<spring:message code="login.password" />
					</form:label>
					<form:input name="password" id="password" class="form-control"
						path="password" type="password" placeholder="${login_password}" />
				</div>
				<div class="form-group">
					<form:label path="username">
						<spring:message code="login.username" var="login_username" />
						<spring:message code="login.username" />
					</form:label>
					<form:input name="username" id="username" class="form-control"
						path="username" placeholder="${login_username}" />
				</div>
			</fieldset>
			<div class="actions pull-right">
				<input type="submit" value="<spring:message code="signup" />"
					class="btn btn-primary" id="btnSignUp" /> <a href="/signin"
					class="btn btn-default"><spring:message code="go_back" /></a>
			</div>
		</form:form>
	</div>

	<script src="${context}/js/jquery.min.js"></script>
	<script src="${context}/js/bootstrap.min.js"></script>
	<script src="${context}/js/dashboard.js"></script>
	<script src="${context}/js/jquery-validate.js"></script>

</body>
</html>