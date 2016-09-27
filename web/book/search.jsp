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
<%@ page import="daoImpl.BookDaoImpl" %>
<html>
<head>
    <%
        if (session.getAttribute("itemNum_BookSearch") == null) {
            List validInputList = new ArrayList();
            validInputList.add(true);
            session.setAttribute("validInput_BookSearch", validInputList);
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
        if (session.getAttribute("display_BookSearch") == null) {
            Long time = System.currentTimeMillis();
            session.setAttribute("top10_BookSearch", new BookDaoImpl().top10());
            session.setAttribute("time_BookSearch", ((double)(System.currentTimeMillis() - time)) / 1000);
            session.setAttribute("display_BookSearch", "top10");
        }
    %>
    <title>Search Book</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        /* Set height of the grid so .sidenav can be 100% (adjust if needed) */
        <c:if test="${sessionScope.display_BookSearch == 'noResults'}">
            .row.content {height: ${sessionScope.itemNum_BookSearch * 100 + 700}px}
        </c:if>
        <c:if test="${sessionScope.display_BookSearch != 'noResults'}">
            .row.content {height: ${sessionScope.itemNum_BookSearch * 100 + 3000}px}
        </c:if>

        /* Set gray background color and 100% height */
        .sidenav {
            background-color: #f1f1f1;
            height: 100%;
            width: auto;
        }

        /* Set black background color, white text and some padding */
        footer {
            background-color: #555;
            color: white;
            padding: 15px;
        }

        /* On small screens, set height to 'auto' for sidenav and grid */
        @media screen and (max-width: 767px) {
            .sidenav {
                height: auto;
                padding: 15px;
                width: auto;
            }
            .row.content {height: auto;}
        }
    </style>
</head>

<body>
<div class="container-fluid">
    <div class="row content">
        <div class="col-sm-3 sidenav">
            <h4>Welcome to DBLP Searching Engine</h4>
            <ul class="nav nav-pills nav-stacked">
                <li><a href="/c?reqtype=TOP10_BOOK">Top 10 Visited</a></li>
                <li><a href="">Graph Search</a></li>
                <li><a href="">Shopping Cart</a></li>
                <li><a href="">Logout</a></li>
            </ul><br>
            <div class="input-group">
                <!--
                <input type="text" class="form-control" placeholder="Search Blog.."><span class="input-group-btn">
                <button class="btn btn-default" type="button"><span class="glyphicon glyphicon-search"></span></button>
                -->
                <form action="/c" method="post">
                    <input type="hidden" name="reqtype" value="SEARCH_BOOK">
                    <table border="0" width="330">
                        <tr>
                            <td colspan="3" align="right">
                                <button class="btn btn-default" type="submit" value="Reset" name="doWhat_BookSearch">Reset</button>
                                <button class="btn btn-default" type="submit" value="-" name="doWhat_BookSearch">-</button>
                                <button class="btn btn-default" type="submit" value="+" name="doWhat_BookSearch">+</button>
                                <button class="btn btn-default" type="submit" value="Search" name="doWhat_BookSearch">Search
                                    <span class="glyphicon glyphicon-search"></span>
                                </button>
                            </td>
                        </tr>
                        <c:forEach var="i" begin="1" end="${sessionScope.itemNum_BookSearch}" step="1">
                            <c:if test="${sessionScope.validInput_BookSearch.get(i - 1) == false}">
                                <tr>
                                    <td colspan="3" align="left">
                                        <br>
                                        <span class="label label-primary">Key word ${i}:</span>
                                        <span class="label label-danger">Invalid input!</span>
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${sessionScope.validInput_BookSearch.get(i - 1) == true}">
                                <tr>
                                    <td colspan="3" align="left">
                                        <br>
                                        <span class="label label-primary">Key word ${i}:</span>
                                    </td>
                                </tr>
                            </c:if>
                            <tr>
                                <td></td>
                                <td>
                                    <c:if test="${sessionScope.whereList_BookSearch.get(i - 1) == 'title'}">
                                        <select name="where${i}" class="form-control">
                                            <option value="title">Title</option>
                                            <option value="authors">Author</option>
                                            <option value="editors">Editor</option>
                                            <option value="venue">Venue</option>
                                            <option value="publisher">Publisher</option>
                                            <option value="isbn">ISBN</option>
                                        </select>
                                    </c:if>
                                    <c:if test="${sessionScope.whereList_BookSearch.get(i - 1) == 'authors'}">
                                        <select name="where${i}" class="form-control">
                                            <option value="authors">Author</option>
                                            <option value="title">Title</option>
                                            <option value="editors">Editor</option>
                                            <option value="venue">Venue</option>
                                            <option value="publisher">Publisher</option>
                                            <option value="isbn">ISBN</option>
                                        </select>
                                    </c:if>
                                    <c:if test="${sessionScope.whereList_BookSearch.get(i - 1) == 'editors'}">
                                        <select name="where${i}" class="form-control">
                                            <option value="editors">Editor</option>
                                            <option value="title">Title</option>
                                            <option value="authors">Author</option>
                                            <option value="venue">Venue</option>
                                            <option value="publisher">Publisher</option>
                                            <option value="isbn">ISBN</option>
                                        </select>
                                    </c:if>
                                    <c:if test="${sessionScope.whereList_BookSearch.get(i - 1) == 'venue'}">
                                        <select name="where${i}" class="form-control">
                                            <option value="venue">Venue</option>
                                            <option value="title">Title</option>
                                            <option value="authors">Author</option>
                                            <option value="editors">Editor</option>
                                            <option value="publisher">Publisher</option>
                                            <option value="isbn">ISBN</option>
                                        </select>
                                    </c:if>
                                    <c:if test="${sessionScope.whereList_BookSearch.get(i - 1) == 'publisher'}">
                                        <select name="where${i}" class="form-control">
                                            <option value="publisher">Publisher</option>
                                            <option value="title">Title</option>
                                            <option value="authors">Author</option>
                                            <option value="editors">Editor</option>
                                            <option value="venue">Venue</option>
                                            <option value="isbn">ISBN</option>
                                        </select>
                                    </c:if>
                                    <c:if test="${sessionScope.whereList_BookSearch.get(i - 1) == 'isbn'}">
                                        <select name="where${i}" class="form-control">
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
                                    <input type="text" name="what${i}" value="${sessionScope.whatList_BookSearch.get(i - 1)}" class="form-control" placeholder="search details...">
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    <select name="how${i}" class="form-control">
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
                                <td></td>
                            </tr>
                        </c:forEach>
                        <tr><td><br></td></tr>
                        <tr>
                            <td colspan="2" align="left"><span class="label label-primary">From year:</span></td>
                        </tr>
                        <tr>
                            <td colspan="2" width="30%"><input type="text" name="from" value="${sessionScope.from_BookSearch}" placeholder="included..." class="form-control"></td>
                        </tr>
                        <tr><td><br></td></tr>
                        <tr>
                            <td colspan="2" align="left"><span class="label label-primary">To year:</span></td>
                        </tr>
                        <tr>
                            <td colspan="2"><input type="text" name="to" value="${sessionScope.to_BookSearch}" placeholder="included..." class="form-control"></td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
        <div class="row">
            <table>
                <tr>
                    <td>&nbsp&nbsp&nbsp&nbsp&nbsp</td>
                    <td>
                        <c:if test="${sessionScope.display_BookSearch == 'top10'}">
                            <h2>Top 10 Visited Records</h2>
                            <h4><small>(${sessionScope.time_BookSearch} seconds)</small></h4>
                            <hr>
                            <c:set var="count" value="1" scope="page"/>
                            <table >
                                <c:forEach items="${sessionScope.top10_BookSearch}" var="book" varStatus="loop">
                                    <tr>
                                        <td><br></td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <h4><small>#${' '}${count}</small></h4>
                                            <a target="_blank" href="c?reqtype=BOOK_DETAIL&book_id=${book.bookID}"><h4>${book.getTitle()}</h4></a>  <!-- **************************************** the link for top 10 page is here **************************************** -->
                                            <h5><span class="label label-info">${book.getType()}</span></h5>
                                            <p>Authors:${' '}${book.getAuthors()}</p>
                                            <p>Year: ${book.getYear()}</p>
                                            <p>Price: $${book.getPrice()}</p>
                                            <p>Visited times: ${book.getVisited()}</p>
                                            <form role="form" action="">
                                                <input type="hidden" name="reqtype" value="CART_ADD">
                                                <input type="hidden" name="book_id" value="${book.bookID}">
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
                        </c:if>
                        <c:if test="${sessionScope.display_BookSearch == 'noResults'}">
                            <h2>Search Results</h2>
                            <h4><small>0 result (${sessionScope.time_BookSearch} seconds)</small></h4>
                            <hr>
                            <img align="center" src="../../img/noresults.jpg" class="img-responsive" alt="Cinque Terre">
                        </c:if>
                        <c:if test="${sessionScope.display_BookSearch == 'showResults'}">
                            <h2>Search Results</h2>
                            <h4><small>${sessionScope.pager_BookSearch.getTotalRecord()} results (${sessionScope.time_BookSearch} seconds)</small></h4>
                            <hr>
                            <c:set var="count" value="1" scope="page"/>
                            <table >
                                <c:forEach items="${sessionScope.top10_BookSearch}" var="book" varStatus="loop">
                                    <tr>
                                        <td><br></td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <h4><small>#${' '}${count}</small></h4>
                                            <a target="_blank" href="c?reqtype=BOOK_DETAIL&book_id=${book.bookID}"><h4>${book.getTitle()}</h4></a> <!-- **************************************** the link for search result page is here **************************************** -->
                                            <h5><span class="label label-info">${book.getType()}</span></h5>
                                            <p>Authors:${' '}${book.getAuthors()}</p>
                                            <p>Year: ${book.getYear()}</p>
                                            <p>Price: $${book.getPrice()}</p>
                                            <p>Visited times: ${book.getVisited()}</p>
                                            <form role="form" action="">
                                                <input type="hidden" name="reqtype" value="CART_ADD">
                                                <input type="hidden" name="book_id" value="${book.bookID}">
                                                <button type="submit" class="btn btn-success">Add to Shopping Cart</button>
                                            </form>
                                        </td>
                                        <td>
                                            <img src="../../img/book${book.getPhotoid()}.jpg" width="200" height="200">
                                        </td>
                                    </tr>
                                    <c:set var="count" value="${count + 1}" scope="page"/>
                                </c:forEach>
                                <tr><td><br></td></tr>
                                <tr><td><br></td></tr>
                                <tr><td><br></td></tr>
                                <tr>
                                    <td colspan="2" align="center">
                                        <form action="/c" method="post">
                                            <input type="hidden" name="reqtype" value="PAGE_BOOK">
                                            <table style="width:100%">
                                                <tr>
                                                    <td align="right" width="44%">
                                                        <c:if test="${sessionScope.pager_BookSearch.getCurrentPage() == 1}">
                                                            <button type="submit" class="btn btn-warning" disabled><c:out value="<Previous"></c:out></button>
                                                        </c:if>
                                                        <c:if test="${sessionScope.pager_BookSearch.getCurrentPage() != 1}">
                                                            <button type="submit" class="btn btn-primary" name="btn" value="-"><c:out value="<Previous"></c:out></button>
                                                        </c:if>
                                                    </td>
                                                    <td width="10%">
                                                        <input type="text" class="form-control" placeholder="Page..."  name="num">
                                                    </td>
                                                    <td width="4%">
                                                        <button class="btn btn-default" type="submit" name="btn" value="?">Go</button>
                                                    </td>
                                                    <td align="left">
                                                        <c:if test="${sessionScope.pager_BookSearch.getCurrentPage() == sessionScope.pager_BookSearch.getTotalPage()}">
                                                            <button type="submit" class="btn btn-warning" disabled><c:out value="Next>"></c:out></button>
                                                        </c:if>
                                                        <c:if test="${sessionScope.pager_BookSearch.getCurrentPage() != sessionScope.pager_BookSearch.getTotalPage()}">
                                                            <button type="submit" class="btn btn-primary" name="btn" value="+"><c:out value="Next>"></c:out></button>
                                                        </c:if>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="6" align="center">
                                                        <h4><small>Total Page: ${sessionScope.pager_BookSearch.getTotalPage()}, Current Page: ${sessionScope.pager_BookSearch.getCurrentPage()}</small></h4>
                                                    </td>
                                                </tr>
                                            </table>
                                        </form>
                                    </td>
                                </tr>
                            </table>
                        </c:if>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>

<footer class="container-fluid">
    <h7>UNSW</h7>
    <h6>High St</h6>
    <h6>Kensington, NSW 2052</h6>
    <h6>Australia</h6>
</footer>

</body>
</html>
