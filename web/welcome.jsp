<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/logistics/pre.jsp" %>
    <!--  <link href='https://fonts.googleapis.com/css?family=Libre+Baskerville:400,700,400italic' rel='stylesheet'
          type_='text/css'>-->
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="header">
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <h1>Supper Book Trader</h1>
            </div>
            <c:if test='${not empty requestScope.user}'>
                ${requestScope.user.firstname }
            </c:if>
            <div class="col-md-8">
                <ul class="nav nav-tabs pull-right">
                    <li><a href="c?reqtype=goto_login">Log in</a></li>
                    <li><a href="user/register.jsp">Sign up</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>

<div class="jumbotron">
    <div class="container">
        <h2>
            We <span>collect and curate</span> <br/> articles, opinions, and images <br/> from around the world.
        </h2>
    </div>
</div>

<div class="footer">
    <div class="container">
        <div class="row">
            <div class="align-left col-md-4">
                <h3>UNSW Australia</h3>
                <p>High St</p>
                <p>Kensington, NSW 2052</p>
                <p>Australia</p>
                <a href="#">
                    <img src="https://s3.amazonaws.com/codecademy-content/projects/headlines/email.svg"></a> <a
                    href="#">
                <img src="https://s3.amazonaws.com/codecademy-content/projects/headlines/fb.svg"></a> <a href="#">
                <img src="https://s3.amazonaws.com/codecademy-content/projects/headlines/twitter.svg"></a></div>
        </div>
    </div>
</div>


<jsp:include page="/logistics/js.jsp"></jsp:include>
</body>
</html>
