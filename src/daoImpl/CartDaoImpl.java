package daoImpl;

import bean.Book;
import bean.CartItem;
import daoIterface.CartDao;
import util.DBHelper;
import util.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Linus on 11/09/2016.
 */
public class CartDaoImpl implements CartDao{
    @Override
    public List<CartItem> getUserCartItems(String userName) {
        List<CartItem> cartItemList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "select book.title,book.book_id, cart.time_added from book, cart\n" +
                    "where book.book_id=cart.book_id and username=?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1,userName);
            rs = psmt.executeQuery();
            while (rs.next()) {
                CartItem cartItem = new CartItem();
                //book info too large, only retrive ID here!
                Book book = new Book();
                book.setBookID(rs.getString("book_id"));
                book.setTitle(rs.getString("title"));
                cartItem.setAddedTime(DateUtil.getDateToMin(rs.getString("time_added")));
                cartItem.setBook(book);
                cartItemList.add(cartItem);
            }
            return cartItemList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DBHelper.realease(rs,psmt);
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
                String time_added = DateUtil.getDateToMin(rs.getString("time_added"));
                String time_removed = DateUtil.getDateToMin(rs.getString("time_removed"));
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
            DBHelper.realease(rs,psmt);
        }
    }

    @Override
    public int addCartItem(String userName, String bookID) {
        if(exists(userName,bookID)){ //CHECK IF Already in cart // database read
            return 2;
        }
        removeLogCart(userName,bookID);// remove log cart if it is in user's log cart
        Connection conn = null;
        PreparedStatement psmt = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "INSERT into `cart` (username, book_id, time_added)\n" +
                    "VALUE (?,?,?);"; //write
            psmt = conn.prepareStatement(sql);
            psmt.setString(1,userName);
            psmt.setString(2,bookID);
            psmt.setString(3,DateUtil.getCurrentTimeToMin());
            psmt.executeUpdate();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            DBHelper.realease(null,psmt);
        }
    }

    @Override
    public boolean removeFromCart(String userName, String bookID, boolean isPurchase) {
        Connection conn = null;
        PreparedStatement psmt = null;
        if(!isPurchase){ //if it is a purchase, no need to add to log
            addLogCart(userName,bookID);
        }
        try {
            conn = DBHelper.getConnection();
            String sql = "DELETE FROM `cart` WHERE username=? AND book_id=?;";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1,userName);
            psmt.setString(2,bookID);
            psmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBHelper.realease(null,psmt);
        }
    }

    private boolean removeLogCart(String userName, String bookID) {
        Connection conn = null;
        PreparedStatement psmt = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "DELETE FROM `log_cart` WHERE username=? AND book_id=?;";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1,userName);
            psmt.setString(2,bookID);
            psmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBHelper.realease(null,psmt);
        }
    }

    //check if already exists
    private boolean exists(String userName, String bookID){
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "SELECT * FROM cart where username=? AND book_id=?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1,userName);
            psmt.setString(2,bookID);
            rs = psmt.executeQuery();
            if (rs.next()) {
              return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBHelper.realease(rs,psmt);
        }
    }

    //add removed items to log_cart
    private boolean addLogCart(String userName,String bookID){
        String addedTime = getAddedTime(userName,bookID);
        Connection conn = null;
        PreparedStatement psmt = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "INSERT into `log_cart` (username, book_id, time_added,time_removed)\n" +
                    "VALUE (?,?,?,?);";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1,userName);
            psmt.setString(2,bookID);
            psmt.setString(3,addedTime);
            psmt.setString(4,DateUtil.getCurrentTimeToMin());
            psmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBHelper.realease(null,psmt);
        }
    }

    //Get a book's addition time
    private String getAddedTime(String userName,String bookID){
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "SELECT time_added FROM cart where username=? AND book_id=?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1,userName);
            psmt.setString(2,bookID);
            rs = psmt.executeQuery();
            if (rs.next()) {
                return DateUtil.getDateToMin(rs.getString("time_added"));
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DBHelper.realease(rs,psmt);
        }
    }



}
