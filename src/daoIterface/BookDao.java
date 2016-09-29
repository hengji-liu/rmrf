package daoIterface;

import java.util.List;
import java.util.Map;

import bean.Book;
import bean.Pager;

/**
 * Created by Linus on 10/09/2016.
 */
public interface BookDao {
	/* Search book by name */
	public List<Book> searchBookByName(String bookName);

	public Book getBookById(String bookID);

	public Pager<Book> getAllUnSoldBooks(int pageNum);

	public boolean deleteBookByID(String bookID);

	List<Book> searchBooks(int itemNum, List<String> whereList, List<String> whatList, List<String> howList,
			String from, String to);

	public void increaseVisited(String bookID);

	public int getTotalBook();

	public Map<String, Integer> getCategoryCount();

	public int countTotalPrice();

	List<Book> top10();

	Pager<Book> searchBooks(int itemNum, List<String> whereList, List<String> whatList, List<String> howList,
			String from, String to, int pageNum);

	Pager<Book> searchBooks(Pager pager, int pageNum);

	int save(Book b);

	void updatePhotoId(String s);

	List<Book> getPausedBooksByUsername(String username);

	List<Book> getSellingBooksByUsername(String username);

	void makePaused(String bookid);

	void makeSelling(String bookid);

	/* TODO: Please define and implemzent more advanced book search! */

}
