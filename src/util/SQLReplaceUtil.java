package util;


public class SQLReplaceUtil {

    public static String replaceQuestionMarks(String sql, String ...values){
        StringBuilder buffer = new StringBuilder(sql);
        int valueCount = 0;
        for(int i=0 ; i < buffer.length(); ++i){
            if(buffer.charAt(i) == '?'){
                buffer.replace(i,i+1,values[valueCount]);
                ++valueCount;
            }
        }
        return buffer.toString();
    }


    public static void main(String []args){
        String sql = "SELECT count(*) FROM user WHERE (type_='1' or type_='4') AND "
                + "(username LIKE ? OR firstname LIKE ? OR lastname LIKE ?)";
        String result = replaceQuestionMarks(sql,"\'hengji\'","\'hengji\'","\'hengji\';");
        System.out.println(result);
    }


}
