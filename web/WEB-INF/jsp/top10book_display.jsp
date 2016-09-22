<%@ page import="bean.Book" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Mingxuan
  Date: 22/09/2016
  Time: 3:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="count" value="1" scope="page"/>
<table >
    <c:forEach items="${sessionScope.top10_BookSearch}" var="book" varStatus="loop">
        <tr>
            <td><br></td>
        </tr>
        <tr>
            <td>
                <h4><small>#${' '}${count}</small></h4>
                <a href=""><h4>${book.getTitle()}</h4></a>
                <h5><span class="label label-info">${book.getType()}</span></h5>
                <p>Authors:${' '}${book.getAuthors()}</p>
                <p>Year: ${book.getYear()}</p>
                <p>Visited times: ${book.getVisited()}</p>
                <form role="form" action="">
                    <button type="submit" class="btn btn-success">Add to Shopping Cart</button>
                </form>
            </td>
            <td>
                <img src="../../img/book${book.getPhotoid()}.jpg" width="200" height="200">
            </td>
        </tr>
        <c:set var="count" value="${count + 1}" scope="page"/>
    </c:forEach>
</table>

