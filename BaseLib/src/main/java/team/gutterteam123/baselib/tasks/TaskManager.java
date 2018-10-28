package team.gutterteam123.baselib.tasks;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskManager {

    @Getter
    private static TaskManager instance = new TaskManager();

    @Getter
    private Set<Task> tasks = new HashSet<>();
    @Getter
    private TaskThread taskThread = new TaskThread();
    @Getter
    private Executor threadExecutor = Executors.newFixedThreadPool(2, new ThreadFactoryBuilder().setNameFormat("Async Task Executor %d").setUncaughtExceptionHandler((thread, throwable) -> {
        throw new TaskException("Exception in thread: " + thread.getName(), throwable);
    }).build());

    private TaskManager() {
        taskThread.start();
    }

    public void registerTask(Task task) {
        tasks.add(task);
    }

    public void unregisterTask(Task task) {
        tasks.remove(task);
    }

}
