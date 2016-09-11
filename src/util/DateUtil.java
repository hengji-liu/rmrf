package util;

/**
 * Created by Linus on 11/09/2016.
 */
public class DateUtil {

    /**
     * Convert a originDateFormat namely, YYYY-MM-DD HH-MM-SS
     * @param originDateFormat
     * @return YYYY-MM-DD
     */
    public static String getDateToDay(String originDateFormat){
        return originDateFormat.substring(0,10);
    }

}
