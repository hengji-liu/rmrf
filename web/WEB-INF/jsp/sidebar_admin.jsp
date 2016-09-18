<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div class="navbar-default sidebar" role="navigation">
    <div class="sidebar-nav navbar-collapse">

        <ul class="nav" id="side-menu">
            <li>
                <a href="#">Site Management</a>

            </li>

            <li>
                <a href="c?reqtype=BOOK_LIST_ALL&user_page=1">Book Management</a>
            </li>

            <li>
                <a href="admin/admin_all_users.jsp">User Management</a>

                <ul class="nav nav-second-level">
                    <li>
                        <a href="c?reqtype=USER_LIST&user_page=1">View All Users</a>
                    </li>
                    <li>
                        <a href="admin/admin_usersearch.jsp">Search Users</a>
                    </li>
                </ul>
            </li>

        </ul>

    </div>
</div>