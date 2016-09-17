package service;

import bean.*;
import daoImpl.AdminDaoImpl;
import daoImpl.CartDaoImpl;
import daoImpl.UserDaoImpl;
import daoImpl.UserListDaoImpl;
import daoIterface.AdminDao;
import daoIterface.CartDao;
import daoIterface.UserDao;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Business logic layer
 * Created by Linus on 10/09/2016.
 */
public class AdminService {

    public boolean adminLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userName = request.getParameter("admin_name");
        String password = request.getParameter("password");
        if (StringUtil.isArgumentsContainNull(userName, password)) return false;

        AdminDao adminDao = new AdminDaoImpl();
        if (adminDao.adminLogin(userName, password)) {
            request.getSession().setAttribute("admin_name", userName);
            request.getSession().setAttribute("password", password);
            request.getRequestDispatcher("admin/admin_overall.jsp").forward(request, response);

        } else {
            request.setAttribute("wrong", "true");
            request.getRequestDispatcher("admin/admin_login.jsp").forward(request, response);
        }

        return adminDao.adminLogin(userName, password);
    }


    public void userList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageNum = request.getParameter("user_page");
        UserListDaoImpl userListDao = new UserListDaoImpl();
        Pager<User> pager = null;
        if (pageNum == null || pageNum.equals("1")) {
            pager = userListDao.getUserPage(1);
        } else {
            pager = userListDao.getUserPage(Integer.parseInt(pageNum));
        }
        request.setAttribute("pager", pager);
        request.getRequestDispatcher("admin/admin_all_users.jsp").forward(request, response);
    }

    public void searchUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyWord = request.getParameter("keyword");
        if (StringUtil.isArgumentsContainNull(keyWord)) return;
        List<User> userList = searchUser(keyWord);
        request.setAttribute("USER_SEARCH_RESULT", userList);
        request.getRequestDispatcher("admin_all_users.jsp").forward(request, response);
    }

    public void prepareUserActivity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("username");
        if (StringUtil.isArgumentsContainNull(userName)) return;
        List<LoginLog> loginLogList = getLoginLog(userName);
        List<CartItem> cartRemovedList = getAddedButRemovedItems(userName);
        List<CartItem> cartItems = getCartItems(userName);
        List<BookTransaction> bookTransactions = getBookTransactions(userName);
        User userInfo = getUser(userName);
        request.getSession().setAttribute("LOGIN_LOG", loginLogList);
        request.getSession().setAttribute("CART_REMOVED", cartRemovedList);
        request.getSession().setAttribute("CART_ITEMS", cartItems);
        request.getSession().setAttribute("TRANSACTIONS", bookTransactions);
        request.getSession().setAttribute("USER_INFO", userInfo);
        request.getRequestDispatcher("user_search_admin.jsp").forward(request, response);
    }

    public void banAUser(HttpServletRequest request, HttpServletResponse response, boolean ban) throws ServletException, IOException {
        String userName = request.getParameter("username");
        if (StringUtil.isArgumentsContainNull(userName)) return;
        AdminDao adminDao = new AdminDaoImpl();
        if (!adminDao.banOrReleaseUser(userName, ban)) {
            //TODO: Add err
        }
        User userInfo = getUser(userName);
        request.getSession().setAttribute("USER_INFO", userInfo);
        request.getRequestDispatcher("user_search_admin.jsp").forward(request, response);

    }

    private User getUser(String userName) {
        UserDao userDao = new UserDaoImpl();
        return userDao.getUserByUserName(userName);
    }

    private List<BookTransaction> getBookTransactions(String userName) {
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


    private List<User> searchUser(String keyWord) {
        UserDao userDao = new UserDaoImpl();
        List<User> userList = userDao.searchUserByName(keyWord);
        if (userList == null || userList.size() == 0) return null;
        return userList;
    }

    public boolean adminVerification(String userName, String password) {
        AdminDao adminDao = new AdminDaoImpl();
        return adminDao.adminLogin(userName, password);
    }


}

