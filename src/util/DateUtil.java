package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    public static String getDateToMin(String originDateFormat){
        return originDateFormat.substring(0,16);
    }
    public static String getCurrentTimeToMin(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date today = Calendar.getInstance().getTime();
        return df.format(today);
    }

    public static String getCurrentTimeToDay(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date today = Calendar.getInstance().getTime();
        return df.format(today);
    }

}
