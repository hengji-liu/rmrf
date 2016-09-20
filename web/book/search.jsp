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
    if (session.getAttribute("itemNum_BookSearch") == null) {
        session.setAttribute("itemNum_BookSearch", new Integer(1));
        List whereList = new ArrayList();
        whereList.add("title");
        session.setAttribute("whereList_BookSearch", whereList);
        List whatList = new ArrayList();
        whatList.add("");
        session.setAttribute("whatList_BookSearch", whatList);
        List howList = new ArrayList();
        howList.add("and");
        session.setAttribute("howList_BookSearch", howList);
    }
%>
<form action="/c" method="post">
    <input type="hidden" name="reqtype" value="SEARCH_BOOK">
    <table border="0">
        <tr>
            <td colspan="3" align="right">
                <input type="submit" value="Reset" name="doWhat_BookSearch">
                <input type="submit" value="-" name="doWhat_BookSearch">
                <input type="submit" value="+" name="doWhat_BookSearch">
            </td>
        </tr>
        <c:forEach var="i" begin="1" end="${sessionScope.itemNum_BookSearch}" step="1">
            <tr>
                <td>Key Word ${i}: </td>
                <td>
                    <c:if test="${sessionScope.whereList_BookSearch.get(i - 1) == 'title'}">
                        <select name="where${i}">
                            <option value="title">Title</option>
                            <option value="authors">Author</option>
                            <option value="editors">Editor</option>
                            <option value="venue">Venue</option>
                            <option value="publisher">Publisher</option>
                            <option value="isbn">ISBN</option>
                        </select>
                    </c:if>
                    <c:if test="${sessionScope.whereList_BookSearch.get(i - 1) == 'authors'}">
                        <select name="where${i}">
                            <option value="authors">Author</option>
                            <option value="title">Title</option>
                            <option value="editors">Editor</option>
                            <option value="venue">Venue</option>
                            <option value="publisher">Publisher</option>
                            <option value="isbn">ISBN</option>
                        </select>
                    </c:if>
                    <c:if test="${sessionScope.whereList_BookSearch.get(i - 1) == 'editors'}">
                        <select name="where${i}">
                            <option value="editors">Editor</option>
                            <option value="title">Title</option>
                            <option value="authors">Author</option>
                            <option value="venue">Venue</option>
                            <option value="publisher">Publisher</option>
                            <option value="isbn">ISBN</option>
                        </select>
                    </c:if>
                    <c:if test="${sessionScope.whereList_BookSearch.get(i - 1) == 'venue'}">
                        <select name="where${i}">
                            <option value="venue">Venue</option>
                            <option value="title">Title</option>
                            <option value="authors">Author</option>
                            <option value="editors">Editor</option>
                            <option value="publisher">Publisher</option>
                            <option value="isbn">ISBN</option>
                        </select>
                    </c:if>
                    <c:if test="${sessionScope.whereList_BookSearch.get(i - 1) == 'publisher'}">
                        <select name="where${i}">
                            <option value="publisher">Publisher</option>
                            <option value="title">Title</option>
                            <option value="authors">Author</option>
                            <option value="editors">Editor</option>
                            <option value="venue">Venue</option>
                            <option value="isbn">ISBN</option>
                        </select>
                    </c:if>
                    <c:if test="${sessionScope.whereList_BookSearch.get(i - 1) == 'isbn'}">
                        <select name="where${i}">
                            <option value="isbn">ISBN</option>
                            <option value="title">Title</option>
                            <option value="authors">Author</option>
                            <option value="editors">Editor</option>
                            <option value="venue">Venue</option>
                            <option value="publisher">Publisher</option>
                        </select>
                    </c:if>
                </td>
                <td>
                    <input type="text" name="what${i}" value="${sessionScope.whatList_BookSearch.get(i - 1)}">
                </td>
                <td>
                    <select name="how${i}">
                        <c:if test="${sessionScope.howList_BookSearch.get(i - 1) == 'and'}">
                            <option value="and">AND</option>
                            <option value="or">OR</option>
                        </c:if>
                        <c:if test="${sessionScope.howList_BookSearch.get(i - 1) == 'or'}">
                            <option value="or">OR</option>
                            <option value="and">AND</option>
                        </c:if>
                    </select>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="2" align="right">From year (included): </td>
            <td><input type="text" name="from" value="${sessionScope.from_BookSearch}"></td>
        </tr>
        <tr>
            <td colspan="2" align="right">To year (included): </td>
            <td><input type="text" name="to" value="${sessionScope.to_BookSearch}"></td>
        </tr>
        <tr>
            <td colspan="3" align="right">
                <input type="submit" value="Search" name="doWhat_BookSearch">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
