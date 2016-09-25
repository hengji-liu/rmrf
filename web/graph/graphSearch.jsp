<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>graphSearch</title>
</head>
<body>

<form action="/c" method="post">
    graph search:
    <input type="hidden" name="reqtype" value="GRAPH_SEARCH">
    <select name="searchType" >
        <option value="title">Title</option>
        <option value="author">Author</option>
        <option value="venue">Venue</option>
    </select>
    <select name="size" >
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
    </select>
    <input type="text" name="keyword" value="keyword">
    <input type="submit" name="search" value="search">
</form>
</body>
</html>
