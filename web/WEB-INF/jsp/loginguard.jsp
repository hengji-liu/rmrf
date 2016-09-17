<%--
  Created by IntelliJ IDEA.
  User: Linus
  Date: 14/09/2016
  Time: 10:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="service.AdminService"%>


<%

    String username = (String) session.getAttribute("admin_name");
    String ps = (String) session.getAttribute("password");
    AdminService service = new AdminService();
    if(!service.adminVerification(username,ps)){
        response.sendRedirect("../admin/admin_login.jsp");
    }
%>
