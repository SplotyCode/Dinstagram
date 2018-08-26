package team.gutterteam123.baselib.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class StringUtil {

    public final static String UTF8 = "UTF-8";

    public static boolean containsIgnoreCase(String str, String searchStr) {
        if (str == null || searchStr == null) return false;

        final int length = searchStr.length();
        if (length == 0)
            return true;

        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true;
        }
        return false;
    }

    public static boolean isEmpty(String s) {
        return !isNotEmpty(s);
    }

    public static List<String> convertToList(String s, String... strings) {
        List<String> founds = new ArrayList<>(Collections.singletonList(s));
        List<String> splits = new ArrayList<>(Arrays.asList(strings));

        while (!splits.isEmpty()) {
            String split = splits.remove(0);
            for (String found : founds)
                if (found.split(split).length > 1) {
                    founds.remove(found);
                    founds.addAll(Arrays.asList(found.split(split)));
                }
        }
        for (String found : founds)
            if (found.isEmpty() || found.equals(" ") || found.equals(""))
                founds.remove(found);
        return founds;
    }

    public static boolean isNotEmpty(String s) {
        return s != null && !s.isEmpty() && !s.equals(" ");
    }

    public static boolean isNotEmpty(String s, String first) {
        s = s.replaceFirst(first, "");
        return s != null && !s.isEmpty() && !s.equals(" ");
    }

    public static List<String> convertToList(String s, String split) {
        List<String> founds = new ArrayList<>(Arrays.asList(s));
        founds.addAll(Arrays.asList(s.split(split)));

        for (String found : founds) {
            if (found.isEmpty() || found.equals(" ") || found.equals("")) {
                founds.remove(found);
            }
        }
        return founds;
    }

    public static String build(String... parts) {
        if (parts.length == 0) {
            return "";
        } else if (parts.length == 1) {
            return parts[0];
        } else {
            StringBuilder sb = new StringBuilder();
            for (String s : parts) {
                sb.append(s);
            }
            return sb.toString();
        }
    }

    public static String removeChars(String string, char... chars) {
        if (isEmpty(string)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            boolean found = false;
            for (char cc : chars) {
                if (cc == c) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static boolean equalsIgnoreCaseTrimmed(String str1, String str2) {
        return (str1 != null && str2 != null) && str1.trim().equalsIgnoreCase(str2.trim());
    }

    public static String limitLength(String string, int maxLength) {
        return string == null ? "" : (string.length() > maxLength ? string.substring(0, maxLength) : string);
    }

    public static String repeat(char ch, int times) {
        if (times == 1) {
            return new String(new char[]{ch});
        } else {
            char[] cha = new char[times];
            for (int i = 0; i < times; ++i) {
                cha[i] = ch;
            }
            return new String(cha);
        }
    }

    public static String repeat(String str, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; ++i) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static String toString(List<String> list, String seperator, boolean removeend) {
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s).append(seperator);
        }
        String str = sb.toString();
        if (removeend && str.endsWith(seperator)) {
            str = str.substring(0, str.length() - seperator.length());
        }
        return str;
    }

    public static String toString(List<String> list, String seperator) {
        return toString(list, seperator, true);
    }

    public static String toString(String[] list, String seperator, boolean removeend) {
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s).append(seperator);
        }
        String str = sb.toString();
        if (removeend && str.length() >= seperator.length()) {
            str = str.substring(0, str.length() - seperator.length());
        }
        return str;
    }

    public static <T> String toString(List<T> list, ToString<T> toString, String seperator) {
        StringBuilder sb = new StringBuilder();
        for (T s : list) {
            sb.append(toString.getString(s)).append(seperator);
        }
        String str = sb.toString();
        if (str.length() >= seperator.length()) {
            str = str.substring(0, str.length() - seperator.length());
        }
        return str;
    }

    public interface ToString<T> {
        String getString(T obj);
    }

    public static String toString(String[] list, String separator) {
        return toString(list, separator, true);
    }

    public static String toStringIfOver0(String[] list, String separator, boolean removeEnd) {
        if (list.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s).append(separator);
        }
        String str = sb.toString();
        if (removeEnd) {
            str = str.substring(0, str.length() - separator.length());
        }
        return str;

    }

    public static String toStringIfOver0(String[] list, String separator) {
        return toStringIfOver0(list, separator, true);
    }

    public static String toStringIfOver0(List<String> list, String separator, boolean removeEnd) {
        if (list.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s).append(separator);
        }
        String str = sb.toString();
        if (removeEnd) {
            str = str.substring(0, str.length() - separator.length());
        }
        return str;
    }

    public static String firstbig(String string) {
        return string.substring(0, string.length() - 1).toUpperCase() + string.substring(1).toUpperCase();
    }

    public static String toStringIfOver0(List<String> list, String separator) {
        return toStringIfOver0(list, separator, true);
    }

    public static String bracketize(String s, boolean soft) {
        return soft ? "(" + s + ")" : "[" + s + "]";
    }

    public static String capitalize(String s, boolean space) {
        return space ? capitalizeSpace(s) : s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    private static String capitalizeSpace(String s) {
        StringBuilder product = new StringBuilder();
        for (String str : s.split(" ")) {
            product.append(capitalize(str, false));
        }
        return product.toString();
    }

    public static byte[] utf8(String string) {
        try {
            return string.getBytes(UTF8);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static String utf8(byte[] bytes) {
        try {
            return new String(bytes, UTF8);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

}
