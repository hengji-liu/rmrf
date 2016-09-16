package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.AdminService;
import service.UserService;

/**
 * Created by Linus on 10/09/2016.
 */
@WebServlet("/BookTrade")
public class Controller extends HttpServlet {

    private static final String REQ_TYPE = "reqtype";
    private static final String USER_LOGIN = "user_login";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        resolvedCommand(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        resolvedCommand(request, response);
    }

    private void resolvedCommand(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestType = request.getParameter(REQ_TYPE);

        AdminService adminService = null;
        UserService userService = null;
        switch (requestType) {
            case USER_LOGIN:
                userService = new UserService();
                userService.login(request, response);
                break;
            case "ADMIN_LOGIN":
                adminService = new AdminService();
                adminService.adminLogin(request, response);
                break;
            case "USER_SEARCH":
                adminService = new AdminService();
                adminService.searchUsers(request, response);
                break;
            case "BAN_USER":
                adminService = new AdminService();
                adminService.banAUser(request, response, true);
                break;
            case "USER_LOG":
                adminService = new AdminService();
                adminService.prepareUserActivity(request, response);
                break;
            case "UNBAN_USER":
                adminService = new AdminService();
                adminService.banAUser(request, response, false);
                break;
            case "USER_LIST":
                adminService = new AdminService();
                adminService.userList(request, response);
                break;
        }
    }

}
