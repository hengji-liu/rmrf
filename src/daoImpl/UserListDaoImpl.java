package daoImpl;

import Config.ServiceConfig;
import bean.Pager;
import bean.User;
import daoIterface.RecordCountDao;
import daoIterface.UserListDao;
import util.DBHelper;
import util.DateUtil;
import util.SQLReplaceUtil;

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
    public Pager<User> getUserPage(int page_num){
        //Do count
        RecordCountDao recordCountDao = new RecordCountDao();
        int totalRecords = recordCountDao.recordCount("(*)", "from user where type_='1' or type_='4';");
        //Do search
        List<User> users=new ArrayList<>();
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "select username,firstname,lastname,email,birthday,address,type_ from user where type_='1' or type_='4' LIMIT ?,?;";
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
    public Pager<User> searchUser(String keyWord, int page_num) {
        //DO COUNT
        RecordCountDao recordCountDao = new RecordCountDao();
        String queryCount = "FROM user WHERE (type_='1' or type_='4') AND "
                + "(username LIKE ? OR firstname LIKE ? OR lastname LIKE ?)";
        String str = "\'%"+keyWord+"%\'";
        queryCount = SQLReplaceUtil.replaceQuestionMarks(queryCount,str,str,str);

        int totalRecords = recordCountDao.recordCount("(*)",queryCount);
        //DoSearch
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
