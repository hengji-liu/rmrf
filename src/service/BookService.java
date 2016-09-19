package service;

import bean.Book;
import daoImpl.AdminDaoImpl;
import daoImpl.BookDaoImpl;
import daoIterface.AdminDao;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        int from = 0;
        if (! request.getParameter("from").equals("")) {
            from = Integer.parseInt(request.getParameter("from"));
        }
        session.setAttribute("from_BookSearch", request.getParameter("from"));
        int to = 9999;
        if (! request.getParameter("to").equals("")) {
            to = Integer.parseInt(request.getParameter("to"));
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
            request.getRequestDispatcher("/bookSearching.jsp").forward(request, response);
        } else if (doWhat.equals("-")) {
            if (itemNum > 1) session.setAttribute("itemNum_BookSearch", new Integer(itemNum - 1));
            request.getRequestDispatcher("/bookSearching.jsp").forward(request, response);
        } else if (doWhat.equals("Reset")) {
            session.setAttribute("itemNum_BookSearch", null);
            session.setAttribute("from_BookSearch", null);
            session.setAttribute("to_BookSearch", null);
            request.getRequestDispatcher("/bookSearching.jsp").forward(request, response);
        } else if (doWhat.equals("Search")){

        }
    }
}
