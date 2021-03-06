package service;

import bean.Book;
import bean.User;
import daoImpl.BookDaoImpl;
import daoImpl.CartDaoImpl;
import daoImpl.TransactionDaoImpl;
import daoImpl.UserDaoImpl;
import daoIterface.BookDao;
import daoIterface.CartDao;
import daoIterface.TransactionDao;
import daoIterface.UserDao;
import util.EmailUtil;
import util.EmailUtilII;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Linus on 19/09/2016.
 */
public class TransactionService {

    public void purchaseItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //FROM Cart page
        String bookID = request.getParameter("book_id");
        User user = (User)request.getSession().getAttribute("user");
        String userName = user.getUsername();
        Book book = purchase(userName,bookID);
        request.setAttribute("book",book);
        //Foward to transaction successful page
        request.getRequestDispatcher("cart/purchase.jsp").forward(request,response);
    }

    //3 dabase calls
    private Book purchase(String buyer,String bookID){
        BookDao bookDao = new BookDaoImpl();
        Book book =  bookDao.getBookById(bookID); //book
        TransactionDao transactionDao = new TransactionDaoImpl();
        transactionDao.userPurchase(book.getSellerID(),buyer,bookID);
        //Email
        UserDao userDao = new UserDaoImpl();
        User seller =  userDao.getUserByUserName(book.getSellerID());
        String subject = subjectAssemble(seller);
        String content = contentAssemble(book);
        //Multi Thread Send, tested works

        //new Thread(new SendEmailRunnable(subject,content,seller.getEmail())).start();
        CartDao cartDao = new CartDaoImpl();
        cartDao.removeFromCart(buyer,bookID,true);
//      EmailUtil.sendEmail(subject,content,seller.getEmail());
        return book;
    }

    private class SendEmailRunnable implements Runnable{
        private String subject;
        private String content;
        private String address;

        SendEmailRunnable(String subject,String contnet,String address){
            this.subject = subject;
            this.content = contnet;
            this.address = address;
        }
        @Override
        public void run() {
            EmailUtilII emailUtil = new EmailUtilII();
            emailUtil.sendEmail(subject,content,address);
        }
    }

    private String subjectAssemble(User seller){
        String title_pre = "Hi "+seller.getFirstname()+" "+seller.getLastname()+",";
        String title_post = "you have a book SOLD!\n";
        return title_pre+title_post;
    }

    private String contentAssemble(Book book){
        return String.format("%s: %s \n %s: %s \n","Book Title",
                book.getTitle(),"You Earned ", book.getPrice()+"$!");
    }



}
