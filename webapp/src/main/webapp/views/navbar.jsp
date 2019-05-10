<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-left">		
			<c:if test="${isUser == true}">
				<a class="navbar-brand" href="/logout"><span class="glyphicon glyphicon-log-out"></span></a>
			</c:if>
			 <a class="navbar-brand" href="${context}/index">
			 	<spring:message code="title" />
			</a>
		</div>
		<div class="navbar-right">
			<a class="navbar-brand" href="/fr"><img
				src="${context}/img/blank.gif" class="flag flag-fr" alt="French flag"/></a> <a
				class="navbar-brand" href="/en"><img
				src="${context}/img/blank.gif" class="flag flag-gb" alt="English flag"/></a>
		</div>
	</div>
</header>
