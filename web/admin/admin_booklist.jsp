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

    <title>Book Manage</title>
    <%@include file="/WEB-INF/jsp/jsp_css_inlcude.jsp" %>
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
                    <h1 class="page-header">Book Management</h1>
                </div>
                <%--Display books--%>
                <c:choose>
                    <c:when test="${not empty book_pager}">
                        <form action="${pageContext.request.contextPath}/c" method="post">
                            <input type="hidden" name="reqtype" value="REMOVE_BOOK">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Title</th>
                                    <th>Author</th>
                                    <th>Type</th>
                                    <th>Seller</th>
                                    <th>Select</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:set var="count" value="${(book_pager.currentPage-1)*10+1}" scope="page"/>
                                <c:forEach items="${book_pager.dataList}" var="book" varStatus="loop">
                                    <tr>
                                        <th scope="row"><c:out value="${count}"/></th>
                                        <%--TODO: title be an link--%>
                                        <td><c:out value="${book.title}"/></td>
                                        <td><c:out value="${book.authors}"/></td>
                                        <td><c:out value="${book.type}"/></td>
                                        <td><c:out value="${book.sellerID}"/></td>
                                        <th><input type="checkbox" name="is_remove" value="${book.bookID}"/></th>
                                    </tr>
                                    <c:set var="count" value="${count + 1}" scope="page"/>
                                </c:forEach>
                                </tbody>
                            </table>
                            <ul class="pager">
                                <c:set var="prev" value="${pageContext.request.contextPath}c?reqtype=BOOK_LIST_ALL&book_page=${book_pager.currentPage-1}" scope="page"/>
                                <c:set var="next" value="${pageContext.request.contextPath}c?reqtype=BOOK_LIST_ALL&book_page=${book_pager.currentPage+1}" scope="page"/>
                                <c:choose>
                                    <c:when test="${book_pager.currentPage > 1}">
                                        <li><a href="${prev}">Previous</a></li>
                                    </c:when>
                                    <c:when test="${book_pager.currentPage < book_pager.totalPage}">
                                        <li><a href="${next}">Next</a></li>
                                    </c:when>
                                </c:choose>
                            </ul>
                            <!-- Remove Button-->
                            <div class="form-group row">
                                <div class="col-xs-4">
                                </div>
                                <div class="col-xs-3">
                                    <button type="submit" class="form-control btn">Remove from cart</button>
                                </div>
                            </div>
                        </form>
                    </c:when>
                </c:choose>


            </div>

            <!-- page selector-->
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
