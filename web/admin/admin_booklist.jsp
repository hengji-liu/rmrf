<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../WEB-INF/jsp/loginguard.jsp" %>
<%@include file="/WEB-INF/jsp/pre.jsp" %>
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
                    <c:when test="${not empty pager}">
                        <form action="c" method="post">
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
                                <c:set var="count" value="${(pager.currentPage-1)*10+1}" scope="page"/>
                                <c:forEach items="${pager.dataList}" var="book" varStatus="loop">
                                    <tr>
                                        <th scope="row"><c:out value="${count}"/></th>
                                            <%--TODO: title be an link--%>
                                        <td><a href="c?reqtype=BOOK_DETAIL_READ&book_id=${book.bookID}"><c:out
                                                value="${book.title}"/></a></td>
                                        <td><c:out value="${book.authors}"/></td>
                                        <td><c:out value="${book.type}"/></td>
                                        <td><c:out value="${book.sellerID}"/></td>
                                        <th><input type="checkbox" name="is_remove" value="${book.bookID}"/></th>
                                    </tr>
                                    <c:set var="count" value="${count + 1}" scope="page"/>
                                </c:forEach>
                                </tbody>
                            </table>

                            <c:set var="req" value="c?reqtype=BOOK_LIST_ALL&book_page=" scope="page"/>
                            <div class="footer"> <!-- page selector-->
                                <div class="container">
                                    <jsp:include page="/WEB-INF/jsp/page_selector.jsp">
                                        <jsp:param name="req_prefix" value="${req}"/>
                                    </jsp:include>
                                </div>
                                <div class="container-fluid bg-2 text-center">
                                    <button type="submit" class="btn btn-default btn-lg">Remove Book</button>
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
</body>
</html>
