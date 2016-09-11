package daoImpl;

import bean.User;
import daoIterface.UserDao;
import util.DBHelper;
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
                        rs.getString("lastname"),rs.getString("email"),rs.getString("birthday"),rs.getString("address"));
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DBHelper.closeConn(conn,rs,psmt);
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
                        rs.getString("lastname"),rs.getString("email"),rs.getString("birthday"),rs.getString("address"));
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DBHelper.closeConn(conn,rs,psmt);
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
            rs = psmt.executeQuery();
            psmt.setString(1,userName);
            if (rs.next()) {
                User user = new User(rs.getString("username"),rs.getString("firstname"),
                        rs.getString("lastname"),rs.getString("email"),rs.getString("birthday"),rs.getString("address"));
                return user;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DBHelper.closeConn(conn,rs,psmt);
        }
    }


}
