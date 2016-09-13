package service;

import bean.*;
import daoImpl.AdminDaoImpl;
import daoImpl.CartDaoImpl;
import daoImpl.UserDaoImpl;
import daoIterface.AdminDao;
import daoIterface.CartDao;
import daoIterface.UserDao;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.List;

/**
 * Business logic layer
 * Created by Linus on 10/09/2016.
 */
public class AdminService {

    public boolean adminLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {

        String userName = request.getParameter("admin_name");
        String password = request.getParameter("password");
        if (StringUtil.isArgumentsContainNull(userName, password)) return false;

        AdminDao adminDao = new AdminDaoImpl();
        if (adminDao.adminLogin(userName, password)) {
            List<User> userList = getAllUser(); //prepare user list
            request.getSession().setAttribute("UERLIST", userList);
            response.sendRedirect("admin.jsp");
        } else {
            request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
        }

        return adminDao.adminLogin(userName, password);
    }

    public void searchUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        String keyWord = request.getParameter("keyword");
        if(StringUtil.isArgumentsContainNull(keyWord)) return;
        List<User> userList = searchUser(keyWord);
        request.setAttribute("USER_SEARCH_RESULT",userList);
        request.getRequestDispatcher("admin.jsp").forward(request,response);
    }


    public void prepareUserActivity(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        String userName = request.getParameter("username");
        if(StringUtil.isArgumentsContainNull(userName)) return;
        List<LoginLog> loginLogList = getLoginLog(userName);
        List<CartItem> cartRemovedList = getAddedButRemovedItems(userName);
        List<CartItem> cartItems = getCartItems(userName);
        List<BookTransaction> bookTransactions = getBookTransactions(userName);
        request.getSession().setAttribute("LOGIN_LOG",loginLogList);
        request.getSession().setAttribute("CART_REMOVED",cartRemovedList);
        request.getSession().setAttribute("CART_ITEMS",cartItems);
        request.getSession().setAttribute("TRANSACTIONS",bookTransactions);
        request.setAttribute("username",userName);
        request.getRequestDispatcher("user_manage.jsp").forward(request,response);
    }


    public void banAUser(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        String userName = request.getParameter("username");
        if(StringUtil.isArgumentsContainNull(userName)) return;
        AdminDao adminDao = new AdminDaoImpl();
        if(!adminDao.banOrReleaseUser(userName, true)){
            //TODO: Add error
        }

    }

    private List<BookTransaction> getBookTransactions(String userName){
        AdminDao adminDao = new AdminDaoImpl();
        List<BookTransaction> bookTransactions = adminDao.purchasedItems(userName);
        if (bookTransactions == null || bookTransactions.size() == 0) return null;
        return bookTransactions;
    }

    private List<LoginLog> getLoginLog(String userName) {
        UserDao userDao = new UserDaoImpl();
        List<LoginLog> loginLogList = userDao.getLoginLog(userName);
        if (loginLogList == null || loginLogList.size() == 0) return null;
        return loginLogList;
    }

    private List<CartItem> getAddedButRemovedItems(String userName) {
        CartDao cartDao = new CartDaoImpl();
        List<CartItem> cartItemList = cartDao.getAddedThenRemovedItemsInCart(userName);
        if (cartItemList == null || cartItemList.size() == 0) return null;
        return cartItemList;
    }

    private List<CartItem> getCartItems(String userName) {
        CartDao cartDao = new CartDaoImpl();
        List<CartItem> cartItemList = cartDao.getUserCartItems(userName);
        if (cartItemList == null || cartItemList.size() == 0) return null;
        return cartItemList;
    }

    public boolean releaseUser(String userName) {
        AdminDao adminDao = new AdminDaoImpl();
        return adminDao.banOrReleaseUser(userName, false);
    }

    private List<User> getAllUser() {
        UserDao userDao = new UserDaoImpl();
        List<User> userList = userDao.getAllUsers();
        if (userList == null || userList.size() == 0) return null;
        return userList;
    }

    private List<User> searchUser(String keyWord) {
        UserDao userDao = new UserDaoImpl();
        List<User> userList = userDao.searchUserByName(keyWord);
        if (userList == null || userList.size() == 0) return null;
        return userList;
    }

}

