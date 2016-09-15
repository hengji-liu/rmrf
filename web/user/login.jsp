<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/logistics/pre.jsp"%>
    <title>Login</title>
</head>

<body class="container-fluid">
	<form class="col-md-4 col-md-offset-4">
	  <div class="form-group">
	    <label for="username">Username</label>
	    <input type="email" class="form-control" id="username" placeholder="Email">
	  </div>
	  <div class="form-group">
	    <label for="password">Password</label>
	    <input type="password" class="form-control" id="password" placeholder="Password">
	  </div>
	  <button type="submit" class="btn btn-default">Submit</button>
	</form>
</body>
</html>
