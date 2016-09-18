<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../WEB-INF/jsp/loginguard.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Search Users</title>
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
                    <h1 class="page-header">User Search</h1>
                </div>
            </div>
            <!-- Search Bar-->
            <jsp:include page="/WEB-INF/jsp/search_bar.jsp">
                <jsp:param name="reqtype" value="USER_SEARCH" />
            </jsp:include>
            <!-- Search Result -->
            <jsp:include page="/WEB-INF/jsp/user_display.jsp"/>
            <!-- Pager Result -->
            <div class="footer"> <!-- page selector-->
                <div class="container">
                    <jsp:include page="/WEB-INF/jsp/page_selector.jsp">
                        <jsp:param name="req_prefix" value="c?reqtype=USER_SEARCH&username=${username}&user_page=" />
                    </jsp:include>
                </div>
            </div>
        </div>
    </div>

</div>

<!-- jQuery -->
<script src="../js/admin_js/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="../js/admin_js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="../js/admin_js/metisMenu.min.js"></script>

<!-- Custom Theme JavaScript -->
<script src="../js/admin_js/startmin.js"></script>

</body>
</html>


