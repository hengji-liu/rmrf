package daoIterface;

import bean.BookTransaction;

import java.util.List;

/**
 * Created by Linus on 19/09/2016.
 */
public interface TransactionDao {

    public List<BookTransaction> purchasedItems(String userName);

}
