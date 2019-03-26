<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>List of companies</title>
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
			<a class="navbar-brand" href="${context}/index"> Application -
				Computer Database </a>
		</div>
	</header>

	<div class="container">
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<th>Company name</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="c" items="${companyList}">
					<tr>
						<td><c:out value="${c.name}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<c:if test="${idPage > 1}">
					<li><a href="${context}/company?id=${idPage-1}"
						aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
					</a></li>
				</c:if>
				<c:forEach var="i" begin="1" end="${nbPages}">
					<li><a href="${context}/company?id=${i}"> <c:out
								value="${i}" /></a></li>
				</c:forEach>
				<c:if test="${idPage < nbPages}">
					<li><a href="${context}/company?id=${idPage+1}"
						aria-label="Next"> <span aria-hidden="true">&raquo;</span>
					</a></li>
				</c:if>
			</ul>
		</div>
	</footer>

</body>
</html>