<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/logistics/pre.jsp"%>
<!DOCTYPE html>
<html >
<head>
    <meta charset="UTF-8">
    <title>Admin Login</title>
    <script src="http://s.codepen.io/assets/libs/modernizr.js" type="text/javascript"></script>
    <link rel="stylesheet" href="css/admin_css/reset.css">
    <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css'>
    <link rel="stylesheet" href="css/admin_css/style_login.css">
</head>

<body>

<form class="login" action="c" method="post">

    <fieldset>
        <legend class="legend">Login</legend>

        <div class="input">
            <input name="admin_name" type="text" placeholder="Admin Name" required />
            <span><i class="fa fa-user"></i></span>
        </div>

        <div class="input">
            <input name="password" type="password" placeholder="Password" required />
            <span><i class="fa fa-lock"></i></span>
        </div>
        <input type="hidden" name="reqtype" value="ADMIN_LOGIN">

        <c:choose>
            <c:when test="${empty wrong}">
            </c:when>
            <c:otherwise>
                <label class="feedback">Admin name or password incorrect!</label>
            </c:otherwise>
        </c:choose>

        <button type="submit" class="submit"><i class="fa fa-long-arrow-right"></i></button>
    </fieldset>

</form>

</body>
</html>
