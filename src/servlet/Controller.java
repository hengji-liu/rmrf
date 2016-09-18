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
import service.UserService;

/**
 * Created by Linus on 10/09/2016.
 */
@WebServlet("/BookTrade")
public class Controller extends HttpServlet {

	private static final String REQTYPE = "reqtype";
	private static final String USER_LOGIN = "user_login";
	private static final String USER_REGISTER = "user_register";
	private static final String CONFIRM = "confirm";

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
		if (!ServletFileUpload.isMultipartContent(request)) {// no file upload
			String requestType = request.getParameter(REQTYPE);
			if(requestType == null){
                response.sendRedirect("welcome.jsp");
                return;
            }
			AdminService adminService = null;
			UserService userService = null;
			switch (requestType) {
			case CONFIRM:
				userService = new UserService();
				userService.cofirm(request, response);
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
				adminService.getUnSoldBookList(request,response);
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
					UserService userService = new UserService();
					userService.register(request, response, list);
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
