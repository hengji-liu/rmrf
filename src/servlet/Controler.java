package servlet;

import bean.User;
import service.AdminService;
import util.StringUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Linus on 10/09/2016.
 */
@WebServlet("/BookTrade")
public class Controler extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        resolvedCommand(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        resolvedCommand(request,response);
    }

    private void resolvedCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String requestType = request.getParameter("reqtype");
        AdminService adminService = null;
        switch (requestType) {
            case "ADMIN_LOGIN":
                adminService = new AdminService();
                adminService.adminLogin(request,response);
                break;
            case "USER_SEARCH":
                adminService = new AdminService();
                adminService.searchUsers(request,response);
                break;
            case "BAN_USER":
                adminService = new AdminService();
                adminService.banAUser(request,response);
                break;
            case "USER_LOG":
                adminService = new AdminService();
                adminService.prepareUserActivity(request,response);
                break;
        }
    }

}
