package daoIterface;

import bean.Book;
import bean.Pager;

import java.util.List;

/**
 * Created by Linus on 10/09/2016.
 */
public interface BookDao {
    /* Search book by name */
    public List<Book> searchBookByName(String bookName);

    public Book getBookById(String bookID);

    /**
     * When provided a bookID, work out some related items
     * @param bookID
     * @return
     */
    public List<Book> getRelatedBooks(String bookID);

    public Pager<Book> getAllUnSoldBooks(int pageNum);

    public boolean deleteBookByID(String bookID);

    /*TODO: Please define and implement more advanced book search!*/

}
