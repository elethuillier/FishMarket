package shared;

import java.util.Arrays;

public class Utils {

    public static boolean isNumeric(String strNum) {
        if (strNum == null) return false;
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean areNumeric(String[] strNums) {
        return Arrays.stream(strNums).allMatch(Utils::isNumeric);
    }

    public class BackgroundColor {
        public static final String RED = "";

    }
}
