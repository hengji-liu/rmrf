<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/logistics/pre.jsp"%>
<title>Change Title Here</title>
</head>

<body class="container-fluid">
	<c:choose>
		<c:when test="${not empty list}">
			<table class="table table-striped table-hover table-bordered">
				<tr>
					<th width="4%">#</th>
					<th width="73%">Title</th>
					<th width="23%">Price</th>
				</tr>
				<c:forEach var="book" items="${list}" varStatus="status">
					<tr>
						<td>${status.count}</td>
						<td><a href="">${book.title}</a></td>
						<td>${book.price }</td>
					</tr>
				</c:forEach>
			</table>
		</c:when>

		<c:otherwise>
			<div class="jumbotron" style="margin-top: 40px;">
				<h1>You haven't uploaded any books!</h1>
			</div>
		</c:otherwise>
	</c:choose>




	<jsp:include page="/logistics/js.jsp"></jsp:include>
</body>
</html>