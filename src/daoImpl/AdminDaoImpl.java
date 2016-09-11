package daoImpl;

import bean.Book;
import bean.BookTransaction;
import bean.CartItem;
import bean.User;
import daoIterface.AdminDao;
import util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            DBHelper.closeConn(conn,rs,psmt);
        }

    }

    @Override
    public List<BookTransaction> purchasedItems(String userName) {
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
            DBHelper.closeConn(conn,rs,psmt);
        }
    }

    @Override
    public List<CartItem> getAddedThenRemovedItemsInCart(String userName) {
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        List<CartItem> cartItems = new ArrayList<>();
        try {
            conn = DBHelper.getConnection();
            String sql = "select book.title,book.book_id, log_cart.time_added, log_cart.time_removed \n" +
                    "from book, log_cart where username =? \n" +
                    "and book.book_id = log_cart.book_id";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1,userName);
            rs = psmt.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setTitle(rs.getString("title"));
                book.setBookID(rs.getString("book_id"));
                String time_added = rs.getString("time_added");
                String time_removed = rs.getString("time_removed");
                CartItem cartItem = new CartItem();
                cartItem.setBook(book);
                cartItem.setAddedTime(time_added);
                cartItem.setRemoveTime(time_removed);
                cartItems.add(cartItem);
            }
            return cartItems;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DBHelper.closeConn(conn,rs,psmt);
        }
    }

    @Override
    public boolean banOrReleaseUser(String userName, boolean ban) {
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            conn = DBHelper.getConnection();
            String sql = "UPDATE `user` SET `user`.type_=? where username=?";
            psmt = conn.prepareStatement(sql);
            if(ban){
                psmt.setString(1,"4");
            }else {
                psmt.setString(1,"1");
            }
            psmt.setString(2,userName);
            psmt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBHelper.closeConn(conn,rs,psmt);
        }

    }





}
