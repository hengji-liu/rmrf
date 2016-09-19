<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/logistics/pre.jsp"%>
<title>Register</title>
</head>

<body class="container-fluid">
	<form class="col-md-4 col-md-offset-4" action="c" method="post"
		enctype="multipart/form-data">
		<input type="hidden" name="reqtype" value="user_register">
		<div class="form-group">
			<label for="username">Username</label> <input type="email"
				class="form-control" id="username" name="username"
				placeholder="Username" required="required">
		</div>
		<div class="form-group">
			<label for="password">Password</label> <input type="password"
				class="form-control" id="password" name="password"
				placeholder="Password" required="required">
		</div>
		<div class="form-group">
			<label for="profileImg">Profile Image</label> <input type="file"
				id="profileImg" name="profileImg">
		</div>
		<div class="form-group">
			<label for="firstname">Firstname</label> <input type="text"
				class="form-control" id="firstname" name="firstname"
				placeholder="Firstname" required="required">
		</div>
		<div class="form-group">
			<label for="lastname">Lastname</label> <input type="text"
				class="form-control" id="lastname" name="lastname"
				placeholder="Lastname" required="required">
		</div>
		<div class="form-group">
			<label for="email">Email</label> <input type="email"
				class="form-control" id="email" name="email" placeholder="Email"
				required="required">
		</div>
		<div class="form-group">
			<label for="birthday">Birthday</label> <input type="date"
				class="form-control" id="birthday" name="birthday"
				placeholder="Birthday" required="required">
		</div>
		<div class="form-group">
			<label for="address">Address</label> <input type="text"
				class="form-control" id="address" name="address"
				placeholder="Address" required="required">
		</div>
		<div class="form-group">
			<label for="creditcard">Credit Card</label> <input type="text"
				class="form-control" id="creditcard" name="creditcard"
				placeholder="4622xxxxxxxx1083" required="required">
		</div>


		<button type="submit" class="btn btn-default">Register</button>
	</form>
</body>
</html>
