<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../WEB-INF/jsp/loginguard.jsp" %>
<%@include file="/WEB-INF/jsp/pre.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<jsp:include page="WEB-INF/jsp/loginguard.jsp"/>--%>
<html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Startmin - Bootstrap Admin Theme</title>
    <%@include file="/WEB-INF/jsp/jsp_css_inlcude.jsp"%>
</head>
<body>

<div id="wrapper">

    <!-- Navigation -->
    <jsp:include page="/WEB-INF/jsp/nav_admin.jsp"/>

    <!-- Page Content -->
    <div id="page-wrapper">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">DashBoard</h1>
                </div>
            </div>
            <%@include file="../WEB-INF/jsp/dashboard.jsp"%>

        </div>
    </div>

</div>
</body>
</html>
