package daoImpl;

import bean.Book;
import bean.BookTransaction;
import bean.User;
import daoIterface.AdminDao;
import util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            String sql = "select id from user where username=? and ps=? and type_='2';";
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

    @Override
    public List<BookTransaction> purchasedItem(String userName) {
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        List<BookTransaction> bookTransactions = new ArrayList<>();
        try {
            conn = DBHelper.getConnection();
            String sql = "select user.username, user.firstname, user.lastname,book.title,book.price,transaction.time\n" +
                    "from user,book,transaction \n" +
                    "where transaction.seller = (select seller from transaction where buyer=?)\n" +
                    "and transaction.seller=user.username and transaction.book_id = book.book_id";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1,userName);
            rs = psmt.executeQuery();
            while (rs.next()) {
                User seller = new User();
                seller.setUsername(rs.getString("user"));
                seller.setFirstname(rs.getString("firstname"));
                seller.setLastname(rs.getString("lastname"));
                Book book = new Book();
                book.setTitle(rs.getString("title"));
                book.setPrice(Integer.parseInt(rs.getString("price")));
                String time = rs.getString("time");
                BookTransaction bookTransaction = new BookTransaction();
                bookTransaction.setBook(book);
                bookTransaction.setSeller(seller);
                bookTransaction.setTime(time);
                bookTransactions.add(bookTransaction);
            }
            return bookTransactions;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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


}
