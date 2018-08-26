package team.gutterteam123.baselib.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.gutterteam123.baselib.util.ThreadUtil;

import java.util.HashSet;

public class TaskThread extends Thread {

    /* Delay in ms */
    private static final long REFRESH_DELAY = 250;

    final Logger logger = LoggerFactory.getLogger(getClass());

    public TaskThread() {
        super("Task Thread");
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        for (Task task : new HashSet<>(TaskManager.getInstance().getTasks())) {
            if (task.isReady()) {
                if (task.isAsync())
                    TaskManager.getInstance().getThreadExecutor().execute(task.getRunnable());
                else task.getRunnable().run();
                task.onCall();
            }
        }
        long delay = System.currentTimeMillis() - start;
        if (delay > 1200) {
            logger.debug("TaskThread is heavily overloaded! Current Delay: {}", delay);
        }
        ThreadUtil.sleep(Math.max(0, REFRESH_DELAY - delay));
        run();
    }

}
