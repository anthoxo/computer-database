<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><spring:message code="computer.add" /></title>
<c:set var="context" value="${pageContext.request.contextPath}" />
<link href="${context}/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="${context}/css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="${context}/css/main.css" rel="stylesheet" media="screen">
<link href="${context}/css/flags.min.css" rel="stylesheet" media="screen">

</head>
<body>

	<%@include file="navbar.jsp" %>

	<!-- Error alert (front validator) -->
	<div class="container">
		<div id="errComputerName" class="alert alert-danger" role="alert">
			<strong><spring:message code="computer.form.error.name" /></strong>
		</div>
		<div id="errIntroduced" class="alert alert-danger" role="alert">
			<strong><spring:message code="computer.form.error.introduced" /></strong>
		</div>
		<div id="errDiscontinued" class="alert alert-danger" role="alert">
			<strong><spring:message code="computer.form.error.discontinued" /></strong>
		</div>
	</div>
	<!-- End error alert (front validator) -->

	<div class="container">
		<h1><spring:message code="computer.add" /></h1>
		<form:form action="${context}/computer/add" method="POST"
			modelAttribute="computerDTO">
			<fieldset>
			<legend></legend>
				<div class="form-group">
					<form:label path="companyId">
						<spring:message code="computer.table.name" var="computer_table_name" />
						<spring:message code="computer.table.name" />
					</form:label>
					<form:input name="name" id="name" class="form-control" path="name"
						value="${computerDTO.name}" 
						placeholder="${computer_table_name}" />
				</div>
				<div class="form-group">
					<form:label path="introducedDate">
						<spring:message code="computer.table.introduced" var="computer_table_introduced" />
						<spring:message code="computer.table.introduced" /> (yyyy/mm/dd)
					</form:label>
					<form:input name="introducedDate" id="introducedDate"
						class="form-control" path="introducedDate"
						value="${computerDTO.introducedDate}"
						placeholder="${computer_table_introduced}" />
				</div>
				<div class="form-group">
					<form:label path="discontinuedDate">
						<spring:message code="computer.table.discontinued" var="computer_table_discontinued" />
						<spring:message code="computer.table.discontinued" /> (yyyy/mm/dd)
					</form:label>
					<form:input name="discontinuedDate" id="discontinuedDate"
						class="form-control" path="discontinuedDate"
						value="${computerDTO.discontinuedDate}"
						placeholder="${computer_table_discontinued}" />
				</div>
				<div class="form-group">
					<form:label path="companyId">
						<spring:message code="company" />
					</form:label>
					<form:select name="companyId" id="companyId" class="form-control"
						path="companyId">
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
				<input type="submit" value="<spring:message code="add" />" class="btn btn-primary"
					id="btnEditOrAddComputer" /> <a href="/computer"
					class="btn btn-default"><spring:message code="cancel" /></a>
			</div>
		</form:form>
	</div>
	<script src="${context}/js/jquery.min.js"></script>
	<script src="${context}/js/bootstrap.min.js"></script>
	<script src="${context}/js/dashboard.js"></script>
	<script src="${context}/js/jquery-validate.js"></script>

</body>
</html>