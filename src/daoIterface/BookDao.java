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

    public Pager<Book> getAllUnSoldBooks(int pageNum);

    public boolean deleteBookByID(String bookID);

    List<Book> searchBooks(int itemNum, List<String> whereList, List<String> whatList, List<String> howList, String from, String to);

    public void increaseVisited(String bookID);

    /*TODO: Please define and implement more advanced book search!*/

}
