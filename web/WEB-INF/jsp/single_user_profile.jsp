<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/pre.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container">
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h3 class="panel-title">${USER_INFO.firstname}&nbsp;${USER_INFO.lastname}</h3>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-3 col-lg-3 " align="left">
                            <img alt="User Pic"
                                 src="http://babyinfoforyou.com/wp-content/uploads/2014/10/avatar-300x300.png"
                                 class="img-circle img-responsive">
                        </div>

                        <div class=" col-md-12 col-lg-9 ">
                            <table class="table table-user-information">
                                <tbody>
                                <tr>
                                    <td>UserName</td>
                                    <td>${USER_INFO.username}</td>
                                </tr>
                                <tr>
                                    <td>Date of Birth</td>
                                    <td>${USER_INFO.birthday}</td>
                                </tr>
                                <tr>
                                <tr>
                                    <td>Address</td>
                                    <td>${USER_INFO.address}</td>
                                </tr>
                                <tr>
                                    <td>Email</td>
                                    <td>${USER_INFO.email}</td>
                                </tr>
                                <!-- LOGIN_LOG-->
                                <!-- CART_REMOVED-->
                                <!-- CART_ITEMS-->
                                <!-- TRANSACTIONS-->

                                </tbody>
                            </table>
                            <!-- LOGIN_LOG-->
                            <div class="list-group">
                                <a href="#" class="list-group-item active">Login Log</a>
                                <c:forEach items="${LOGIN_LOG}" var="login_log" varStatus="loop">
                                    <a href="#" class="list-group-item list-group-item-action"><c:out
                                            value="${login_log.time}"/></a>
                                </c:forEach>
                            </div>
                            <!-- CART_REMOVED-->
                            <h5 class="panel-title">Books Removed From Cart:</h5>
                            <table class="table .table-sm">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Book Title</th>
                                    <th>Added Time</th>
                                    <th>Removed Time</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:set var="count" value="${1}" scope="page"/>
                                <c:forEach items="${CART_REMOVED}" var="cart_removed" varStatus="loop">
                                    <tr>
                                        <th scope="row"><c:out value="${count}"/></th>
                                        <td><c:out value="${cart_removed.book.title}"/></td>
                                        <td><c:out value="${cart_removed.addedTime}"/></td>
                                        <td><c:out value="${cart_removed.removeTime}"/></td>
                                    </tr>
                                    <c:set var="count" value="${count + 1}" scope="page"/>
                                </c:forEach>

                                </tbody>
                            </table>
                            <!-- CART_ITEMS-->
                            <h5 class="panel-title">Item In Cart Currently:</h5>
                            <table class="table .table-sm">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Book Title</th>
                                    <th>Added Time</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:set var="count" value="${1}" scope="page"/>
                                <c:forEach items="${CART_ITEMS}" var="cart_removed" varStatus="loop">
                                    <tr>
                                        <th scope="row"><c:out value="${count}"/></th>
                                        <td><c:out value="${cart_removed.book.title}"/></td>
                                        <td><c:out value="${cart_removed.addedTime}"/></td>
                                    </tr>
                                    <c:set var="count" value="${count + 1}" scope="page"/>
                                </c:forEach>

                                </tbody>
                            </table>
                            <!-- TRANSACTIONS-->
                            <h5 class="panel-title">Item Has been bought:</h5>
                            <table class="table .table-sm">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Book Title</th>
                                    <th>Seller</th>
                                    <th>Price</th>
                                    <th>Time</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:set var="count" value="${1}" scope="page"/>
                                <c:forEach items="${TRANSACTIONS}" var="transactions" varStatus="loop">
                                    <tr>
                                        <th scope="row"><c:out value="${count}"/></th>
                                        <td><c:out value="${transactions.book.title}"/></td>
                                        <td><c:out value="${transactions.seller.username}"/></td>
                                        <td><c:out value="${transactions.book.price}"/>$</td>
                                        <td><c:out value="${transactions.time}"/></td>
                                    </tr>
                                    <c:set var="count" value="${count + 1}" scope="page"/>
                                </c:forEach>

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="panel-footer">
                    <a href="#" data-original-title="Broadcast Message" data-toggle="tooltip" type="button"
                       class="btn btn-sm btn-primary">Send Email</a>
                    <span class="pull-right">
                        <c:choose>
                            <c:when test="${USER_INFO.type_ == 1}">
                                <a data-original-title="Ban User" data-toggle="tooltip" type="button"
                                   class="btn btn-sm btn-danger"
                                   href="${pageContext.request.contextPath}/c?reqtype=BAN_USER&username=${USER_INFO.username}">Ban User</a>
                            </c:when>
                            <c:otherwise>
                                <a  data-original-title="Edit this user" data-toggle="tooltip" type="button" class="btn btn-sm btn-success"
                                    href="${pageContext.request.contextPath}/c?reqtype=UNBAN_USER&username=${USER_INFO.username}">Release User</a>
                            </c:otherwise>
                        </c:choose>
                        </span>
                </div>

            </div>
        </div>
    </div>
</div>

