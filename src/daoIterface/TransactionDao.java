package daoIterface;

import bean.BookTransaction;

import java.util.List;

/**
 * Created by Linus on 19/09/2016.
 */
public interface TransactionDao {

    //get a user's purchased items
    public List<BookTransaction> purchasedItems(String userName);

    public boolean userPurchase(String sellerID, String buyerID, String bookID);

}
