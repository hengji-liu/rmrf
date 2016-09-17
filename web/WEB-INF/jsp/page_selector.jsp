<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
    <c:when test="${not empty pager}">
        <ul>
            <c:choose>
                <c:when test="${pager.totalRecord > 0 && pager.currentPage!=1}">
                    <li><a href="${param.req_prefix}1">Front</a></li>
                </c:when>
                <c:otherwise>
                    <li><a>Front</a></li>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${pager.currentPage != 1}">
                    <li><a href="${param.req_prefix}<c:out value="${pager.currentPage-1}"/>">Prev</a></li>
                </c:when>
                <c:otherwise>
                    <li><a>Prev</a></li>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${pager.currentPage < pager.totalPage}">
                    <li><a href="${param.req_prefix}<c:out value="${pager.currentPage+1}"/>">Next</a></li>
                </c:when>
                <c:otherwise>
                    <li><a>Next</a></li>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${pager.currentPage < pager.totalPage}">
                    <li><a href="${param.req_prefix}<c:out value="${pager.totalPage}"/>">End</a></li>
                </c:when>
                <c:otherwise>
                    <li><a>End</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </c:when>
</c:choose>

