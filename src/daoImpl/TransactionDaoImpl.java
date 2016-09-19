package daoImpl;

import bean.Book;
import bean.BookTransaction;
import bean.User;
import daoIterface.TransactionDao;
import util.DBHelper;
import util.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linus on 19/09/2016.
 */
public class TransactionDaoImpl implements TransactionDao{

    @Override
    public List<BookTransaction> purchasedItems(String userName) {
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        List<BookTransaction> bookTransactions = new ArrayList<>();
        try {
            conn = DBHelper.getConnection();
            String sql = "select user.username, user.firstname, user.lastname,book.title,book.price,transactions.time\n" +
                    "from user,book,transactions \n" +
                    "where transactions.seller IN (select seller from transactions where buyer=?)\n" +
                    "and transactions.seller=user.username and transactions.book_id = book.book_id";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1,userName);
            rs = psmt.executeQuery();
            while (rs.next()) {
                User seller = new User();
                seller.setFirstname(rs.getString("firstname"));
                seller.setLastname(rs.getString("lastname"));
                seller.setUsername(rs.getString("username"));
                Book book = new Book();
                book.setTitle(rs.getString("title"));
                book.setPrice(Integer.parseInt(rs.getString("price")));
                String time = DateUtil.getDateToDay(rs.getString("time"));
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
            DBHelper.realease(rs,psmt);
        }
    }

    @Override
    public boolean userPurchase(String sellerID, String buyerID, String bookID) {
        Connection conn = null;
        PreparedStatement psmt = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "INSERT INTO `transactions` (seller, buyer, book_id, time) " +
                    "VALUE (?,?,?,?)";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1,sellerID);
            psmt.setString(2,buyerID);
            psmt.setString(3,bookID);
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
}
