package daoIterface;

import bean.Book;
import bean.BookTransaction;
import bean.CartItem;
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
    public List<BookTransaction> purchasedItems(String userName);

    /**
     * Ban or unban a user from system
     * @param userName
     * @param ban true if it is a ban, false if it is a release from ban
     * @return
     */
    public boolean banOrReleaseUser(String userName, boolean ban);



}
