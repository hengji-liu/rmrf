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
            <div class="col-md-3">
                <h1>Supper Book Trader</h1>
            </div>
            <c:if test='${not empty requestScope.user}'>
                ${requestScope.user.firstname }
            </c:if>
            <div class="col-md-9">
                <ul class="nav nav-tabs pull-right">
                    <li><a href="user/login.jsp">Log in</a></li>
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

<%--<div class="banner">--%>
    <%--<div class="container">--%>
        <%--<h2>The Bottom Line.</h2>--%>
        <%--<p> We deliver the news that is relevant to you.</p>--%>
    <%--</div>--%>
<%--</div>--%>

<div class="cards">
    <div class="container">
        <h2>Our Deal.</h2>
        <div class="row">
            <!--col 1-->
            <div class="col-md-4">
                <div class="panel panel-info">
                    <div class="panel-heading">BOOK TITLE</div>
                    <div class="panel-body"><img src="/profileImg/default.jpg" class="img-responsive"
                                                 style="width:100%" alt="Image"></div>
                    <div class="panel-footer">100$</div>
                </div>
                <div class="panel panel-primary">
                    <div class="panel-heading">BLACK FRIDAY DEAL</div>
                    <div class="panel-body"><img src="http://placehold.it/150x80?text=IMAGE" class="img-responsive"
                                                 style="width:100%" alt="Image"></div>
                    <div class="panel-footer">Buy 50 mobiles and get a gift card</div>
                </div>
                <div class="panel panel-primary">
                    <div class="panel-heading">BLACK FRIDAY DEAL</div>
                    <div class="panel-body"><img src="http://placehold.it/150x80?text=IMAGE" class="img-responsive"
                                                 style="width:100%" alt="Image"></div>
                    <div class="panel-footer">Buy 50 mobiles and get a gift card</div>
                </div>
            </div>

            <!--col 2-->
            <div class="col-md-4">
                <div class="panel panel-primary">
                    <div class="panel-heading">Book Title</div>
                    <div class="panel-body"><img src="http://placehold.it/150x80?text=IMAGE" class="img-responsive"
                                                 style="width:100%" alt="Image"></div>
                    <div class="panel-footer">Buy 50 mobiles and get a gift card</div>
                </div>
                <div class="panel panel-primary">
                    <div class="panel-heading">BLACK FRIDAY DEAL</div>
                    <div class="panel-body"><img src="http://placehold.it/150x80?text=IMAGE" class="img-responsive"
                                                 style="width:100%" alt="Image"></div>
                    <div class="panel-footer">Buy 50 mobiles and get a gift card</div>
                </div>
                <div class="panel panel-primary">
                    <div class="panel-heading">BLACK FRIDAY DEAL</div>
                    <div class="panel-body"><img src="http://placehold.it/150x80?text=IMAGE" class="img-responsive"
                                                 style="width:100%" alt="Image"></div>
                    <div class="panel-footer">Buy 50 mobiles and get a gift card</div>
                </div>
            </div>
            <!--col 3-->
            <div class="col-md-4">
                <div class="panel panel-primary">
                    <div class="panel-heading">BLACK FRIDAY DEAL</div>
                    <div class="panel-body"><img src="http://placehold.it/150x80?text=IMAGE" class="img-responsive"
                                                 style="width:100%" alt="Image"></div>
                    <div class="panel-footer">Buy 50 mobiles and get a gift card</div>
                </div>
                <div class="panel panel-primary">
                    <div class="panel-heading">BLACK FRIDAY DEAL</div>
                    <div class="panel-body"><img src="http://placehold.it/150x80?text=IMAGE" class="img-responsive"
                                                 style="width:100%" alt="Image"></div>
                    <div class="panel-footer">Buy 50 mobiles and get a gift card</div>
                </div>
                <div class="panel panel-primary">
                    <div class="panel-heading">BLACK FRIDAY DEAL</div>
                    <div class="panel-body"><img src="http://placehold.it/150x80?text=IMAGE" class="img-responsive"
                                                 style="width:100%" alt="Image"></div>
                    <div class="panel-footer">Buy 50 mobiles and get a gift card</div>
                </div>
            </div>
        </div>
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
