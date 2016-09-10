package bean;

/**
 * Created by Linus on 10/09/2016.
 */
public class CartItem {
    private User intentional_buyer;
    private Book book;
    private String addedTime;
    private String removeTime;

    public User getIntentional_buyer() {
        return intentional_buyer;
    }

    public void setIntentional_buyer(User intentional_buyer) {
        this.intentional_buyer = intentional_buyer;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }

    public String getRemoveTime() {
        return removeTime;
    }

    public void setRemoveTime(String removeTime) {
        this.removeTime = removeTime;
    }
}
