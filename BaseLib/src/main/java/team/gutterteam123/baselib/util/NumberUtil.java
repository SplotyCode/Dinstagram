package team.gutterteam123.baselib.util;

import lombok.Getter;

@Getter
public class NumberUtil {

    private static final int[] singleDigits = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    public static boolean isNumber(String s) {
        try {
            Integer.valueOf(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isDouble(String s) {
        try {
            Double.valueOf(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isBoolean(String s) {
        try {
            Boolean.valueOf(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isNumberComma(String s) {
        try {
            Double.valueOf(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isShort(String s) {
        try {
            Short.valueOf(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isLong(String s) {
        try {
            Long.valueOf(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static int getnumber(String s) {
        try {
            return Integer.valueOf(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static double getDouble(String s) {
        try {
            return Double.valueOf(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static float getFloat(String str) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            return -1F;
        }
    }

    public static short getShort(String str) {
        try {
            return Short.parseShort(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static long getLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static boolean getBoolean(String str) {
        try {
            return Boolean.parseBoolean(str);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static double getVersion(String version) {
        version = version.replaceAll("\\.", "");
        version = version.replace(":", ".");
        return Double.valueOf(version);
    }

}
