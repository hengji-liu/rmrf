package service;

import bean.Book;
import daoImpl.AdminDaoImpl;
import daoImpl.BookDaoImpl;
import daoIterface.AdminDao;
import util.DBHelper;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linus on 19/09/2016.
 */
public class BookService {

    public void showBook(HttpServletRequest request, HttpServletResponse response,boolean isAdmin) throws ServletException, IOException {
        //FROM admin book detail/ search result /
        String bookID = request.getParameter("book_id");
        if (StringUtil.isArgumentsContainNull(bookID)) return;
        Book book = new BookDaoImpl().getBookById(bookID);
        request.setAttribute("book",book);
        if(isAdmin){
            request.setAttribute("readonly","true");
        }
        //to book detail page
        request.getRequestDispatcher("/book/bookdetail.jsp").forward(request,response);
    }

    public void searchBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int itemNum = (int) session.getAttribute("itemNum_BookSearch");
        List<String> whereList = new ArrayList<>();
        List<String> whatList = new ArrayList<>();
        List<String> howList = new ArrayList<>();
        for (int i = 1; i <= itemNum; i ++) {
            whereList.add(request.getParameter("where" + String.valueOf(i)));
            whatList.add(request.getParameter("what" + String.valueOf(i)));
            howList.add(request.getParameter("how" + String.valueOf(i)));
        }
        session.setAttribute("whereList_BookSearch", whereList);
        session.setAttribute("whatList_BookSearch", whatList);
        session.setAttribute("howList_BookSearch", howList);
        String from = "0";
        if (! request.getParameter("from").equals("")) {
            from = request.getParameter("from");
        }
        session.setAttribute("from_BookSearch", request.getParameter("from"));
        String to = "9999";
        if (! request.getParameter("to").equals("")) {
            to = request.getParameter("to");
        }
        session.setAttribute("to_BookSearch", request.getParameter("to"));
        String doWhat = request.getParameter("doWhat_BookSearch");
        if (doWhat.equals("+")) {
            whereList.add("title");
            session.setAttribute("whereList_BookSearch", whereList);
            whatList.add("");
            session.setAttribute("whatList_BookSearch", whatList);
            howList.add("and");
            session.setAttribute("howList_BookSearch", howList);
            session.setAttribute("itemNum_BookSearch", new Integer(itemNum + 1));
            request.getRequestDispatcher("/book/search.jsp").forward(request, response);
        } else if (doWhat.equals("-")) {
            if (itemNum > 1) session.setAttribute("itemNum_BookSearch", new Integer(itemNum - 1));
            request.getRequestDispatcher("/book/search.jsp").forward(request, response);
        } else if (doWhat.equals("Reset")) {
            session.setAttribute("itemNum_BookSearch", null);
            session.setAttribute("from_BookSearch", null);
            session.setAttribute("to_BookSearch", null);
            request.getRequestDispatcher("/book/search.jsp").forward(request, response);
        } else if (doWhat.equals("Search")){
            Connection conn = null;
            try {
                conn = DBHelper.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String sql = "select book_id, seller, book_type, authors, editors, " +
                    "title, year, venue, publisher, isbn, tag, paused, price, visited " +
                    "from book where (";
            for (int i = 0; i < itemNum; i ++) {
                if (i != itemNum - 1) {
                    if (whatList.get(i).equals("")) continue;
                    sql += whereList.get(i) + " LIKE '%" + whatList.get(i) + "%' " + howList.get(i) + " ";
                } else {
                    if (! whatList.get(i).equals("")) {
                        sql += whereList.get(i) + " LIKE '%" + whatList.get(i) + "%'";
                    } else {
                        sql += "book_id > 0";
                    }
                }
            }
            sql += ") AND year >= " + from + " AND year <= " + to + " AND paused = 0";
            System.out.println(sql);
            List<Book> bookList = new ArrayList<>();
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    String book_id = rs.getString("book_id");
                    String seller = rs.getString("seller");
                    String book_type = rs.getString("book_type");
                    String authors = rs.getString("authors");
                    String editors = rs.getString("editors");
                    String title = rs.getString("title");
                    String year = rs.getString("year");
                    String venue = rs.getString("venue");
                    String publisher = rs.getString("publisher");
                    String isbn = rs.getString("isbn");
                    String tag = rs.getString("tag");
                    String paused = rs.getString("paused");
                    String price = rs.getString("price");
                    String visited = rs.getString("visited");
                    Book book = new Book();
                    book.setBookID(book_id);
                    book.setSellerID(seller);
                    book.setType(book_type);
                    book.setAuthors(authors);
                    book.setEditors(editors);
                    book.setTitle(title);
                    book.setYear(year);
                    book.setVenue(venue);
                    book.setPublisher(publisher);
                    book.setIsbn(isbn);
                    book.setTag(tag);
                    book.setPaused(paused);
                    if (price != null && !price.equals("")) book.setPrice(Integer.parseInt(price));
                    book.setVisited(visited);
                    bookList.add(book);
                }
                for (Book book : bookList) {
                    System.out.println(book.getAuthors());
                    System.out.println(book.getTitle());
                    System.out.println(book.getYear());
                }
                System.out.println(bookList.size());

            } catch (SQLException e) {
                e.printStackTrace();
            }
            request.getRequestDispatcher("/book/search.jsp").forward(request, response);
        }
    }
}
