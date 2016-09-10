package servlet;

import service.AdminManager;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Linus on 10/09/2016.
 */
@WebServlet("/Controler")
public class Controler extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestType = request.getParameter("sevice_requestType");

        switch (requestType) {
            case "admin_login":
                doAdminLogin(request,response);
                break;
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


    public void doAdminLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        String userName = request.getParameter("admin_name");
        String password = request.getParameter("password");
        if(StringUtil.isArgumentsContainNull(userName,password)) return;

        AdminManager adminManager = new AdminManager();
        if (adminManager.adminLogin(userName,password)){
            request.getRequestDispatcher("admin.jsp").forward(request,response);
        }else{
            request.getRequestDispatcher("adminLogin.jsp").forward(request,response);
        }
    }


}
