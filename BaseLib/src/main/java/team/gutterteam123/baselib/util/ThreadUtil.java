package team.gutterteam123.baselib.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public final class ThreadUtil {

    public static void sleep(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private static final ThreadFactory DEFAULT_THREAD_FACTORY = Executors.defaultThreadFactory();

    public static ThreadFactory getThreadFactory(String nameFormat) {
        final AtomicLong count = (nameFormat != null) ? new AtomicLong(0) : null;
        return runnable -> {
            Thread thread = ThreadUtil.DEFAULT_THREAD_FACTORY.newThread(runnable);
            thread.setName(String.format(nameFormat, count.getAndIncrement()));
            thread.setDaemon(true);
            return thread;
        };
    }

}
