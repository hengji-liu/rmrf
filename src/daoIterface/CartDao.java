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

    public boolean addCartItem(CartItem cartItem);

    public boolean remove(CartItem cartItem);

    //TODO: declare and implement more method if needed
}
