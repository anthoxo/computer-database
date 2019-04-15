<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add computer</title>
<c:set var="context" value="${pageContext.request.contextPath}" />
<link href="${context}/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${context}/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="${context}/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="${context}/index"> Application - Computer
				Database </a>
		</div>
	</header>

	<!-- Error alert (front validator) -->
	<div class="container">
		<div id="errComputerName" class="alert alert-danger" role="alert">
			<strong>Should add computer name</strong>
		</div>
		<div id="errIntroduced" class="alert alert-danger" role="alert">
			<strong>Introduced date should be in this form : yyyy/mm/dd</strong>
		</div>
		<div id="errDiscontinued" class="alert alert-danger" role="alert">
			<strong>Discontinued date should be in this form :
				yyyy/mm/dd</strong>
		</div>
	</div>
	<!-- End error alert (front validator) -->

	<div class="container">
		<h1>Add computer</h1>
		<form action="${context}/computer/add" method="POST">
			<fieldset>
				<div class="form-group">
					<label for="computerName">Computer name</label> <input type="text"
						class="form-control" id="computerName" name="computerName"
						placeholder="Computer name">
				</div>
				<div class="form-group">
					<label for="introduced">Introduced date (yyyy/mm/dd)</label> <input
						type="text" class="form-control" name="introduced" id="introduced"
						placeholder="Introduced date">
				</div>
				<div class="form-group">
					<label for="discontinued">Discontinued date (yyyy/mm/dd)</label> <input
						type="text" class="form-control" id="discontinued"
						name="discontinued" placeholder="Discontinued date">
				</div>
				<div class="form-group">
					<label for="companyId">Company</label> <select class="form-control"
						id="companyId" name="companyId">
						<option value="0">--</option>
						<c:forEach var="c" items="${companyList}">
							<option value="${c.id}"
								<c:if test="${c.name == computer.companyName}">selected</c:if>>${c.name}</option>
						</c:forEach>
					</select>
				</div>
			</fieldset>
			<div class="actions pull-right">
				<input type="submit" value="Add" class="btn btn-primary"
					id="btnEditOrAddComputer" /> <a href="${context}/computer"
					class="btn btn-default">Cancel</a>
			</div>
		</form>

	</div>
	<script src="${context}/js/jquery.min.js"></script>
	<script src="${context}/js/bootstrap.min.js"></script>
	<script src="${context}/js/dashboard.js"></script>
	<script src="${context}/js/jquery-validate.js"></script>

</body>
</html>