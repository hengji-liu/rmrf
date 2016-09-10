package util;

/**
 * Created by Linus on 10/09/2016.
 */
public class StringUtil {


    public static boolean isArgumentsContainNull(String... strs) {
        for (String s : strs) {
            if (isEmpty(s)) return true;
        }
        return false;
    }

    public static boolean isEmpty(String s) {
        return (s == null || s.length() == 0);
    }

}
