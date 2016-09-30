<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/logistics/pre.jsp"%>
<title>Paused Books</title>
</head>

<body class="container-fluid">
	<h1>My Paused Books</h1>
	<c:choose>
		<c:when test="${not empty list}">
			<table class="table table-striped table-hover table-bordered">
				<tr>
					<th width="5%">#</th>
					<th width="70%">Title</th>
					<th width="15%">Price</th>
					<th width="10%">Status</th>
				</tr>
				<c:forEach var="book" items="${list}" varStatus="status">
					<tr>
						<td>${status.count}</td>
						<td><a target="_blank" href="c?reqtype=BOOK_DETAIL&book_id=${book.bookID}">${book.title}</a></td>
						<td>${book.price }</td>
						<td><a href="c?reqtype=changeto_selling&bookid=${book.bookID }">Change to selling</a></td>
					</tr>
				</c:forEach>	
			</table>
		</c:when>

		<c:otherwise>
			<div class="jumbotron" style="margin-top: 40px;">
				<h1>You have no paused books!</h1>
			</div>
		</c:otherwise>
	</c:choose>




	<jsp:include page="/logistics/js.jsp"></jsp:include>
</body>
</html>