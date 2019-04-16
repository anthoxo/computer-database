<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>List of computers</title>
<c:set var="context" value="${pageContext.request.contextPath}" />
<link href="${context}/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="${context}/css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="${context}/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<c:if test="${notification == true}">
		<div class="alert alert-${lvlNotification} alert-dismissible show"
			role="alert">
			<strong><c:out value="${msgNotification}"></c:out></strong>
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
	</c:if>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="${context}/index"> Application -
				Computer Database </a>
		</div>
	</header>
	<div class="container">
		<h1 id="homeTitle">${page.length} computer<c:if test="${page.length > 1}">s</c:if> found
		</h1>
		<div id="actions" class="form-horizontal">
			<div class="pull-left">
				<form id="searchForm" action="${context}/computer/search"
					method="POST" class="form-inline">
					<input type="search" id="searchbox" name="search"
						class="form-control" placeholder="Search name" /> <input
						type="submit" id="searchSubmitBtn" value="Filter by name"
						class="btn btn-primary" />
				</form>
			</div>
			<div class="pull-right">
				<c:choose>
					<c:when test="${isSearching == false}">
						<a class="btn btn-success" id="addComputerBtn"
							href="${context}/computer/add">Add Computer</a>
					</c:when>
					<c:otherwise>
						<a class="btn btn-success" id="goBack" href="${context}/computer">Go
							Back</a>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>

	<div class="container">
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<th><a href="${urlPath}?orderBy=name"><span
							class="glyphicon glyphicon-sort"></span></a>&nbsp;Computer name</th>
					<th><a href="${urlPath}?orderBy=introduced"><span
							class="glyphicon glyphicon-sort"></span></a>&nbsp;Introduced date</th>
					<th><a href="${urlPath}?orderBy=discontinued"><span
							class="glyphicon glyphicon-sort"></span></a>&nbsp;Discontinued date</th>
					<th><a href="${urlPath}?orderBy=companyName"><span
							class="glyphicon glyphicon-sort"></span></a>&nbsp;Company name</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="c" items="${computerList}">
					<tr id="Computer_${c.id}">
						<td><a href="${context}/computer/edit?id=${c.id}"><c:out
									value="${c.name}" /></a></td>
						<td><c:out value="${c.introducedDate}" /></td>
						<td><c:out value="${c.discontinuedDate}" /></td>
						<td><c:out value="${c.companyName}" /></td>
						<td><a type="button" class="btn btn-danger btn-sm"
							data-toggle="modal" data-target="#modalDelete_${c.id}"> <i
								class="fa fa-trash-o fa-lg"></i>
						</a></td>
					</tr>
					<!--  Modal -->
					<div class="modal fade" id="modalDelete_${c.id}" tabindex="-1"
						role="dialog" aria-labelledby="exampleModalCenterTitle"
						aria-hidden="true">
						<div class="modal-dialog modal-dialog-centered" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title">
										Do you really want to delete this computer ?
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</h5>
								</div>
								<div class="modal-body">
									<c:out value="${c.name}" />
									<c:if test="${c.introducedDate != ''}">
										<br>Introduced in <c:out value="${c.introducedDate}" />
									</c:if>
									<c:if test="${c.discontinuedDate != ''}">
										<br>Discontinued in <c:out value="${c.discontinuedDate}" />
									</c:if>
									<c:if test="${c.companyName != null && c.companyName != ''}">
										<br>Company : <c:out value="${c.companyName}" />
									</c:if>
								</div>
								<div class="modal-footer form-inline">
									<form action="${context}/computer/delete" method="POST">
										<input type="hidden" value="${c.id}" id="id_delete"
											name="id_delete" /> <input type="submit"
											class="btn btn-danger" value="Delete" />
										<button type="button" class="btn btn-default"
											data-dismiss="modal">Close</button>
									</form>
								</div>
							</div>
						</div>
					</div>
					<!--  Fin modal -->
				</c:forEach>
			</tbody>
		</table>
	</div>

	<!-- Pagination (4 cases) -->
	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<c:if test="${page.indexPage > 0}">
					<li><a href="${urlPath}?id=${page.indexPage}" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
					</a></li>
				</c:if>
				<c:choose>
					<c:when test="${page.nbPages <= 13}">
						<c:forEach var="i" begin="1" end="${page.nbPages}">
							<li><a href="${urlPath}?id=${i}"> <c:out value="${i}" /></a></li>
						</c:forEach>
					</c:when>
					<c:when test="${page.indexPage < 6}">
						<c:forEach var="i" begin="1" end="8">
							<li><a href="${urlPath}?id=${i}"> <c:out value="${i}" /></a></li>
						</c:forEach>
						<li><a href="#">...</a></li>
						<c:forEach var="i" begin="${page.nbPages - 3}" end="${page.nbPages}">
							<li><a href="${urlPath}?id=${i}"> <c:out value="${i}" /></a></li>
						</c:forEach>
					</c:when>
					<c:when test="${page.indexPage > page.nbPages - 7}">
						<c:forEach var="i" begin="1" end="3">
							<li><a href="${urlPath}?id=${i}"> <c:out value="${i}" /></a></li>
						</c:forEach>
						<li><a href="#">...</a></li>
						<c:forEach var="i" begin="${page.nbPages - 8}" end="${page.nbPages}">
							<li><a href="${urlPath}?id=${i}"> <c:out value="${i}" /></a></li>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach var="i" begin="1" end="3">
							<li><a href="${urlPath}?id=${i}"> <c:out value="${i}" /></a></li>
						</c:forEach>
						<li><a href="#">...</a></li>
						<c:forEach var="i" begin="${page.indexPage - 1}" end="${page.indexPage + 3}">
							<li><a href="${urlPath}?id=${i}"> <c:out value="${i}" /></a></li>
						</c:forEach>
						<li><a href="#">...</a></li>
						<c:forEach var="i" begin="${page.nbPages - 2}" end="${page.nbPages}">
							<li><a href="${urlPath}?id=${i}"> <c:out value="${i}" /></a></li>
						</c:forEach>
					</c:otherwise>
				</c:choose>
				<c:if test="${page.indexPage < page.nbPages - 1}">
					<li><a href="${urlPath}?id=${page.indexPage + 2}" aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
					</a></li>
				</c:if>
			</ul>
		</div>
	</footer>
	<script src="${context}/js/jquery.min.js"></script>
	<script src="${context}/js/bootstrap.min.js"></script>
	<script src="${context}/js/dashboard.js"></script>
</body>
</html>