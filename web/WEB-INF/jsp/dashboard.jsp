<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container-fluid">
    <div class="row content">
        <div class="col-sm-12">
            <div class="row">
                <div class="col-sm-4">
                    <div class="well">
                        <h4>Total: Publications</h4>
                        <p>${requestScope.totalbook}</p>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="well">
                        <h4>Incollections</h4>
                        <p>${requestScope.incollection}</p>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="well">
                        <h4>Books</h4>
                        <p>${requestScope.book}</p>
                    </div>
                </div>
            </div>
        </div>


        <div class="col-sm-12">
            <div class="row">
                <div class="col-sm-4">
                    <div class="well">
                        <h4>Mastersthesis</h4>
                        <p>${requestScope.mastersthesis}</p>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="well">
                        <h4>Proceedings</h4>
                        <p>${requestScope.proceedings}</p>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="well">
                        <h4>Inproceedings</h4>
                        <p>${requestScope.inproceedings}</p>
                    </div>
                </div>
            </div>
        </div>


        <div class="col-sm-12">
            <div class="row">
                <div class="col-sm-4">
                    <div class="well">
                        <h4>Phdthesis</h4>
                        <p>${requestScope.phdthesis}</p>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="well">
                        <h4>Article</h4>
                        <p>${requestScope.article}</p>
                    </div>
                </div>
                <%--<div class="col-sm-4">--%>
                    <%--<div class="well">--%>
                        <%--<h4>inproceedings</h4>--%>
                        <%--<p>${requestScope.inproceedings}</p>--%>
                    <%--</div>--%>
                <%--</div>--%>
            </div>
        </div>


    </div>




</div>


