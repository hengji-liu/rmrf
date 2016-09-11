package servlet;

import bean.User;
import service.AdminService;
import util.StringUtil;

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
        String requestType = request.getParameter("reqtype");
        AdminDispatcher adminDispatcher = null;
        switch (requestType) {
            case "ADMIN_LOGIN":
                adminDispatcher = new AdminDispatcher();
                adminDispatcher.doAdminLogin(request,response);
                break;
            case "USER_SEARCH":
                adminDispatcher = new AdminDispatcher();
                adminDispatcher.searchUsers(request,response);

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    //Admin Service starts...


    //public void doAdmin

    //Admin Service ends...


}
