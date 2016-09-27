<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/pre.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Your Cart</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">My Cart</h1>
            </div>
            <%--Display books--%>
            <form action="c" method="post">
                <input type="hidden" name="reqtype" value="REMOVE_BOOK">
                <c:choose>
                    <c:when test="${not empty requestScope.CART_ITEMS}">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>Title</th>
                                <th>Buy</th>
                                <th>Remove</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:set var="count" value="${1}" scope="page"/>
                            <c:forEach items="${requestScope.CART_ITEMS}" var="item" varStatus="loop">
                                <tr>
                                    <th scope="row"><c:out value="${count}"/></th>
                                        <%--TODO: title be an link--%>
                                    <td><c:out value="${item.book.title}"/></td>
                                    <td>
                                        <a href="c?reqtype=BOOK_BUY&book_id=${item.book.bookID}" type="button" class="btn btn-default btn-lg">Buy</a>
                                    </td>
                                    <th>
                                        <a href="c?reqtype=CART_REMOVE&book_id=${item.book.bookID}"
                                           type="button" class="btn btn-default btn-lg">Remove</a>
                                    </th>
                                </tr>
                                <c:set var="count" value="${count + 1}" scope="page"/>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <p>Cart is empty!</p>
                    </c:otherwise>
                </c:choose>

            </form>
        </div>

        <!-- page selector-->
    </div>
</div>


</body>
</html>
