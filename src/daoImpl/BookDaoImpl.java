package daoImpl;

import bean.Book;
import daoIterface.BookDao;

import java.util.List;

/**
 * Created by Linus on 10/09/2016.
 */
public class BookDaoImpl implements BookDao{

    @Override
    public List<Book> searchBookByName(String bookName) {
        return null;
    }

    @Override
    public Book getBookById(String bookID) {
        return null;
    }

    @Override
    public List<Book> getRelatedBooks(String bookID) {
        return null;
    }
}
