package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import service.AdminService;
import service.BookService;
import service.CartService;
import service.GraphService;
import service.TransactionService;
import service.UserService;

/**
 * Created by Linus on 10/09/2016.
 */
@WebServlet("/BookTrade")
public class Controller extends HttpServlet {
	// control token
	private static final String REQTYPE = "reqtype";
	private static final String USER_LOGIN = "user_login";
	private static final String USER_REGISTER = "user_register";
	private static final String CONFIRM = "confirm";
	private static final String PROFILE_CHANGE = "profile_change";
	private static final String LOGOUT = "logout";
	private static final String UPLOAD = "upload";
	private static final String PAUSED = "changeto_paused";
	private static final String SELLING = "changeto_selling";

	// goto
	private static final String GOTO_LOGIN = "goto_login";
	private static final String GOTO_GRAPH = "goto_graph";
	private static final String GOTO_SEARCH = "goto_search";
	private static final String GOTO_UPLOAD = "goto_upload";
	private static final String GOTO_PAUSED = "goto_paused";
	private static final String GOTO_SELLING = "goto_selling";
	// page
	private static final String HOMEPAGE = "welcome.jsp";
	private static final String LOGIN_PAGE = "user/login.jsp";
	private static final String GRAPH_PAGE = "graph/graphSandR.jsp";
	private static final String SEARCH_PAGE = "book/search.jsp";
	private static final String UPLOAD_PAGE = "book/upload.jsp";
	// attribute
	private static final String USER = "user";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		resolvedCommand(request, response);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		resolvedCommand(request, response);
	}

	private void resolvedCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminService adminService = null;
		UserService userService = null;
		BookService bookService = null;
		CartService cartService = null;
		GraphService graphService = null;
		TransactionService transactionService = null;
		if (!ServletFileUpload.isMultipartContent(request)) {// no file upload
			String requestType = request.getParameter(REQTYPE);
			if (requestType == null) {
				response.sendRedirect("welcome.jsp");
				return;
			}
			switch (requestType) {
			case CONFIRM:
				userService = new UserService();
				userService.cofirm(request, response);
				break;
			case USER_LOGIN:
				userService = new UserService();
				userService.login(request, response);
				break;
			case "ADMIN_LOGIN":
				adminService = new AdminService();
				adminService.adminLogin(request, response);
				break;
			case "USER_SEARCH":
				adminService = new AdminService();
				adminService.searchUsers(request, response);
				break;
			case "BAN_USER":
				adminService = new AdminService();
				adminService.banAUser(request, response, true);
				break;
			case "USER_LOG":
				adminService = new AdminService();
				adminService.prepareUserActivity(request, response);
				break;
			case "UNBAN_USER":
				adminService = new AdminService();
				adminService.banAUser(request, response, false);
				break;
			case "USER_LIST":
				adminService = new AdminService();
				adminService.userList(request, response);
				break;
			case "BOOK_LIST_ALL":
				adminService = new AdminService();
				adminService.getUnSoldBookList(request, response);
				break;
			case "REMOVE_BOOK":
				adminService = new AdminService();
				adminService.deleteBooksByID(request, response);
				break;
			case "SEARCH_BOOK":
				bookService = new BookService();
				bookService.searchBook(request, response);
				break;
			case "BOOK_DETAIL":
				bookService = new BookService();
				bookService.showBook(request, response, false);
				break;
			case "BOOK_DETAIL_READ":
				bookService = new BookService();
				bookService.showBook(request, response, true);
				break;
			case "CART_ITEM":
				cartService = new CartService();
				cartService.showCartItem(request, response);
				break;
			case "CART_REMOVE":
				cartService = new CartService();
				cartService.removeItemsFromCart(request, response);
				break;
			case "CART_ADD":
				cartService = new CartService();
				cartService.addItemsToCart(request, response);
				break;
			case "BOOK_BUY":
				transactionService = new TransactionService();
				transactionService.purchaseItem(request, response);
				break;
			case "TOP10_BOOK":
				bookService = new BookService();
				bookService.top10(request, response);
				break;
			case "PAGE_BOOK":
				bookService = new BookService();
				bookService.page(request, response, request.getParameter("btn"), request.getParameter("num"));
				break;
			case "GRAPH_SEARCH":
				graphService = new GraphService();
				graphService.search(request, response);
				break;
			case "ADMIN_OVERALL":
				adminService = new AdminService();
				adminService.openOverall(request, response);
				break;
			case LOGOUT:
				request.getSession().removeAttribute(USER);
				response.sendRedirect(HOMEPAGE);
				break;
			case GOTO_GRAPH:
				request.getRequestDispatcher(GRAPH_PAGE).forward(request, response);
				break;
			case GOTO_SEARCH:
				request.getRequestDispatcher(SEARCH_PAGE).forward(request, response);
				break;
			case GOTO_LOGIN:
				if (null != request.getSession().getAttribute(USER)) {
					request.getRequestDispatcher(SEARCH_PAGE).forward(request, response);
				} else {
					request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
				}
				break;
			case GOTO_UPLOAD:
				request.getRequestDispatcher(UPLOAD_PAGE).forward(request, response);
				break;
			case GOTO_PAUSED:
				bookService = new BookService();
				bookService.paused(request, response);
				break;
			case GOTO_SELLING:
				bookService = new BookService();
				bookService.selling(request, response);
				break;
			case PAUSED:
				bookService = new BookService();
				bookService.makePaused(request, response);
				break;
			case SELLING:
				bookService = new BookService();
				bookService.makeSelling(request, response);
				break;
			}
		} else {// contains file upload
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> list;
			try {
				list = upload.parseRequest(request);
				String requestType = "";
				for (FileItem item : list) {// find reqtype
					String name = item.getFieldName();
					String value = item.getString();
					if (REQTYPE.equals(name)) {
						requestType = value;
						break;
					}
				}
				switch (requestType) {
				case USER_REGISTER:
					userService = new UserService();
					userService.register(request, response, list);
					break;
				case PROFILE_CHANGE:
					userService = new UserService();
					userService.update(request, response, list);
					break;
				case UPLOAD:
					bookService = new BookService();
					int result = bookService.save(request, response, list);
					if (0 == result) {
						request.getRequestDispatcher("book/failure.jsp").forward(request, response);
					} else {
						request.getRequestDispatcher(SEARCH_PAGE).forward(request, response);
					}
					break;
				default:
					break;
				}

			} catch (FileUploadException e) {
				e.printStackTrace();
			}

		}

	}

}
