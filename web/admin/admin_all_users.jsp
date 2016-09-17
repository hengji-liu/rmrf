<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../WEB-INF/jsp/loginguard.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- display all users in page, no search-->
<html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Startmin - Bootstrap Admin Theme</title>

    <!-- Bootstrap Core CSS -->
    <link href="../css/admin_css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../css/admin_css/metisMenu.min.css" rel="stylesheet">

    <!-- Timeline CSS -->
    <link href="../css/admin_css/timeline.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../css/admin_css/startmin.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="../css/admin_css/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../css/admin_css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <link href="../css/admin_css/userstyle.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>

<div id="wrapper">

    <!-- Navigation -->
    <jsp:include page="../WEB-INF/jsp/nav_admin.jsp"/>

    <!-- Page Content -->
    <div id="page-wrapper">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Managing Users</h1>
                </div>
            </div>
            <!-- ... All Users List with page split ... -->
            <%--<%@ include file="../WEB-INF/jsp/admin_searchbar.jsp" %>--%>
            <jsp:include page="../WEB-INF/jsp/user_display.jsp"></jsp:include>
            <%-- Include User display page--%>


            <div class="footer">
                <div class="container">
                    <ul>
                        <c:choose>
                            <c:when test="${pager.totalRecord > 0 && pager.currentPage!=1}">
                                <li><a href="c?reqtype=USER_LIST&user_page=1">Front</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a>Front</a></li>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${pager.currentPage != 1}">
                                <li><a href="c?reqtype=USER_LIST&user_page=<c:out value="${pager.currentPage-1}"/>">Prev</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a>Prev</a></li>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${pager.currentPage < pager.totalPage}">
                                <li><a href="c?reqtype=USER_LIST&user_page=<c:out value="${pager.currentPage+1}"/>">Next</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a>Next</a></li>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${pager.currentPage < pager.totalPage}">
                                <li><a href="c?reqtype=USER_LIST&user_page=<c:out value="${pager.totalPage}"/>">End</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a>End</a></li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
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
