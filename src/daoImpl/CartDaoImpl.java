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
            String sql = "select book.title,book.book_id, cart.time_addded from book, cart\n" +
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
                cartItem.setAddedTime(DateUtil.getDateToMin(rs.getString("time_addded")));
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
    public boolean addCartItem(CartItem cartItem) {
        //TODO: add cart Item to database
        return false;
    }

    @Override
    public boolean remove(CartItem cartItem) {
        //TODO: remove Item from database
        return false;
    }


}
