<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/logistics/pre.jsp"%>
<title>Change Title Here</title>
</head>

<body class="container-fluid">
	<div class="col-md-4 col-md-offset-4">Hi, ${user.firstname } ${user.lastname }</div>
	<div class="col-md-4 col-md-offset-4"><img alt="" src="profileImg/${user.username }_profile"></div>
	<jsp:include page="/logistics/js.jsp"></jsp:include>
</body>
</html>