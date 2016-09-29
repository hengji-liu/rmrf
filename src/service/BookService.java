package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;

import bean.Book;
import bean.Pager;
import bean.User;
import daoImpl.BookDaoImpl;
import daoIterface.BookDao;
import util.EncryptionUtil;
import util.StringUtil;

/**
 * Created by Linus on 19/09/2016.
 */
public class BookService {

	public void showBook(HttpServletRequest request, HttpServletResponse response, boolean isAdmin)
			throws ServletException, IOException {
		// FROM admin book detail/ search result /
		String bookID = request.getParameter("book_id");
		if (StringUtil.isArgumentsContainNull(bookID))
			return;
		BookDao bookDao = new BookDaoImpl();
		Book book = bookDao.getBookById(bookID);
		request.getSession().setAttribute("book", book);
		if (isAdmin) {
			request.setAttribute("readonly", "true");
		} else {
			// Increase visited count by one
			bookDao.increaseVisited(bookID);
		}
		// to book detail page
		request.getRequestDispatcher("/book/bookdetail.jsp").forward(request, response);
	}

	public void top10(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("display_BookSearch", "top10");
		Long time = System.currentTimeMillis();
		List<Book> bookSearchResultList = new BookDaoImpl().top10();
		session.setAttribute("time_BookSearch", ((double) (System.currentTimeMillis() - time)) / 1000);
		session.setAttribute("top10_BookSearch", bookSearchResultList);
		request.getRequestDispatcher("/book/search.jsp").forward(request, response);
	}

	public void searchBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Boolean validInput = true;
		HttpSession session = request.getSession();
		int itemNum = (int) session.getAttribute("itemNum_BookSearch");
		List<String> whereList = new ArrayList<>();
		List<Boolean> validInputList = new ArrayList<>();
		List<String> whatList = new ArrayList<>();
		List<String> howList = new ArrayList<>();
		for (int i = 1; i <= itemNum; i++) {
			whereList.add(request.getParameter("where" + String.valueOf(i)).trim());
			String what = request.getParameter("what" + String.valueOf(i)).trim();
			whatList.add(what);
			if (what.indexOf("--") > -1)
				validInput = false;
			validInputList.add(what.indexOf("--") == -1);
			howList.add(request.getParameter("how" + String.valueOf(i)).trim());
		}
		session.setAttribute("whereList_BookSearch", whereList);
		session.setAttribute("validInput_BookSearch", validInputList);
		session.setAttribute("whatList_BookSearch", whatList);
		session.setAttribute("howList_BookSearch", howList);
		String from = "0";
		if (!request.getParameter("from").equals("")) {
			from = request.getParameter("from").trim();
		}
		session.setAttribute("from_BookSearch", request.getParameter("from"));
		String to = "9999";
		if (!request.getParameter("to").equals("")) {
			to = request.getParameter("to").trim();
		}
		session.setAttribute("to_BookSearch", request.getParameter("to"));
		String doWhat = request.getParameter("doWhat_BookSearch");
		if (doWhat.equals("+")) {
			whereList.add("title");
			session.setAttribute("whereList_BookSearch", whereList);
			validInputList.add(true);
			session.setAttribute("validInput_BookSearch", validInputList);
			whatList.add("");
			session.setAttribute("whatList_BookSearch", whatList);
			howList.add("and");
			session.setAttribute("howList_BookSearch", howList);
			session.setAttribute("itemNum_BookSearch", new Integer(itemNum + 1));
		} else if (doWhat.equals("-")) {
			if (itemNum > 1)
				session.setAttribute("itemNum_BookSearch", new Integer(itemNum - 1));
		} else if (doWhat.equals("Reset")) {
			session.setAttribute("itemNum_BookSearch", null);
			session.setAttribute("from_BookSearch", null);
			session.setAttribute("to_BookSearch", null);
		} else if (doWhat.equals("Search")) {
			if (validInput) {
				Long time = System.currentTimeMillis();
				Pager<Book> bookPager = new BookDaoImpl().searchBooks(itemNum, whereList, whatList, howList, from, to,
						1);
				session.setAttribute("time_BookSearch", ((double) (System.currentTimeMillis() - time)) / 1000);
				if (bookPager.getDataList().size() == 0) {
					session.setAttribute("display_BookSearch", "noResults");
				} else {
					session.setAttribute("display_BookSearch", "showResults");
					session.setAttribute("pager_BookSearch", bookPager);
					session.setAttribute("top10_BookSearch", bookPager.getDataList());
				}
			}
		}
		request.getRequestDispatcher("/book/search.jsp").forward(request, response);
	}

	public void page(HttpServletRequest request, HttpServletResponse response, String btn, String num)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		int pageNum = 1;
		if (btn.equals("+")) {
			pageNum = ((Pager) session.getAttribute("pager_BookSearch")).getCurrentPage() + 1;
		} else if (btn.equals("-")) {
			pageNum = ((Pager) session.getAttribute("pager_BookSearch")).getCurrentPage() - 1;
		} else {
			try {
				pageNum = Integer.parseInt(num);
			} catch (Exception e) {
				request.getRequestDispatcher("/book/search.jsp").forward(request, response);
			}
		}
		Pager<Book> bookPager = (Pager<Book>) session.getAttribute("pager_BookSearch");
		Long time = System.currentTimeMillis();
		bookPager = new BookDaoImpl().searchBooks(bookPager, pageNum);
		session.setAttribute("time_BookSearch", ((double) (System.currentTimeMillis() - time)) / 1000);
		session.setAttribute("pager_BookSearch", bookPager);
		session.setAttribute("top10_BookSearch", bookPager.getDataList());
		request.getRequestDispatcher("/book/search.jsp").forward(request, response);
	}

	public int save(HttpServletRequest request, HttpServletResponse response, List<FileItem> list)
			throws ServletException, IOException {
		BookDao dao = new BookDaoImpl();
		Book b = new Book();
		String savePath = request.getSession().getServletContext().getRealPath("/img");
		for (FileItem item : list) {
			if (item.isFormField()) {
				String name = item.getFieldName();
				String value = item.getString();
				switch (name) {
				case "type":
					b.setType(value);
					break;
				case "authors":
					b.setAuthors(value);
					break;
				case "editors":
					b.setEditors(value);
					break;
				case "title":
					b.setTitle(value);
					break;
				case "year":
					b.setYear(value);
					break;
				case "venue":
					b.setVenue(value);
					break;
				case "publisher":
					b.setPublisher(value);
					break;
				case "isbn":
					b.setIsbn(value);
					break;
				case "price":
					b.setPrice(Integer.parseInt(value));
				default:
					break;
				}
			} else {
				String filename = item.getName();
				if (filename == null || filename.trim().equals("")) {
					continue;
				}
				Random random = new Random();
				b.setPhotoid(EncryptionUtil.encryptPassword(b.getTitle(), String.valueOf(random.nextFloat())));
				String fileName = b.getPhotoid();
				InputStream in = item.getInputStream();
				FileOutputStream out = new FileOutputStream(savePath + File.separator + fileName);
				byte buffer[] = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				in.close();
				out.close();
				item.delete();
			}
		}
		User u = (User) request.getSession().getAttribute("user");
		b.setSellerID(u.getUsername());
		int newRowId = dao.save(b);
		if (0 == newRowId) {
			return 0;
		} else {
			if (null != b.getPhotoid()) {
				File f = new File(savePath + File.separator + b.getPhotoid());
				f.renameTo(new File(savePath + File.separator + "book" + newRowId));
				dao.updatePhotoId(String.valueOf(newRowId));
			}
			return 1;
		}

	}

	public void paused(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BookDao dao = new BookDaoImpl();
		User u = (User) request.getSession().getAttribute("user");
		List<Book> list = dao.getPausedBooksByUsername(u.getUsername());
		request.setAttribute("list", list);
		request.getRequestDispatcher("book/paused.jsp").forward(request, response);
	}

	public void selling(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BookDao dao = new BookDaoImpl();
		User u = (User) request.getSession().getAttribute("user");
		List<Book> list = dao.getSellingBooksByUsername(u.getUsername());
		request.setAttribute("list", list);
		request.getRequestDispatcher("book/selling.jsp").forward(request, response);
	}

	public void makeSelling(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BookDao dao = new BookDaoImpl();
		String bookid = (String) request.getParameter("bookid");
		dao.makeSelling(bookid);
		request.getRequestDispatcher("c?reqtype=goto_paused").forward(request, response);
	}

	public void makePaused(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BookDao dao = new BookDaoImpl();
		String bookid = (String) request.getParameter("bookid");
		System.out.println(bookid);
		dao.makePaused(bookid);
		request.getRequestDispatcher("c?reqtype=goto_selling").forward(request, response);
	}

}
