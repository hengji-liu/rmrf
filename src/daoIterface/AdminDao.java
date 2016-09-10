package daoIterface;

import bean.Book;
import bean.BookTransaction;
import bean.User;
import util.EncryptionUtil;

import java.util.List;

/**
 * Created by Linus on 10/09/2016.
 */
public interface AdminDao {

    public boolean adminLogin(String userName,String password);

    /**
     * get purchased Item by userName
     * @param userName
     * @return a List<Book> object, if size is 0, no purchase
     */
    public List<BookTransaction> purchasedItem(String userName);



}
