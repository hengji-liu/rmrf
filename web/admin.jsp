<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Managing BBLIB</title>
</head>
<body>
<h1> Hello Admin</h1>

<p>List of all users(with page split!)</p>
<c:out value="${'hello'}"/>

<table>
    <c:forEach items="${sessionScope.UERLIST}" var="user" varStatus="loop">
        <tr>
            <td>
                <a href="BookTrade?reqtype=USER_LOG&username=<c:out value="${user.username}"/>">
                    <c:out value="${user.username}"/></a>
            <td>
            <td><c:out value="${user.firstname}"/></td>
            <td><c:out value="${user.lastname}"/></td>
            <td><c:out value="${user.email}"/></td>
            <td><c:out value="${user.birthday}"/></td>
        </tr>
    </c:forEach>
</table>

<form action="BookTrade" method="post">
    <!-- row one -->
    <div class="form-group row">
        <input type="hidden" name="reqtype" value="USER_SEARCH">
        <div class="col-xs-3">
            <label>User Search:</label> <input
                type="text" class="form-control"
                name="keyword" placeholder="Username/Real Name">
        </div>

        <div class="col-xs-2">
            <button type="submit" class="form-control btn">Search</button>
        </div>

    </div>
</form>

<p>Search Result:</p>

<table>
    <c:forEach items="${USER_SEARCH_RESULT}" var="user" varStatus="loop">
        <tr>
            <td><c:out value="${user.username}"/></td>
            <td><c:out value="${user.firstname}"/></td>
            <td><c:out value="${user.lastname}"/></td>
            <td><c:out value="${user.email}"/></td>
            <td><c:out value="${user.birthday}"/></td>

        </tr>
    </c:forEach>
</table>

</body>
</html>
