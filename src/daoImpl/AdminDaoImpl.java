package daoImpl;

import daoIterface.AdminDao;
import util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Linus on 10/09/2016.
 */
public class AdminDaoImpl implements AdminDao{

    @Override
    public boolean adminLogin(String userName, String password) {
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "select id from user where username=? and ps=? and identity='2';";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1,userName);
            psmt.setString(2,password);
            rs = psmt.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeCon(conn,rs,psmt);
        }

    }


    public void closeCon(Connection con,ResultSet rs, PreparedStatement stmt){
        if(con != null){
            try {
                con.close();
                con = null;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
                stmt = null;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }


    public static void main(String [] args){
        AdminDao dao = new AdminDaoImpl();
        System.out.println(dao.adminLogin("xx","123"));
    }
}
