package service;

import bean.CartItem;
import bean.LoginLog;
import bean.User;
import daoImpl.AdminDaoImpl;
import daoImpl.CartDaoImpl;
import daoImpl.UserDaoImpl;
import daoIterface.AdminDao;
import daoIterface.CartDao;
import daoIterface.UserDao;

import java.util.List;

/**
 * Created by Linus on 10/09/2016.
 */
public class AdminService {

    public boolean adminLogin(String userName, String password) {
        AdminDao adminDao = new AdminDaoImpl();
        return adminDao.adminLogin(userName, password);
    }

    public List<User> getAllUser() {
        UserDao userDao = new UserDaoImpl();
        List<User> userList = userDao.getAllUser();
        if (userList == null || userList.size() == 0) return null;
        return userList;
    }

    public List<User> searchUser(String keyWord) {
        UserDao userDao = new UserDaoImpl();
        List<User> userList = userDao.searchUserByName(keyWord);
        if (userList == null || userList.size() == 0) return null;
        return userList;
    }

    public List<LoginLog> getLoginLog(String userName) {
        UserDao userDao = new UserDaoImpl();
        List<LoginLog> loginLogList = userDao.getLoginLog(userName);
        if (loginLogList == null || loginLogList.size() == 0) return null;
        return loginLogList;
    }

    public List<CartItem> getAddedButRemovedItems(String userName){
        CartDao cartDao = new CartDaoImpl();
        List<CartItem> cartItemList = cartDao.getAddedThenRemovedItemsInCart(userName);
        if (cartItemList == null || cartItemList.size() == 0) return null;
        return cartItemList;
    }

    public List<CartItem> getCartItems(String userName){
        CartDao cartDao = new CartDaoImpl();
        List<CartItem> cartItemList = cartDao.getUserCartItems(userName);
        if (cartItemList == null || cartItemList.size() == 0) return null;
        return cartItemList;
    }


}
