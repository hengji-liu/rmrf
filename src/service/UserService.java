package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import bean.User;
import daoImpl.UserDaoImpl;
import daoIterface.UserDao;
import util.EmailUtil;
import util.EncryptionUtil;

public class UserService {
	// para
	private static final String USERNAME = "username";
	private static final String PS = "password";
	private static final String FIRSTNAME = "firstname";
	private static final String LASTNAME = "lastname";
	private static final String EMAIL = "email";
	private static final String BIRTHDAY = "birthday";
	private static final String ADDRESS = "address";
	private static final String CREDITCARD = "creditcard";
	// attri
	private static final String USER = "user";
	private static final String LOGIN_FAILURE = "loginFailure";
	// pages
	private static final String LOGIN_PAGE = "user/login.jsp";
	private static final String SEARCH_PAGE = "book/search.jsp";
	private static final String REGISTER_PAGE = "user/register.jsp";
	private static final String FAILURE_PAGE = "user/failure.jsp";
	private static final String CONFIRM_PAGE = "user/confirm.jsp";
	private static final String PROFILE_PAGE = "user/profile.jsp";

	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDao dao = new UserDaoImpl();
		String username = request.getParameter(USERNAME);
		String password = request.getParameter(PS);
		User u = dao.getUserWhenLogin(username, EncryptionUtil.encryptPassword(password, username));
		if (null == u) {
			request.setAttribute(LOGIN_FAILURE, LOGIN_FAILURE);
			request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
		} else {
			request.getSession().setAttribute(USER, u);
			request.getRequestDispatcher(SEARCH_PAGE).forward(request, response);
		}
	}

	public void register(HttpServletRequest request, HttpServletResponse response, List<FileItem> list)
			throws ServletException, IOException {
		UserDao dao = new UserDaoImpl();
		User u = new User();
		for (FileItem item : list) {
			if (item.isFormField()) {
				String name = item.getFieldName();
				String value = item.getString();
				switch (name) {
				case USERNAME:
					u.setUsername(value);
					break;
				case PS:
					u.setPs(EncryptionUtil.encryptPassword(value, u.getUsername()));
					break;
				case FIRSTNAME:
					u.setFirstname(value);
					break;
				case LASTNAME:
					u.setLastname(value);
					break;
				case EMAIL:
					u.setEmail(value);
					break;
				case BIRTHDAY:
					u.setBirthday(value);
					break;
				case ADDRESS:
					u.setAddress(value);
					break;
				case CREDITCARD:
					u.setCreditcard(value);
					break;
				default:
					break;
				}
			} else {
				String savePath = request.getSession().getServletContext().getRealPath("/profileImg");
				String filename = item.getName();
				if (filename == null || filename.trim().equals("")) {
					makeDefaultImg(new File(savePath + File.separator + "default.jpg"),
							new File(savePath + File.separator + u.getUsername() + "_profile"));
					continue;
				}
				String fileName = u.getUsername() + "_profile";
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
		u.setType_(0);
		int newRowId = dao.save(u);
		if (0 == newRowId) {
			request.getRequestDispatcher(FAILURE_PAGE).forward(request, response);
		} else {
			request.getRequestDispatcher(CONFIRM_PAGE).forward(request, response);
			// TODO:send email in a new thread
			EmailUtil.sendEmail("bblib registration confirmation email",
					"http://localhost:8080/rmrf/c?reqtype=confirm&username=" + u.getUsername(), u.getEmail());
		}
	}

	public void cofirm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter(USERNAME);
		UserDao dao = new UserDaoImpl();
		dao.confirm(username);
		User u = dao.getUserWhenConfirm(username);
		if (null == u) {
			request.getRequestDispatcher(FAILURE_PAGE).forward(request, response);
		} else {
			request.getSession().setAttribute(USER, u);
			request.getRequestDispatcher(SEARCH_PAGE).forward(request, response);
		}
	}

	public void update(HttpServletRequest request, HttpServletResponse response, List<FileItem> list)
			throws ServletException, IOException {
		UserDao dao = new UserDaoImpl();
		User u = new User();
		for (FileItem item : list) {
			if (item.isFormField()) {
				String name = item.getFieldName();
				String value = item.getString();
				switch (name) {
				case USERNAME:
					u.setUsername(value);
					break;
				case PS:
					if ("".equals(value)) {
						User user = (User) request.getSession().getAttribute(USER);
						u.setPs(user.getPs());
					} else {
						u.setPs(EncryptionUtil.encryptPassword(value, u.getUsername()));
					}
					break;
				case FIRSTNAME:
					u.setFirstname(value);
					break;
				case LASTNAME:
					u.setLastname(value);
					break;
				case EMAIL:
					u.setEmail(value);
					break;
				case BIRTHDAY:
					u.setBirthday(value);
					break;
				case ADDRESS:
					u.setAddress(value);
					break;
				case CREDITCARD:
					u.setCreditcard(value);
					break;
				default:
					break;
				}
			} else {
				String savePath = request.getSession().getServletContext().getRealPath("/profileImg");
				String filename = item.getName();
				if (filename == null || filename.trim().equals("")) {
					continue;
				}
				String fileName = u.getUsername() + "_profile";
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
		u.setType_(1);
		int newRowId = dao.update(u);
		if (0 == newRowId) {
			request.getRequestDispatcher(FAILURE_PAGE).forward(request, response);
		} else {
			request.getSession().setAttribute(USER, u);
			request.getRequestDispatcher(PROFILE_PAGE).forward(request, response);
		}
	}

	private void makeDefaultImg(File source, File target) {
		FileChannel in = null;
		FileChannel out = null;
		FileInputStream inStream = null;
		FileOutputStream outStream = null;
		try {
			inStream = new FileInputStream(source);
			outStream = new FileOutputStream(target);
			in = inStream.getChannel();
			out = outStream.getChannel();
			in.transferTo(0, in.size(), out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inStream.close();
				in.close();
				outStream.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
