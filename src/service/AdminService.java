package service;

import bean.*;
import daoImpl.*;
import daoIterface.*;
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
        UserDao userListDao = new UserDaoImpl();
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
        String keyWord = request.getParameter("username");
        String pageNum = request.getParameter("user_page");
        Pager<User> pager = null;
        if (pageNum == null || pageNum.equals("1")) {
            pager = searchUser(keyWord,1);
        } else {
            pager = searchUser(keyWord,Integer.parseInt(pageNum));
        }
        request.setAttribute("pager", pager);
        request.setAttribute("username",keyWord);
        request.getRequestDispatcher("admin/admin_usersearch.jsp").forward(request, response);
    }

    public void prepareUserActivity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("username");
        if (StringUtil.isArgumentsContainNull(userName)) return;
        List<LoginLog> loginLogList = getLoginLog(userName);
        List<CartItem> cartRemovedList = getAddedButRemovedItems(userName);
        List<CartItem> cartItems = getCartItems(userName);
        List<BookTransaction> bookTransactions = getBookTransactions(userName);
        User userInfo = getUser(userName);
        request.setAttribute("LOGIN_LOG", loginLogList);
        request.setAttribute("CART_REMOVED", cartRemovedList);
        request.setAttribute("CART_ITEMS", cartItems);
        request.setAttribute("TRANSACTIONS", bookTransactions);
        request.setAttribute("USER_INFO", userInfo);
        request.getRequestDispatcher("admin/admin_single_user.jsp").forward(request, response);
    }

    public void banAUser(HttpServletRequest request, HttpServletResponse response, boolean ban) throws ServletException, IOException {
        String userName = request.getParameter("username");
        if (StringUtil.isArgumentsContainNull(userName)) return;
        AdminDao adminDao = new AdminDaoImpl();
        if (!adminDao.banOrReleaseUser(userName, ban)) {
        }
        List<LoginLog> loginLogList = getLoginLog(userName);
        List<CartItem> cartRemovedList = getAddedButRemovedItems(userName);
        List<CartItem> cartItems = getCartItems(userName);
        List<BookTransaction> bookTransactions = getBookTransactions(userName);
        User userInfo = getUser(userName);
        request.setAttribute("LOGIN_LOG", loginLogList);
        request.setAttribute("CART_REMOVED", cartRemovedList);
        request.setAttribute("CART_ITEMS", cartItems);
        request.setAttribute("TRANSACTIONS", bookTransactions);
        request.setAttribute("USER_INFO", userInfo);
        request.getRequestDispatcher("admin/admin_single_user.jsp").forward(request, response);

    }

    public void getUnSoldBookList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageNum = request.getParameter("book_page");
        BookDao bookDao = new BookDaoImpl();
        Pager<Book> bookPager = null;
        if(pageNum == null || pageNum.equals("1")){
            bookPager = bookDao.getAllUnSoldBooks(1);
        }else{
            bookPager = bookDao.getAllUnSoldBooks(Integer.parseInt(pageNum));
        }
        request.setAttribute("pager",bookPager);
        request.getRequestDispatcher("admin/admin_booklist.jsp").forward(request,response);
    }


    public void deleteBooksByID(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String [] bookIDs = request.getParameterValues("is_remove");
        BookDao bookDao = new BookDaoImpl();
        for(String bookID : bookIDs){
            bookDao.deleteBookByID(bookID);
        }
        request.setAttribute("book_page","1");
        getUnSoldBookList(request,response);
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


    private Pager<User> searchUser(String keyWord,int page) {
        UserDao userListDao = new UserDaoImpl();
        return userListDao.searchUser(keyWord,page);
    }

    public boolean adminVerification(String userName, String password) {
        AdminDao adminDao = new AdminDaoImpl();
        return adminDao.adminLogin(userName, password);
    }


}

