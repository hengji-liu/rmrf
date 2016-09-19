package service;

import bean.Book;
import daoImpl.AdminDaoImpl;
import daoImpl.BookDaoImpl;
import daoIterface.AdminDao;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Linus on 19/09/2016.
 */
public class BookService {

    public void showBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //FROM admin book detail/ search result /
        String bookID = request.getParameter("book_id");
        if (StringUtil.isArgumentsContainNull(bookID)) return;
        Book book = new BookDaoImpl().getBookById(bookID);
        request.setAttribute("book",book);
        //to book detail page
        //request.getRequestDispatcher().forward(request,response);

    }

    public void searchBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
