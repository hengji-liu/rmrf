<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="WEB-INF/jsp/loginguard.jsp" %>
<html>
<head>
    <title>Managing Users</title>
</head>
<body>

<p>User Info: </p>
<c:out value="${requestScope.username}"/>
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

<p>User purchased items:</p>

<table>
    <c:forEach items="${sessionScope.TRANSACTIONS}" var="trans" varStatus="loop">
        <tr>
            <td><c:out value="${trans.seller.username}"/></td>
            <td><c:out value="${trans.book.title}"/></td>
            <td><c:out value="${trans.book.price}"/></td>
            <td><c:out value="${trans.time}"/></td>
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

<form action="BookTrade" method="post">
    <div class="form-group row">
        <input type="hidden" name="reqtype" value="BAN_USER">
        <input type="hidden" name="username" value="<c:out value="${requestScope.username}"/>"/>
        <div class="col-xs-2">
            <button type="submit" class="form-control btn">Ban This user!</button>
        </div>
    </div>
</form>

</body>
</html>
