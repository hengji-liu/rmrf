package bean;

import java.io.Serializable;

/**
 * Created by Linus on 10/09/2016.
 */
public class BookTransaction implements Serializable{

    private User seller;
    private User buyer;
    private Book book;
    private String time;

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
