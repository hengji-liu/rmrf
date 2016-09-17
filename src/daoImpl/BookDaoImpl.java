package daoImpl;

import Config.ServiceConfig;
import bean.Book;
import bean.LoginLog;
import bean.Pager;
import bean.User;
import daoIterface.BookDao;
import daoIterface.RecordCountDao;
import util.DBHelper;
import util.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linus on 10/09/2016.
 */
public class BookDaoImpl implements BookDao{

    @Override
    public List<Book> searchBookByName(String bookName) {
        return null;
    }

    @Override
    public Book getBookById(String bookID) {
        return null;
    }

    @Override
    public List<Book> getRelatedBooks(String bookID) {
        return null;
    }

    @Override
    public Pager<Book> getAllUnSoldBooks(int pageNum) {
        //DO COUNT
        String queryCountSql = "from book " +
                "where book.book_id not in (select book_id from transactions)";
        int totalCount = new RecordCountDao().recordCount("(*)",queryCountSql);
        //DO qUERY
        List<Book> bookList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "select * from book " +
                    "where book.book_id not in (select book_id from transactions);";
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setBookID(rs.getString("book_id"));
                book.setSellerID(rs.getString("seller"));
                book.setType(rs.getString("book_type"));
                book.setAuthors(rs.getString("authors"));
                book.setEditors(rs.getString("editors"));
                book.setTitle(rs.getString("title"));
                book.setYear(rs.getString("year"));
                book.setVenue(rs.getString("venue"));
                book.setPublisher(rs.getString("publisher"));
                book.setIsbn(rs.getString("isbn"));
                book.setTag(rs.getString("tag"));
                book.setPaused(rs.getString("paused"));
                book.setPrice(rs.getInt("price"));
                bookList.add(book);
            }
            return new Pager<>(ServiceConfig.USER_PAGE_LIMIT, pageNum,totalCount,bookList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DBHelper.realease(rs, psmt);
        }

    }
}
