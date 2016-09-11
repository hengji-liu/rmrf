package daoImpl;

import bean.Book;
import bean.CartItem;
import daoIterface.CartDao;
import util.DBHelper;
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
            String sql = "select * from cart where username=?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1,userName);
            rs = psmt.executeQuery();
            while (rs.next()) {
                CartItem cartItem = new CartItem();
                //book info too large, only retrive ID here!
                Book book = new Book();
                book.setBookID(rs.getString("book_id"));
                cartItem.setAddedTime(rs.getString("time_addded"));
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
