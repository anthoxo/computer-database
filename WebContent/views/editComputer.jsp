<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit computer</title>
<link href="/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="/index"> Application - Computer
				Database </a>
		</div>
	</header>

	<div class="container">
		<div id="id" class="form-horizontal">
			<div class="label label-default pull-right">id: <c:out value="${computer.id}"/></div>
		</div>
	</div>

	<div class="container">
		<h1>Edit computer</h1>
		<form action="/computer/edit" method="POST">
			<input type="hidden" value="${computer.id}" id="id" name="id"/>
			<fieldset>
				<div class="form-group">
					<label for="computerName">Computer name</label> <input type="text"
						value="${computer.name}" class="form-control" id="computerName"
						name="computerName" placeholder="Computer name">
				</div>
				<div class="form-group">
					<label for="introduced">Introduced date (yyyy/mm/dd)</label> <input type="text"
						value="${computer.introducedDate}" class="form-control"
						name="introduced" id="introduced" placeholder="Introduced date">
				</div>
				<div class="form-group">
					<label for="discontinued">Discontinued date (yyyy/mm/dd)</label> <input
						type="text" value="${computer.discontinuedDate}"
						class="form-control" id="discontinued" name="discontinued"
						placeholder="Discontinued date">
				</div>
				<div class="form-group">
					<label for="companyId">Company</label> <select class="form-control"
						id="companyId" name="companyId">
						<option value="0">--</option>
						<c:forEach var="c" items="${listCompanies}">
							<option value="${c.id}"
								<c:if test="${c.name == computer.companyName}">selected</c:if>>${c.name}</option>
						</c:forEach>
					</select>
				</div>
			</fieldset>
			<div class="actions pull-right">
				<input type="submit" value="Edit" class="btn btn-primary" /> <a
					href="/computer" class="btn btn-default">Cancel</a>
			</div>
		</form>
	</div>
	<script src="/js/jquery.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<script src="/js/dashboard.js"></script>

</body>
</html>