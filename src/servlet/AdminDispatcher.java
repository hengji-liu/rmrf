package servlet;

import bean.CartItem;
import bean.LoginLog;
import bean.User;
import service.AdminService;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Linus on 11/09/2016.
 */
public class AdminDispatcher {

    public void doAdminLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        String userName = request.getParameter("admin_name");
        String password = request.getParameter("password");
        if(StringUtil.isArgumentsContainNull(userName,password)) return;
        AdminService adminService = new AdminService();
        if (adminService.adminLogin(userName,password)){ //Admin Identity verified:
            List<User> userList = adminService.getAllUser(); //get all users for display
            //TODO: set Cokkies of username and assword
            request.getSession().setAttribute("UERLIST",userList);
            response.sendRedirect("admin.jsp");
        }else{
            request.getRequestDispatcher("adminLogin.jsp").forward(request,response);
        }
    }


    public void searchUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        String keyWord = request.getParameter("keyword");
        if(StringUtil.isArgumentsContainNull(keyWord)) return;
        AdminService adminService = new AdminService();
        List<User> userList = adminService.searchUser(keyWord);
        request.setAttribute("USER_SEARCH_RESULT",userList);
        request.getRequestDispatcher("admin.jsp").forward(request,response);
    }

    public void prepareUserActivity(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        String userName = request.getParameter("username");
        if(StringUtil.isArgumentsContainNull(userName)) return;
        AdminService adminService = new AdminService();
        List<LoginLog> loginLogList = adminService.getLoginLog(userName);
        List<CartItem> cartRemovedList = adminService.getAddedButRemovedItems(userName);
        List<CartItem> cartItems = adminService.getCartItems(userName);
        request.getSession().setAttribute("LOGIN_LOG",loginLogList);
        request.getSession().setAttribute("CART_REMOVED",cartRemovedList);
        request.getSession().setAttribute("CART_ITEMS",cartItems);
        response.sendRedirect("user_manage.jsp");
    }

}
