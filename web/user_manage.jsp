<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Managing Users</title>
</head>
<body>
<p>User Info: </p>
<c:out value="${log.user.username}"/>
<p>Login log: </p>

<table>
    <c:forEach items="${sessionScope.LOGIN_LOG}" var="log" varStatus="loop">
        <tr>
            <td><c:out value="${log.user.username}"/></td>
            <td><c:out value="${log.time}"/></td>
            <td><c:out value="${log.granted}"/></td>
        </tr>
    </c:forEach>
</table>

<p>Users current cart: </p>

<table>
    <c:forEach items="${sessionScope.CART_ITEMS}" var="cartItem" varStatus="loop">
        <tr>
            <td><c:out value="${cartItem.book.bookID}"/></td>
            <td><c:out value="${cartItem.addedTime}"/></td>
        </tr>
    </c:forEach>
</table>


<p>Users removed item from cart: </p>

<table>
    <c:forEach items="${sessionScope.CART_REMOVED}" var="cartItem" varStatus="loop">
        <tr>
            <td><c:out value="${cartItem.book.title}"/></td>
            <td><c:out value="${cartItem.addedTime}"/></td>
            <td><c:out value="${cartItem.removeTime}"/></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
