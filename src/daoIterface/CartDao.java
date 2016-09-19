package daoIterface;

import bean.CartItem;

import java.util.List;

/**
 * Created by Linus on 11/09/2016.
 */
public interface CartDao {

    /**
     * Get items has been added to Cart
     * @param userName
     * @return null if query error, else a List<CartItem> is returned
     */
    public List<CartItem> getUserCartItems(String userName);

    public List<CartItem> getAddedThenRemovedItemsInCart(String userName);

    /**
     *
     * @param userName
     * @param bookID
     * @return 1 ok, 2 duplicate add, 0 fail
     */
    int addCartItem(String userName, String bookID);

    //isPurchase : whether it is a purchase, if it is a purchase, record will not going to log_cart
    boolean removeFromCart(String userName, String bookID, boolean isPurchase);

    //TODO: declare and implement more method if needed
}
