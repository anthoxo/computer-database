<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><spring:message code="computer.title" /></title>
<c:set var="context" value="${pageContext.request.contextPath}" />
<link href="${context}/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="${context}/css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="${context}/css/main.css" rel="stylesheet" media="screen">
<link href="${context}/css/flags.min.css" rel="stylesheet" media="screen">
</head>
<body>
	<c:if test="${isNotifying == true}">
		<div class="alert alert-${notification.type} alert-dismissible show"
			role="alert">
			<strong><c:out value="${notification.message}"></c:out></strong>
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
	</c:if>
	
	<%@include file="navbar.jsp" %>
	
	<div class="container">
		<h1 id="homeTitle">
			<c:choose>
				<c:when test="${page.length > 1}">
					<spring:message code="computer.numberComputers" arguments="${page.length}" />
				</c:when>
				<c:otherwise>
					<spring:message code="computer.numberComputer" arguments="${page.length}" />
				</c:otherwise>
			</c:choose>
		</h1>
		<div id="actions" class="form-horizontal">
			<div class="pull-left">
				<form id="searchForm" action="${context}/computer/search"
					method="POST" class="form-inline">
					<input type="search" id="searchbox" name="search"
						class="form-control" 
						placeholder="<spring:message code="computer.search_name" />" />
					<input
						type="submit" id="searchSubmitBtn"
						value="<spring:message code="computer.filter_name" />"
						class="btn btn-primary" />
				</form>
			</div>
			<div class="pull-right">
				<c:choose>
					<c:when test="${isSearching == false}">
						<c:if test="${isAdmin == true}">
							<a class="btn btn-success" id="addComputerBtn" href="${context}/computer/add">
								<spring:message code="computer.add" />
							</a>
						</c:if>
					</c:when>
					<c:otherwise>
						<a class="btn btn-success" id="goBack" href="${context}/computer">
							<spring:message code="go_back" />
						</a>
					</c:otherwise>
				</c:choose>
				<a class="btn btn-warning" id="addComputerBtn" href="#" onclick="$.fn.toggleEditMode();">
					<spring:message code="edit" />
				</a>
			</div>
		</div>
	</div>

	<div class="container">
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<th>
						<a href="${urlPath}?orderBy=name">
							<span class="glyphicon glyphicon-sort"></span>
						</a>
						&nbsp;<spring:message code="computer.table.name" />
					</th>
					<th>
						<a href="${urlPath}?orderBy=introduced">
							<span class="glyphicon glyphicon-sort"></span>
						</a>
						&nbsp;<spring:message code="computer.table.introduced" />
					</th>
					<th>
						<a href="${urlPath}?orderBy=discontinued">
							<span class="glyphicon glyphicon-sort"></span>
						</a>
						&nbsp;<spring:message code="computer.table.discontinued" />
					</th>
					<th>
						<a href="${urlPath}?orderBy=company">
							<span class="glyphicon glyphicon-sort"></span>
						</a>&nbsp;<spring:message code="computer.table.company_name" />
					</th>
					<c:if test="${isAdmin == true}">
						<th></th>
					</c:if>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="c" items="${page.entitiesPage}">
					<tr id="Computer_${c.id}">
						<td>
						<c:choose>
							<c:when test="${isAdmin == true}">
								<a href="${context}/computer/${c.id}"><c:out value="${c.name}" /></a>
							</c:when>
							<c:otherwise>
								<c:out value="${c.name}" />
							</c:otherwise>
						</c:choose>
						</td>
						<td><c:out value="${c.introducedDate}" /></td>
						<td><c:out value="${c.discontinuedDate}" /></td>
						<td><c:out value="${c.companyName}" /></td>
						<c:if test="${isAdmin == true}">
							<td align="center">
							<a type="button" class="btn btn-danger btn-sm"
								data-toggle="modal" data-target="#modalDelete_${c.id}"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
							</td>
						</c:if>
						
					</tr>
					
					<!--  Modal delete -->
					<div class="modal fade" id="modalDelete_${c.id}" tabindex="-1"
						role="dialog" aria-labelledby="exampleModalCenterTitle"
						aria-hidden="true">
						<div class="modal-dialog modal-dialog-centered" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title">
										<spring:message code="computer.delete.main_message" />
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</h5>
								</div>
								<div class="modal-body">
									<c:out value="${c.name}" />
									<c:if test="${c.introducedDate != ''}">
										<br>
										<spring:message code="computer.delete.message.introduced" arguments="${c.introducedDate}"/>
									</c:if>
									<c:if test="${c.discontinuedDate != ''}">
										<br>
										<spring:message code="computer.delete.message.discontinued" arguments="${c.discontinuedDate}"/>
									</c:if>
									<c:if test="${c.companyName != null && c.companyName != ''}">
										<br>
										<spring:message code="computer.delete.message.company_name" arguments="${c.companyName}"/>
									</c:if>
								</div>
								<div class="modal-footer form-inline">
									<form action="${context}/computer/delete" method="POST">
										<input type="hidden" value="${c.id}" id="id_delete"
											name="id_delete" /> <input type="submit"
											class="btn btn-danger" value="<spring:message code="delete" />" />
										<button type="button" class="btn btn-default"data-dismiss="modal">
											<spring:message code="close" />
										</button>
									</form>
								</div>
							</div>
						</div>
					</div>
					<!--  Fin modal delete -->
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