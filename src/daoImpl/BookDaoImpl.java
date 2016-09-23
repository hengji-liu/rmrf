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

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Linus on 10/09/2016.
 */
public class BookDaoImpl implements BookDao {

    @Override
    public List<Book> searchBookByName(String bookName) {
        return null;
    }

    @Override
    public Book getBookById(String bookID) {
        Book book = null;
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "select * from book where book.book_id=?;";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, bookID);
            rs = psmt.executeQuery();
            if (rs.next()) {
                book = getBookDetail(rs);
            }
            return book;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DBHelper.realease(rs, psmt);
        }
    }


    @Override
    public Pager<Book> getAllUnSoldBooks(int pageNum) {
        //DO COUNT
        String queryCountSql = "from book " +
                "where book.book_id not in (select book_id from transactions)";
        int totalCount = new RecordCountDao().recordCount("(*)", queryCountSql);
        //DO qUERY
        List<Book> bookList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "select * from book where book.book_id not in (select book_id from transactions) LIMIT ?,?;";
            psmt = conn.prepareStatement(sql);
            int offset = (pageNum - 1) * ServiceConfig.USER_PAGE_LIMIT;
            psmt.setInt(1, offset);
            psmt.setInt(2, ServiceConfig.USER_PAGE_LIMIT);
            rs = psmt.executeQuery();
            while (rs.next()) {
                Book book = getBookDetail(rs);
                bookList.add(book);
            }
            return new Pager<>(ServiceConfig.USER_PAGE_LIMIT, pageNum, totalCount, bookList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DBHelper.realease(rs, psmt);
        }

    }

    @Override
    public boolean deleteBookByID(String bookID) {
        Connection conn = null;
        PreparedStatement psmt = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "DELETE FROM bblib.book WHERE book_id=?;";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, bookID);
            psmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBHelper.realease(null, psmt);
        }
    }

    @Override
    public List<Book> searchBooks(int itemNum, List<String> whereList, List<String> whatList, List<String> howList, String from, String to) {
        Connection conn = null;
        try {
            conn = DBHelper.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sql = "select * from book where (";
        for (int i = 0; i < itemNum; i++) {
            if (i != itemNum - 1) {
                if (whatList.get(i).equals("")) continue;
                sql += whereList.get(i) + " LIKE '%" + whatList.get(i).toLowerCase() + "%' " + howList.get(i) + " ";
            } else {
                if (!whatList.get(i).equals("")) {
                    sql += whereList.get(i) + " LIKE '%" + whatList.get(i).toLowerCase() + "%'";
                } else {
                    sql += "book_id > 0";
                }
            }
        }
        sql += ") AND year >= " + from + " AND year <= " + to + " AND paused = 0";
        //System.out.println(sql);
        List<Book> bookList = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Book book = getBookDetail(rs);
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    private Book getBookDetail(ResultSet rs) throws SQLException {
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
        book.setVisited(rs.getString("visited"));
        book.setPhotoid(rs.getString("photoid"));
        return book;
    }

    @Override
    public void increaseVisited(String bookID) {
        Connection conn = null;
        PreparedStatement psmt = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "UPDATE bblib.book SET visited=visited+1 WHERE book_id=?;";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, bookID);
            psmt.executeUpdate();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        } finally {
            DBHelper.realease(null, psmt);
        }
    }

    @Override

    public int getTotalBook() {
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "SELECT COUNT(*) as COUNT FROM book";
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            DBHelper.realease(rs, psmt);
        }
    }

    @Override
    public Map<String, Integer> getCategoryCount() {
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Map<String, Integer> map = new HashMap<>();
        try {
            conn = DBHelper.getConnection();
            String sql = "SELECT book_type,COUNT(*) as count FROM book GROUP BY book_type;";
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            while (rs.next()) {
                map.put(rs.getString(1), rs.getInt(2));
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DBHelper.realease(rs, psmt);
        }
    }


    public int countTotalPrice() {
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "SELECT SUM(price) as price FROM book";
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("price");
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            DBHelper.realease(rs, psmt);
        }

    }

    public List<Book> top10 () {
        Connection conn = null;
        try {
            conn = DBHelper.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sql = "SELECT * FROM bblib.book order by visited desc limit 10 offset 0";
        List<Book> bookList = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Book book = getBookDetail(rs);
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    @Override
    public Pager<Book> searchBooks ( int itemNum, List<
            String > whereList, List < String > whatList, List < String > howList, String from, String to,int pageNum){
        Connection conn = null;
        try {
            conn = DBHelper.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sql = "select * from book where (";
        for (int i = 0; i < itemNum; i++) {
            if (i != itemNum - 1) {
                if (whatList.get(i).equals("")) continue;
                sql += whereList.get(i) + " LIKE '%" + whatList.get(i).toLowerCase() + "%' " + howList.get(i) + " ";
            } else {
                if (!whatList.get(i).equals("")) {
                    sql += whereList.get(i) + " LIKE '%" + whatList.get(i).toLowerCase() + "%'";
                } else {
                    sql += "book_id > 0";
                }
            }
        }
        sql += ") AND year >= " + from + " AND year <= " + to + " AND paused = 0 ORDER BY visited DESC ";
        sql += "LIMIT " + ServiceConfig.USER_PAGE_LIMIT + " OFFSET ";
        String pagerSql = sql;
        sql += (pageNum - 1) * ServiceConfig.USER_PAGE_LIMIT;
        //System.out.println(sql);
        List<Book> bookList = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Book book = getBookDetail(rs);
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int totalRecord = (new BookDaoImpl().searchBooks(itemNum, whereList, whatList, howList, from, to)).size();
        //System.out.println(totalRecord);
        Pager<Book> pager = new Pager<>(ServiceConfig.USER_PAGE_LIMIT, pageNum, totalRecord, bookList);
        pager.setSql(pagerSql);
        return pager;
    }

    @Override
    public Pager<Book> searchBooks (Pager pager,int pageNum){
        Connection conn = null;
        try {
            conn = DBHelper.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sql = pager.getSql() + (pageNum - 1) * ServiceConfig.USER_PAGE_LIMIT;
        List<Book> bookList = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Book book = getBookDetail(rs);
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pager.setCurrentPage(pageNum);
        pager.setDataList(bookList);
        return pager;

    }

}