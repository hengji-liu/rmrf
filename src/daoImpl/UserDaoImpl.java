package daoImpl;

import bean.LoginLog;
import bean.User;
import daoIterface.UserDao;
import sun.rmi.runtime.Log;
import util.DBHelper;
import util.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linus on 10/09/2016.
 */
public class UserDaoImpl implements UserDao{

    @Override
    public List<User> getAllUser() {
        List<User> users=new ArrayList<>();
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "select username,firstname,lastname,email,birthday,address from user where type_='1';";
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getString("username"),rs.getString("firstname"),
                        rs.getString("lastname"),rs.getString("email"),rs.getString("address"));
                user.setBirthday(DateUtil.getDateToDay(rs.getString("birthday")));
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DBHelper.realease(rs,psmt);
        }
    }

    @Override
    public List<User> searchUserByName(String keyWord) {
        List<User> users=new ArrayList<>();
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "SELECT username,firstname,lastname,email,birthday,address FROM user WHERE type_='1' AND " +
                    "(username LIKE ? OR firstname LIKE ? OR lastname LIKE ?);";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1,"%"+keyWord+"%");
            psmt.setString(2,"%"+keyWord+"%");
            psmt.setString(3,"%"+keyWord+"%");
            rs = psmt.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getString("username"),rs.getString("firstname"),
                        rs.getString("lastname"),rs.getString("email"),rs.getString("address"));
                user.setBirthday(DateUtil.getDateToDay(rs.getString("birthday")));
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DBHelper.realease(rs,psmt);
        }

    }

    @Override
    public User getUserByUserName(String userName) {
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "select username,firstname,lastname,email,birthday,address from user where username=? and type_='1';";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1,userName);
            rs = psmt.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString("username"),rs.getString("firstname"),
                        rs.getString("lastname"),rs.getString("email"),rs.getString("address"));
                user.setBirthday(DateUtil.getDateToDay(rs.getString("birthday")));
                return user;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DBHelper.realease(rs,psmt);
        }
    }

    @Override
    public List<LoginLog> getLoginLog(String userName) {
        List<LoginLog> loginLogList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "select * from `user_login` where username=? order by time desc";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1,userName);
            rs = psmt.executeQuery();
            while (rs.next()) {
                LoginLog loginLog = new LoginLog();
                loginLog.setUserByName(userName);
                loginLog.setTime(rs.getString("time"));
                if (rs.getString("granted").equals("0")){
                    loginLog.setGranted(false);
                }else{
                    loginLog.setGranted(true);
                }
                loginLogList.add(loginLog);
            }
            return loginLogList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DBHelper.realease(rs,psmt);
        }

    }

    /**
     * Img is a bit large, get Image in Separate function
     * @param userName
     * @return
     */
    @Override
    public byte[] getUserImg(String userName) {
        return new byte[0];
    }


}
