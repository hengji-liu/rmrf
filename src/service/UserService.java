package service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import daoImpl.UserDaoImpl;
import daoIterface.UserDao;

public class UserService {
	// para
	private static final String USERNAME = "username";
	private static final String PS = "password";
	// attri
	private static final String USER = "user";
	private static final String LOGIN_FAILURE = "loginFailure";
	// pages
	private static final String LOGIN_PAGE = "user/login.jsp";
	private static final String SEARCH_PAGE = "search/search.jsp";

	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDao dao = new UserDaoImpl();
		String username = request.getParameter(USERNAME);
		String password = request.getParameter(PS);
		User u = dao.getUserWhenLogin(username, password);
		if (null == u) {
			request.setAttribute(LOGIN_FAILURE, LOGIN_FAILURE);
			request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
		} else {
			request.setAttribute(USER, u);
			request.getRequestDispatcher(SEARCH_PAGE).forward(request, response);
		}

	}
}
