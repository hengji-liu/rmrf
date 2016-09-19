<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Mingxuan
  Date: 19/09/2016
  Time: 8:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, java.util.ArrayList"%>
<html>
<head>
    <title>Book Searching</title>
</head>
<body>
    <%
        request.setAttribute("reqtype", "SEARCH_BOOK");
        if (request.getAttribute("itemNum") == null)
            request.setAttribute("itemNum", 1);
    %>
    <form action="c" method="post">
        <table border="0">
            <c:forEach var="i" begin="1" end="${requestScope.itemNum}" step="1">
                <tr>
                    <td>Key Word ${i}: </td>
                    <td><select name="where${i}">
                        <option value="title">Title</option>
                        <option value="author">Author</option>
                        <option value="editor">Editor</option>
                        <option value="venue">Venue</option>
                        <option value="publisher">Publisher</option>
                        <option value="isbn">ISBN</option>
                    </select> </td>
                    <td>
                        <input type="text" name="what${i}">
                    </td>
                    <td><select name="how${i}">
                        <option value="and">AND</option>
                        <option value="or">OR</option>
                    </select> </td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="4" align="right">
                    <input type="button" value="more" name="doWhat">
                </td>
            </tr>
            <tr>
                <td></td><td align="right">From:</td>
                <td><input type="text" name="from"></td>
            </tr>
            <tr>
                <td></td><td align="right">To:</td>
                <td><input type="text" name="to"></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="button" value="Reset" name="doWhat">
                </td>
                <td colspan="2" align="center">
                    <input type="button" value="Search" name="doWhat">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
