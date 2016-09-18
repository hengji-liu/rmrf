package util;

import bean.Book;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mingxuan on 18/09/2016.
 */
public class XML2DB {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = DBHelper.getConnection();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        XML2DB passer = new XML2DB();
        List bookList = passer.listBook();
        for (Object book :  bookList)
            add2DB((Book) book, conn);

        try {
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("ALL DONE");
    }


    private List listBook() {
        Document document = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(this.getClass().getClassLoader().getResourceAsStream("dblp.xml"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        List bookList = new ArrayList();
        String allTypes = "articleinproceedingsproceedingsbookincollectionphdthesismastersthesiswww";
        Node rootNode = document.getDocumentElement();
        NodeList rootNodeChildrenList = rootNode.getChildNodes();
        for (int i = 0; i < rootNodeChildrenList.getLength(); i++) {
            Node rootNodeChild = rootNodeChildrenList.item(i);
            if (allTypes.indexOf(rootNodeChild.getNodeName()) > -1) {
                String type = rootNodeChild.getNodeName();
                String authors = "";
                String editors = "";
                String title = "";
                String year = "";
                String venue = "";
                String publisher = "";
                String isbn = "";

                NodeList childElementsList = rootNodeChild.getChildNodes();
                for (int j = 0; j < childElementsList.getLength(); j++) {
                    Node element = childElementsList.item(j);
                    String elementName = element.getNodeName();
                    String elementValue = element.getTextContent();
                    if (elementName.equals("author")) {
                        authors += elementValue + ", ";
                    } else if (elementName.equals("editor")) {
                        editors += elementValue + ", ";
                    } else if (elementName.equals("title")) {
                        title += elementValue + ", ";
                        if(title.indexOf("CAiSE") > -1) venue = "CAiSE";
                        else if (title.indexOf("SIGMOD") > -1) venue = "SIGMOD";
                    } else if (elementName.equals("year")) {
                        year += elementValue + ", ";
                    } else if (elementName.equals("publisher")) {
                        publisher += elementValue + ", ";
                    } else if (elementName.equals("isbn")) {
                        isbn += elementValue + ", ";
                    }
                }
                if(! authors.equals("")) authors = authors.substring(0, authors.length()-3);
                if(! editors.equals("")) editors = editors.substring(0, editors.length()-3);
                if(! title.equals("")) title = title.substring(0, title.length()-3);
                if(! year.equals("")) year = year.substring(0, 4);
                if(! publisher.equals("")) publisher = publisher.substring(0, publisher.length()-3);
                if(! isbn.equals("")) isbn = isbn.substring(0, isbn.length()-3);

                Book book = new Book();
                book.setType(type);
                book.setAuthors(authors);
                book.setEditors(editors);
                book.setTitle(title);
                book.setYear(year);
                book.setVenue(venue);
                book.setPublisher(publisher);
                book.setIsbn(isbn);
                bookList.add(book);
            }
        }
        return bookList;
    }

    @SuppressWarnings("deprecation")
    private static void add2DB(Book book, Connection conn) {
        String sql = "insert into book (book_type, authors, editors, title, year, venue, publisher, isbn, paused) values(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, book.getType());
            pstmt.setString(2, book.getAuthors());
            pstmt.setString(3, book.getEditors());
            pstmt.setString(4, book.getTitle());
            if (! book.getYear().equals(""))
                pstmt.setDate(5, new java.sql.Date(Integer.valueOf(book.getYear()), 1, 1));
            else
                pstmt.setDate(5, null);
            pstmt.setString(6, book.getVenue());
            pstmt.setString(7, book.getPublisher());
            pstmt.setString(8, book.getIsbn());
            pstmt.setInt(9, 0);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
