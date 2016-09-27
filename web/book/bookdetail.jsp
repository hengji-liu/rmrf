<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/pre.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Title</title>
    <link href="css/admin_css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bookdetail.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h3 class="panel-title"><c:out value="${book.title}"/></h3>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-3 col-lg-3 " align="center"><img alt="Book Img" src="img/book${book.photoid }"
                                                                            class="img-thumbnail img-responsive"></div>
                        <div class=" col-md-9 col-lg-9 ">
                            <table class="table table-user-information">
                                <tbody>
                                <c:choose>
                                    <c:when test="${not empty book.type}">
                                        <tr>
                                            <td>Type:</td>
                                            <td><c:out value="${book.type}"/></td>
                                        </tr>
                                    </c:when>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${not empty book.authors}">
                                        <tr>
                                            <td>Authors:</td>
                                            <td><c:out value="${book.authors}"/></td>
                                        </tr>
                                    </c:when>
                                </c:choose>

                                <c:choose>
                                    <c:when test="${not empty book.year}">
                                        <tr>
                                            <td>Year:</td>
                                            <td><c:out value="${book.year}"/></td>
                                        </tr>
                                    </c:when>
                                </c:choose>

                                <c:choose>
                                    <c:when test="${not empty book.venue}">
                                        <tr>
                                            <td>Venue:</td>
                                            <td><c:out value="${book.venue}"/></td>
                                        </tr>
                                    </c:when>
                                </c:choose>

                                <c:choose>
                                    <c:when test="${not empty book.publisher}">
                                        <tr>
                                            <td>Publisher:</td>
                                            <td><c:out value="${book.publisher}"/></td>
                                        </tr>
                                    </c:when>
                                </c:choose>
                                </tbody>
                            </table>
                            <c:choose>
                                <c:when test="${empty requestScope.readonly}">
                                    <span class="pull-right">
                                        <a href="c?reqtype=CART_ADD&book_id=${book.bookID}"
                                           class="btn btn-primary">Add to cart</a>
                                    </span>
                                    <span class="pull-right">
                                        <a href="c?reqtype=CART_ITEM" class="btn btn-primary">Go to cart</a>
                                    </span>
                                </c:when>
                            </c:choose>

                        </div>
                    </div>
                </div>
                <div class="panel-footer">
                    <c:choose>
                        <c:when test="${not empty requestScope.dup}">
                            Item is already in cart!
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
