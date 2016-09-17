package daoImpl;

import Config.ServiceConfig;
import bean.Pager;
import bean.User;
import daoIterface.UserListDao;
import util.DBHelper;
import util.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linus on 16/09/2016.
 */
public class UserListDaoImpl implements UserListDao{


    @Override
    public int getTotalUserCount() {
        int count = 0;
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConnection();
            String countQuery = "select count(*) from user where type_='1' or type_='4'";
            psmt = conn.prepareStatement(countQuery);
            rs = psmt.executeQuery();
            if(rs.next()){
                count = Integer.parseInt(rs.getString("count(*)"));
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return count;
        } finally {
            DBHelper.realease(rs,psmt);
        }
    }

    @Override
    public Pager<User> getUserPage(int page_num){

        int totalRecords = getTotalUserCount();
        List<User> users=new ArrayList<>();
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "select username,firstname,lastname,email,birthday,address,type_  from user where type_='1' or type_='4' LIMIT ?,?;";
            psmt = conn.prepareStatement(sql);
            int offset = (page_num-1)*ServiceConfig.USER_PAGE_LIMIT;
            psmt.setInt(1,offset);//offset
            psmt.setInt(2,ServiceConfig.USER_PAGE_LIMIT);//end
            rs = psmt.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getString("username"), rs.getString("firstname"),
                        rs.getString("lastname"), rs.getString("email"), rs.getString("address"), Integer.parseInt(rs.getString("type_")));
                user.setBirthday(DateUtil.getDateToDay(rs.getString("birthday")));
                users.add(user);
            }
            return new Pager<>(ServiceConfig.USER_PAGE_LIMIT, page_num,totalRecords, users);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DBHelper.realease(rs,psmt);
        }
    }

    @Override
    public int getTotalUserSearchCount(String keyWord) {
        int count = 0;
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "SELECT count(*) FROM user WHERE (type_='1' or type_='4') AND "
                    + "(username LIKE ? OR firstname LIKE ? OR lastname LIKE ?);";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, "%" + keyWord + "%");
            psmt.setString(2, "%" + keyWord + "%");
            psmt.setString(3, "%" + keyWord + "%");
            rs = psmt.executeQuery();
            if(rs.next()){
                count = Integer.parseInt(rs.getString("count(*)"));
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            DBHelper.realease(rs, psmt);
        }
    }

    @Override
    public Pager<User> searchUser(String keyWord, int page_num) {
        int totalRecords = getTotalUserSearchCount(keyWord);
        List<User> users = new ArrayList<>();
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "SELECT * FROM user WHERE (type_='1' or type_='4') AND "
                    + "(username LIKE ? OR firstname LIKE ? OR lastname LIKE ?) LIMIT ?,?;";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, "%" + keyWord + "%");
            psmt.setString(2, "%" + keyWord + "%");
            psmt.setString(3, "%" + keyWord + "%");
            int offset = (page_num-1)*ServiceConfig.USER_PAGE_LIMIT;
            psmt.setInt(4,offset);
            psmt.setInt(5,offset+ServiceConfig.USER_PAGE_LIMIT);
            rs = psmt.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getString("username"), rs.getString("firstname"), rs.getString("lastname"),
                        rs.getString("email"), rs.getString("address"));
                user.setBirthday(DateUtil.getDateToDay(rs.getString("birthday")));
                users.add(user);
            }
            return new Pager<>(ServiceConfig.USER_PAGE_LIMIT, page_num,totalRecords, users);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DBHelper.realease(rs, psmt);
        }

    }


}
