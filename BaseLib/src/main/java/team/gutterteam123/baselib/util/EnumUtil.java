package team.gutterteam123.baselib.util;

public final class EnumUtil {

    public static <T extends Enum<?>> T mactchenum(T[] values, String tomatch) {
        tomatch = tomatch.toLowerCase().replace('-', '_').replace(' ', '_');
        for (T check : values) {
            String name = check.name().toLowerCase();
            if (tomatch.equals(name) || (name.replace('_', ' ').equals(tomatch) || name.startsWith(tomatch)))
                return check;
        }
        return null;
    }

    public static String preattyName(Enum<?> e) {
        return e.name().toLowerCase().replace('_', ' ');
    }

}
