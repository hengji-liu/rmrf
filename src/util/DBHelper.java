package util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Linus on 10/09/2016.
 */
public class DBHelper {

    private static final String driver = "com.mysql.jdbc.Driver";

    private static final String url="jdbc:mysql://localhost:3306/bblib?useUnicode=true&characterEncoding=UTF-8";
    private static final String username="root";
    private static final String password="12345";

    private static Connection conn=null;

    static
    {
        try
        {
            Class.forName(driver);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public static Connection getConnection() throws Exception
    {
        if(conn==null)
        {
            conn = DriverManager.getConnection(url, username, password);
            return conn;
        }
        return conn;
    }

    public static void main(String[] args) {

        try
        {
            Connection conn = DBHelper.getConnection();
            if(conn!=null)
            {
                System.out.println("connection success");
            }
            else
            {
                System.out.println("connection failed");
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }
}
