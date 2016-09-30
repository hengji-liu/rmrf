<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/logistics/pre.jsp"%>
<title>Profile</title>
</head>

<body class="container-fluid">
            <ul class="col-md-2 col-md-offset-3 nav nav-pills nav-stacked">
                <li class="active"><a href=c?reqtype=goto_search>Home</a></li>
            </ul>
       <form class="col-md-8 col-md-offset-2 form-horizontal" action="c"
		method="post" enctype="multipart/form-data">
		<input type="hidden" name="reqtype" value="profile_change">
		<div class="form-group">
			<label class="col-sm-2 control-label" for="username">Username</label>
			<div class="col-sm-8">
				<input type="email" class="form-control" id="username"
					name="username" placeholder="Username" value="${user.username }"
					readonly="readonly">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="password">Password</label>
			<div class="col-sm-8">
				<input type="password" class="form-control" id="password"
					name="password" placeholder="No change" readonly="readonly">
			</div>
			<div class="col-sm-2">
				<button onclick="removeReadonly('password')" type="button"
					class="btn btn-default">
					<span class="glyphicon glyphicon-edit"></span>
				</button>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="profileImg">Profile
				Image</label>
			<div class="col-sm-2">
				<input type="file" id="profileImg" name="profileImg">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="firstname">Firstname</label>
			<div class="col-sm-8">
				<input type="text" class="form-control" id="firstname"
					name="firstname" value="${user.firstname }" readonly="readonly">
			</div>
			<div class="col-sm-2">
				<button onclick="removeReadonly('firstname')" type="button"
					class="btn btn-default">
					<span class="glyphicon glyphicon-edit"></span>
				</button>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="lastname">Lasttname</label>
			<div class="col-sm-8">
				<input type="text" class="form-control" id="lastname"
					name="lastname" value="${user.lastname }" readonly="readonly">
			</div>
			<div class="col-sm-2">
				<button onclick="removeReadonly('lastname')" type="button"
					class="btn btn-default">
					<span class="glyphicon glyphicon-edit"></span>
				</button>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="email">Email</label>
			<div class="col-sm-8">
				<input type="text" class="form-control" id="email" name="email"
					value="${user.email }" readonly="readonly">
			</div>
			<div class="col-sm-2">
				<button onclick="removeReadonly('email')" type="button"
					class="btn btn-default">
					<span class="glyphicon glyphicon-edit"></span>
				</button>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="birthday">Birthday</label>
			<div class="col-sm-8">
				<input type="date" class="form-control" id="birthday"
					name="birthday" value="${user.birthday }" readonly="readonly">
			</div>
			<div class="col-sm-2">
				<button onclick="removeReadonly('birthday')" type="button"
					class="btn btn-default">
					<span class="glyphicon glyphicon-edit"></span>
				</button>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="address">Address</label>
			<div class="col-sm-8">
				<input type="text" class="form-control" id="address" name="address"
					value="${user.address }" readonly="readonly">
			</div>
			<div class="col-sm-2">
				<button onclick="removeReadonly('address')" type="button"
					class="btn btn-default">
					<span class="glyphicon glyphicon-edit"></span>
				</button>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="creditcard">Credit
				Card</label>
			<div class="col-sm-8">
				<input type="text" class="form-control" id="creditcard"
					name="creditcard" value="${user.creditcard }" readonly="readonly">
			</div>
			<div class="col-sm-2">
				<button onclick="removeReadonly('creditcard')" type="button"
					class="btn btn-default">
					<span class="glyphicon glyphicon-edit"></span>
				</button>
			</div>
		</div>
		<button type="submit" class="btn btn-default col-md-offset-2">Modify</button>
	</form>

	<jsp:include page="/logistics/js.jsp"></jsp:include>
	<script type="text/javascript">
		removeReadonly = function(id) {
			$("#" + id).removeAttr("readonly");
		}
	</script>
</body>
</html>
