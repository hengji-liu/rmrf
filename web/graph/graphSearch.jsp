<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<html>
<head>
    <title>graphSearch</title>
</head>
<body>

<form action="/c" method="post">
    graph search:
    <input type="hidden" name="reqtype" value="GRAPH_SEARCH">
    <select name="searchType" >
        <option value="Paper">Paper</option>
        <option value="Author">Author</option>
        <option value="Venue">Venue</option>
    </select>
    <input type="text" name="keyword" value="keyword">
    <input type="text" name="maxNumberOfNodes" value="30">
    <input type="submit" name="search" value="search">
</form>
</body>
</html>
