package team.gutterteam123.baselib.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public final class ThreadUtil {

    private static final Logger logger = LoggerFactory.getLogger(ThreadUtil.class);


    public static void sleep(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
            logger.error("Sleep got interrupted", ex);
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
