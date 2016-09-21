package service;

import bean.CartItem;
import daoImpl.AdminDaoImpl;
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
        String userName = request.getParameter("user_name");
        String bookID = request.getParameter("book_id");
        if(StringUtil.isArgumentsContainNull(userName,bookID)) return;
        CartDao cartDao = new CartDaoImpl();
        int status_code = cartDao.addCartItem(userName,bookID);
        if(status_code == 1){
            //Suceess, forward to cart page
            //request.getRequestDispatcher().forward(request,response);
        }else if(status_code == 2){
            //Duplicate to cart page, forward to cart page
            //request.getRequestDispatcher().forward(request,response);
        }

        //request.getRequestDispatcher().forward(request,response);

    }

    public void removeItemsFromCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //From item cart page
        String userName = request.getParameter("user_name");
        String bookID = request.getParameter("book_id");
        if(StringUtil.isArgumentsContainNull(userName,bookID)) return;
        CartDao cartDao = new CartDaoImpl();
        cartDao.removeFromCart(userName,bookID,false);
        //Forward to cart page
        //request.getRequestDispatcher().forward(request,response);
    }

    public void showCartItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //From (userprofile page? or user main page?)and(cart page )
        String userName = request.getParameter("username");
        if (StringUtil.isArgumentsContainNull(userName)) return;
        CartDao cartDao = new CartDaoImpl();
        List<CartItem> cartItemList = cartDao.getUserCartItems(userName);
        request.setAttribute("CART_ITEMS", cartItemList);
        //To cart page
        request.getRequestDispatcher("cart/cart.jsp").forward(request,response);
    }
}
