package team.gutterteam123.baselib.tasks;

public class RunLaterTask extends Task {

    private long ready;

    public RunLaterTask(boolean async, Runnable runnable, long delay) {
        super(async, runnable);
        ready = System.currentTimeMillis() + delay;
    }

    @Override
    boolean isReady() {
        return System.currentTimeMillis() >= ready;
    }

    @Override
    void onCall() {
        TaskManager.getInstance().getTasks().remove(this);
    }

    @Override
    Runnable getRunnable() {
        return runnable;
    }
}
