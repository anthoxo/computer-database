<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>List of computers</title>
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
		<div id="actions" class="form-horizontal">
			<div class="pull-left">
				<form id="searchForm" action="/computer/search" method="POST"
					class="form-inline">
					<input type="search" id="searchbox" name="search"
						class="form-control" placeholder="Search name" /> <input
						type="submit" id="searchsubmit" value="Filter by name"
						class="btn btn-primary" />
				</form>
			</div>
			<div class="pull-right">
				<c:choose>
					<c:when test="${isSearching == false}">
						<a class="btn btn-success" id="addComputer" href="#">Add
							Computer</a>
					</c:when>
					<c:otherwise>
						<a class="btn btn-success" id="goBack" href="/computer">Go Back</a>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>

	<div class="container">
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<th>Computer name</th>
					<th>Introduced date</th>
					<th>Discontinued date</th>
					<th>Company name</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="c" items="${listComputers}">
					<tr>
						<td><c:out value="${c.name}" /></td>
						<td><c:out value="${c.introducedDate}" /></td>
						<td><c:out value="${c.discontinuedDate}" /></td>
						<td><c:out value="${c.companyName}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<c:if test="${idPage > 1}">
					<li><a href="${urlPath}?id=${idPage-1}" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
					</a></li>
				</c:if>
				<c:forEach var="i" begin="1" end="${nbPages}">
					<li><a href="${urlPath}?id=${i}"> <c:out value="${i}" /></a></li>
				</c:forEach>
				<c:if test="${idPage < nbPages}">
					<li><a href="${urlPath}?id=${idPage+1}" aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
					</a></li>
				</c:if>
			</ul>
	</footer>

</body>
</html>