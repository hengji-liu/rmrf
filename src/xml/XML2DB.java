package xml;

import bean.Book;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import util.DBHelper;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Mingxuan on 18/09/2016.
 */
public class XML2DB {
    static HashMap<String, String> paper;
    static HashMap<String, String> person;
    static HashMap<String, String> venue;
    static HashSet<String> edge;
    static int paperCounter;
    static int personCounter;
    static int venueCounter;
    static int edgeCounter;

    public static void main(String[] args) {
        paper = new HashMap<>();
        person = new HashMap<>();
        venue = new HashMap<>();
        edge = new HashSet<>();
        paperCounter = 0;
        personCounter = 0;
        venueCounter = 0;
        edgeCounter = 0;

        Connection conn = null;
        try {
            conn = DBHelper.getConnection();
        } catch (Exception e) {

            e.printStackTrace();
        }

        XML2DB passer = new XML2DB();
        List bookList = passer.listBook();
        int percent = 0;
        for (int i = 0; i < bookList.size(); i++) {
            if (i > bookList.size() * percent / 100) {
                System.out.println(percent + "% DONE");
                percent += 2;
            }
            add2DB((Book) bookList.get(i), conn);
        }

        try {
            conn.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }
        System.out.println("ALL DONE");
    }


    private List listBook() {
        String path = System.getProperty("user.dir");
        path += File.separator + "web" + File.separator + "sql" + File.separator + "dblp.xml";
        //System.out.println(path);
        Document document = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(new FileInputStream(path));
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
                    String elementName = element.getNodeName().trim().toLowerCase();
                    String elementValue = element.getTextContent().trim().toLowerCase();
                    if (elementName.indexOf("author") > -1) {
                        authors += elementValue + ", ";
                    } else if (elementName.indexOf("editor") > -1) {
                        editors += elementValue + ", ";
                    } else if (elementName.indexOf("title") > -1) {
                        title = elementValue;
                        if(title.indexOf("caise") > -1) venue = "caise";
                        else if (title.indexOf("sigmod") > -1) venue = "sigmod";
                        else if (title.indexOf("vldb") > -1) venue = "vldb";
                    } else if (elementName.indexOf("year") > -1) {
                        year = elementValue;
                    } else if (elementName.indexOf("publisher") > -1) {
                        publisher = elementValue;
                    } else if (elementName.indexOf("isbn") > -1) {
                        isbn = elementValue;
                    } else if (elementName.indexOf("journal") > -1) {
                        if(elementValue.indexOf("caise") > -1) venue = "caise";
                        else if (elementValue.indexOf("sigmod") > -1) venue = "sigmod";
                        else if (elementValue.indexOf("vldb") > -1) venue = "vldb";
                    } else if (elementName.indexOf("series") > -1) {
                        if(elementValue.indexOf("caise") > -1) venue = "caise";
                        else if (elementValue.indexOf("sigmod") > -1) venue = "sigmod";
                        else if (elementValue.indexOf("vldb") > -1) venue = "vldb";
                    }
                }
                if (! title.equals("")) {
                    if (type.indexOf("www") > -1) venue = "www";
                    if (!authors.equals("")) authors = authors.substring(0, authors.length() - 3);
                    if (!editors.equals("")) editors = editors.substring(0, editors.length() - 3);
                    Book book = new Book();
                    book.setType(type);
                    book.setAuthors(authors);
                    book.setEditors(editors);
                    book.setTitle(title);
                    book.setYear(year);
                    book.setVenue(venue);
                    book.setPublisher(publisher);
                    book.setIsbn(isbn);
                    book.setSellerID("hengji@rm.rf");
                    bookList.add(book);
                }
            }
        }
        return bookList;
    }

    @SuppressWarnings("deprecation")
    private static void add2DB(Book book, Connection conn) {
        // add to book table
        PreparedStatement pstmt;
        String sql4book = "insert into book (book_type, authors, editors, title, year, venue, publisher, isbn, paused, seller) values(?,?,?,?,?,?,?,?,?,?)";
        try {
            pstmt = conn.prepareStatement(sql4book);
            pstmt.setString(1, book.getType());
            pstmt.setString(2, book.getAuthors());
            pstmt.setString(3, book.getEditors());
            pstmt.setString(4, book.getTitle());
            pstmt.setString(5, book.getYear());
            pstmt.setString(6, book.getVenue());
            pstmt.setString(7, book.getPublisher());
            pstmt.setString(8, book.getIsbn());
            pstmt.setInt(9, 0);
            pstmt.setString(10, book.getSellerID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // add to entity_store table and graph_store table
        List<String> authorList = String2List(book.getAuthors());
        List<String> editorList = String2List(book.getEditors());
        String paperKey;
        String personKey;
        String venueKey;
        String edgeKey;
        String sql4entity = "insert into entity_store (subject, predicate, object) values (?,?,?)";
        String sql4graph = "insert into graph_store (subject, predicate, object) values (?,?,?)";

        // adding title
        if (!paper.containsKey(book.getTitle())) {
            paperCounter++;
            paperKey = "P" + String.valueOf(paperCounter);
            paper.put(book.getTitle(), paperKey);
            try {
                pstmt = conn.prepareStatement(sql4entity);
                pstmt.setString(1, paperKey);
                pstmt.setString(2, "Type");
                pstmt.setString(3, "Paper");
                pstmt.executeUpdate();
                pstmt.setString(2, "class");
                pstmt.setString(3, "entityNode");
                pstmt.executeUpdate();
                pstmt.setString(2, "Title");
                pstmt.setString(3, book.getTitle());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            paperKey = paper.get(book.getTitle());
        }

        // adding authors
        for (String author : authorList) {
            if (!person.containsKey(author)) {
                personCounter++;
                personKey = "A" + String.valueOf(personCounter);
                person.put(author, personKey);
                try {
                    pstmt = conn.prepareStatement(sql4entity);
                    pstmt.setString(1, personKey);
                    pstmt.setString(2, "Type");
                    pstmt.setString(3, "Author");
                    pstmt.executeUpdate();
                    pstmt.setString(2, "class");
                    pstmt.setString(3, "entityNode");
                    pstmt.executeUpdate();
                    pstmt.setString(2, "Name");
                    pstmt.setString(3, author);
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                personKey = person.get(author);
            }
            if (!edge.contains(paperKey + personKey)) {
                edgeCounter++;
                edge.add(paperKey + personKey);
                edgeKey = "E" + String.valueOf(edgeCounter);
                try {
                    pstmt = conn.prepareStatement(sql4entity);
                    pstmt.setString(1, edgeKey);
                    pstmt.setString(2, "Type");
                    pstmt.setString(3, "directedLink");
                    pstmt.executeUpdate();
                    pstmt.setString(2, "class");
                    pstmt.setString(3, "Edge");
                    pstmt.executeUpdate();
                    pstmt.setString(2, "Label");
                    pstmt.setString(3, "authoredBy");
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    pstmt = conn.prepareStatement(sql4graph);
                    pstmt.setString(1, paperKey);
                    pstmt.setString(2, edgeKey);
                    pstmt.setString(3, personKey);
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // adding editor
        for (String editor : editorList) {
            if (!person.containsKey(editor)) {
                personCounter++;
                personKey = "A" + String.valueOf(personCounter);
                person.put(editor, personKey);
                try {
                    pstmt = conn.prepareStatement(sql4entity);
                    pstmt.setString(1, personKey);
                    pstmt.setString(2, "Type");
                    pstmt.setString(3, "Author");
                    pstmt.executeUpdate();
                    pstmt.setString(2, "class");
                    pstmt.setString(3, "entityNode");
                    pstmt.executeUpdate();
                    pstmt.setString(2, "Name");
                    pstmt.setString(3, editor);
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                personKey = person.get(editor);
            }
            if (!edge.contains(paperKey + personKey)) {
                edgeCounter++;
                edge.add(paperKey + personKey);
                edgeKey = "E" + String.valueOf(edgeCounter);
                try {
                    pstmt = conn.prepareStatement(sql4entity);
                    pstmt.setString(1, edgeKey);
                    pstmt.setString(2, "type");
                    pstmt.setString(3, "directedLink");
                    pstmt.executeUpdate();
                    pstmt.setString(2, "Class");
                    pstmt.setString(3, "Edge");
                    pstmt.executeUpdate();
                    pstmt.setString(2, "Label");
                    pstmt.setString(3, "editedBy");
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    pstmt = conn.prepareStatement(sql4graph);
                    pstmt.setString(1, paperKey);
                    pstmt.setString(2, edgeKey);
                    pstmt.setString(3, personKey);
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // adding venue
        if (! book.getVenue().equals("")) {
            if (! venue.containsKey(book.getVenue())) {
                venueCounter++;
                venueKey = "V" + String.valueOf(venueCounter);
                venue.put(book.getVenue(), venueKey);
                try {
                    pstmt = conn.prepareStatement(sql4entity);
                    pstmt.setString(1, venueKey);
                    pstmt.setString(2, "Type");
                    pstmt.setString(3, "Venue");
                    pstmt.executeUpdate();
                    pstmt.setString(2, "class");
                    pstmt.setString(3, "entityNode");
                    pstmt.executeUpdate();
                    pstmt.setString(2, "Name");
                    pstmt.setString(3, book.getVenue());
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                venueKey = venue.get(book.getVenue());
            }
            if (!edge.contains(paperKey + venueKey)) {
                edgeCounter++;
                edge.add(paperKey + venueKey);
                edgeKey = "E" + String.valueOf(edgeCounter);
                try {
                    pstmt = conn.prepareStatement(sql4entity);
                    pstmt.setString(1, edgeKey);
                    pstmt.setString(2, "type");
                    pstmt.setString(3, "directedLink");
                    pstmt.executeUpdate();
                    pstmt.setString(2, "Class");
                    pstmt.setString(3, "Edge");
                    pstmt.executeUpdate();
                    pstmt.setString(2, "Label");
                    pstmt.setString(3, "publishedIn");
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    pstmt = conn.prepareStatement(sql4graph);
                    pstmt.setString(1, paperKey);
                    pstmt.setString(2, edgeKey);
                    pstmt.setString(3, venueKey);
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static List<String> String2List(String str) {
        List<String> list = new ArrayList<>();
        if (str.equals(""))
            return list;
        String[] split = str.split(", ");
        for (String one : split) {
            list.add(one);
        }
        return list;
    }

}
