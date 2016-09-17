package util;
/**
 * Created by Linus on 10/09/2016.
 */
public class StringUtil {

    /**
     * Check if a varied length of String arguments contains null element (String of length 0 is no null!)
     * @param strs
     * @return
     */
    public static boolean isArgumentsContainNull(String... strs) {
        for (String s : strs) {
            if (s == null) return true;
        }
        return false;
    }

    /**
     * Check if a string is null or length=0
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        return (s == null || s.length() == 0);
    }

}
