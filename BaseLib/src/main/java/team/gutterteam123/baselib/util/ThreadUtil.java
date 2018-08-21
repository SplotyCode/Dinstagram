package team.gutterteam123.baselib.util;

public final class ThreadUtil {

    public static void sleep(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
