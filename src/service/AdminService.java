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
        if (userList.size() == 0) return null;
        return userList;
    }

    public List<User> searchUser(String keyWord){
        UserDao userDao = new UserDaoImpl();
        List<User> userList = userDao.searchUserByName(keyWord);
        if (userList.size() == 0) return null;
        return userList;
    }

}
