package service;

import bean.*;
import daoImpl.*;
import daoIterface.*;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GraphService {


    public void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = (String) request.getParameter("keyword");
        String searchType = (String) request.getParameter("searchType");
        String size  =  (String) request.getParameter("size");
        response.getOutputStream().println(keyword+searchType+size);
    }
}
