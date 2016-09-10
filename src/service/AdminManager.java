package service;

import daoImpl.AdminDaoImpl;
import daoIterface.AdminDao;

/**
 * Created by Linus on 10/09/2016.
 */
public class AdminManager {

    public boolean adminLogin(String userName,String password){
        AdminDao adminDao = new AdminDaoImpl();
        return adminDao.adminLogin(userName,password);
    }
}
