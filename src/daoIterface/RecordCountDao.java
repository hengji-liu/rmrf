package daoIterface;

import util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Linus on 17/09/2016.
 */
public class RecordCountDao {

    /**
     * Give a Total count of a certian query
     * For example, If we want to get a count user, we will genereate
     * select count (*) from user; we can do this by carlling
     * sql: recordCountDao.getCountSQL("(*)","FROM user;")
     * @param countFlag
     * @param querySql
     * @return
     */
    public int recordCount(String countFlag,String querySql) {

        int count = 0;
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConnection();
            String countQuery = getCountSQL(countFlag,querySql);
            psmt = conn.prepareStatement(countQuery);
            rs = psmt.executeQuery();
            if (rs.next()) {
                count = Integer.parseInt(rs.getString("count(*)"));
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return count;
        } finally {
            DBHelper.realease(rs, psmt);
        }

    }

    private String getCountSQL(String countKey,String query) {
        StringBuilder stringBuilder = new StringBuilder("SELECT");
        stringBuilder.append(" COUNT" + countKey);
        stringBuilder.append(" " + query);
        return stringBuilder.toString();
    }


}
