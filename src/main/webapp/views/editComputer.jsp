<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit computer</title>
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

	<div class="container">
		<div id="id" class="form-horizontal">
			<div class="label label-default pull-right">
				id:
				<c:out value="${computerDTO.id}" />
			</div>
		</div>
	</div>

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
		<h1>Edit computer</h1>
		<label for="computerName">Computer name</label> <br>
		<form:form action="${context}/computer/edit" method="POST"
			modelAttribute="computerDTO">
			<form:hidden path="id" value="${computerDTO.id}" />
			<fieldset>
				<div class="form-group">
					<form:input name="name" id="name" class="form-control" path="name"
						value="${computerDTO.name}" placeholder="Computer name" />
				</div>
				<div class="form-group">
					<form:label path="introducedDate">Introduced date (yyyy/mm/dd)</form:label>
					<form:input name="introducedDate" id="introducedDate" class="form-control" path="introducedDate"
						value="${computerDTO.introducedDate}" placeholder="Introduced date" />
				</div>
				<div class="form-group">
					<form:label path="discontinuedDate">Discontinued date (yyyy/mm/dd)</form:label>
					<form:input name="discontinuedDate" id="discontinuedDate" class="form-control" path="discontinuedDate"
						value="${computerDTO.discontinuedDate}"
						placeholder="Discontinued date" />
				</div>
				<div class="form-group">
					<form:label path="companyId">Company</form:label>
					<form:select name="companyId" id="companyId" class="form-control" path="companyId">
						<form:option value="0" label="--" />
						<c:forEach var="c" items="${companyList}">
							<c:choose>
								<c:when test="${c.name == computerDTO.companyName}">
									<form:option value="${c.id}" label="${c.name}" selected="true" />
								</c:when>
								<c:otherwise>
									<form:option value="${c.id}" label="${c.name}" />
								</c:otherwise>
							</c:choose>

						</c:forEach>
					</form:select>
				</div>
			</fieldset>
			<div class="actions pull-right">
				<input type="submit" value="Edit" class="btn btn-primary"
					id="btnEditOrAddComputer" /> <a href="${context}/computer"
					class="btn btn-default">Cancel</a>
			</div>
		</form:form>
	</div>
	<script src="${context}/js/jquery.min.js"></script>
	<script src="${context}/js/bootstrap.min.js"></script>
	<script src="${context}/js/dashboard.js"></script>
	<script src="${context}/js/jquery-validate.js"></script>

</body>
</html>