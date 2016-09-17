<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
    <c:when test="${not empty pager}">
        <table class="table">
            <thead>
            <tr>
                <th>Num</th>
                <th>UserName</th>
                <th>First Name</th>
                <th>Second Name</th>
                <th>Email</th>
                <th>Birthday</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="count" value="${(pager.currentPage-1)*10+1}" scope="page"/>
            <c:forEach items="${pager.dataList}" var="user" varStatus="loop">
                <tr>
                    <th scope="row"><c:out value="${count}"/></th>
                    <td><c:out value="${user.username}"/></td>
                    <td><c:out value="${user.firstname}"/></td>
                    <td><c:out value="${user.lastname}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.birthday}"/></td>
                </tr>
                <c:set var="count" value="${count + 1}" scope="page"/>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
</c:choose>
