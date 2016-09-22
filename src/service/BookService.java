package service;

import bean.Book;
import bean.Pager;
import daoImpl.AdminDaoImpl;
import daoImpl.BookDaoImpl;
import daoIterface.AdminDao;
import daoIterface.BookDao;
import util.DBHelper;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
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
        BookDao bookDao = new BookDaoImpl();
        Book book = bookDao.getBookById(bookID);
        request.getSession().setAttribute("book",book);
        if(isAdmin){
            request.setAttribute("readonly","true");
        }else{
            //Increase visited count by one
            bookDao.increaseVisited(bookID);
        }
        //to book detail page
        request.getRequestDispatcher("/book/bookdetail.jsp").forward(request,response);
    }

    public void top10(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("display_BookSearch", "top10");
        Long time = System.currentTimeMillis();
        List<Book> bookSearchResultList = new BookDaoImpl().top10();


        session.setAttribute("time_BookSearch", ((double)(System.currentTimeMillis() - time)) / 1000);
        session.setAttribute("top10_BookSearch", bookSearchResultList);
        request.getRequestDispatcher("/book/search.jsp").forward(request, response);
    }

    public void searchBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Boolean validInput = true;
        HttpSession session = request.getSession();
        int itemNum = (int) session.getAttribute("itemNum_BookSearch");
        List<String> whereList = new ArrayList<>();
        List<Boolean> validInputList = new ArrayList<>();
        List<String> whatList = new ArrayList<>();
        List<String> howList = new ArrayList<>();
        for (int i = 1; i <= itemNum; i ++) {
            whereList.add(request.getParameter("where" + String.valueOf(i)));
            String what = request.getParameter("what" + String.valueOf(i));
            whatList.add(what);
            if (what.indexOf("--") > -1) validInput = false;
            validInputList.add(what.indexOf("--") == -1);
            howList.add(request.getParameter("how" + String.valueOf(i)));
        }
        session.setAttribute("whereList_BookSearch", whereList);
        session.setAttribute("validInput_BookSearch", validInputList);
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
            validInputList.add(true);
            session.setAttribute("validInput_BookSearch", validInputList);
            whatList.add("");
            session.setAttribute("whatList_BookSearch", whatList);
            howList.add("and");
            session.setAttribute("howList_BookSearch", howList);
            session.setAttribute("itemNum_BookSearch", new Integer(itemNum + 1));
        } else if (doWhat.equals("-")) {
            if (itemNum > 1) session.setAttribute("itemNum_BookSearch", new Integer(itemNum - 1));
        } else if (doWhat.equals("Reset")) {
            session.setAttribute("itemNum_BookSearch", null);
            session.setAttribute("from_BookSearch", null);
            session.setAttribute("to_BookSearch", null);
        } else if (doWhat.equals("Search")){
            if (validInput) {
                Long time = System.currentTimeMillis();
                List<Book> bookSearchResultList = new BookDaoImpl().searchBooks(itemNum, whereList, whatList, howList, from, to);

                session.setAttribute("time_BookSearch", ((double)(System.currentTimeMillis() - time)) / 1000);
                if (bookSearchResultList.size() == 0) {
                    session.setAttribute("display_BookSearch", "noResults");
                } else {
                    session.setAttribute("display_BookSearch", "showResults");
                }
            }
        }
        request.getRequestDispatcher("/book/search.jsp").forward(request, response);
    }
}
