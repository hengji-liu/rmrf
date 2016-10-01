package service;

import bean.Book;
import bean.CartItem;
import bean.User;
import daoImpl.AdminDaoImpl;
import daoImpl.BookDaoImpl;
import daoImpl.CartDaoImpl;
import daoIterface.AdminDao;
import daoIterface.CartDao;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CartService {

    static String CART_PAGE = null;

    public void addItemsToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //From item view page
        String userName = ((User)request.getSession().getAttribute("user")).getUsername();
        String bookID = request.getParameter("book_id");
        if(StringUtil.isArgumentsContainNull(userName,bookID)) return;
        CartDao cartDao = new CartDaoImpl();
        int status_code = cartDao.addCartItem(userName,bookID);
        if(status_code == 1){
            //Suceess, forward to cart page
            showCartItem(request,response);
        }else if(status_code == 2){
            //Duplicate to cart page, forward to cart page
            request.setAttribute("dup","true");
            Book book = new BookDaoImpl().getBookById(bookID);
            request.setAttribute("book",book);
            request.getRequestDispatcher("book/bookdetail.jsp").forward(request,response);
        }

        //request.getRequestDispatcher().forward(request,response);

    }

    public void removeItemsFromCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //From item cart page
        String userName = ((User)request.getSession().getAttribute("user")).getUsername();
        String bookID = request.getParameter("book_id");
        if(StringUtil.isArgumentsContainNull(userName,bookID)) return;
        CartDao cartDao = new CartDaoImpl();
        cartDao.removeFromCart(userName,bookID,false);
        showCartItem(request,response);
    }

    public void showCartItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //From (userprofile page? or user main page?)and(cart page )
        String userName = ((User)request.getSession().getAttribute("user")).getUsername();
        if (StringUtil.isArgumentsContainNull(userName)) return;
        CartDao cartDao = new CartDaoImpl();
        List<CartItem> cartItemList = cartDao.getUserCartItems(userName);
        request.setAttribute("CART_ITEMS", cartItemList);
        //To cart page
        request.getRequestDispatcher("cart/cart.jsp").forward(request,response);
    }
}
