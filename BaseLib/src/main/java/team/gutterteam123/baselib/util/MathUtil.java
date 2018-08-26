package team.gutterteam123.baselib.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public final class MathUtil {

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        } else {
            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }
    }

    public static long averageLong(List<Long> list) {
        long add = 0L;

        Long listlist;
        for (Iterator var4 = list.iterator(); var4.hasNext(); add += listlist) {
            listlist = (Long) var4.next();
        }

        return add / (long) list.size();
    }

    public static double averageDouble(List<Double> list) {
        Double add = 0.0D;

        Double listlist;
        for (Iterator var3 = list.iterator(); var3.hasNext(); add = add + listlist) {
            listlist = (Double) var3.next();
        }

        return add / (double) list.size();
    }

    public static int randInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    public static long randLong(long min, long max) {
        return min + (long) (new Random().nextDouble() * (max - min));
    }

    public static double randDouble(double min, double max) {
        return min + new Random().nextDouble() * (max - min);
    }

}
