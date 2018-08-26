package team.gutterteam123.baselib.tasks;

public class TimerTask extends Task {

    private boolean running;
    private long timerDelay, startDelay;
    private long time;

    public TimerTask(boolean async, Runnable runnable, long timerDelay, long startDelay) {
        super(async, runnable);
        this.timerDelay = timerDelay;
        this.startDelay = startDelay;
        time = System.currentTimeMillis();
    }

    @Override
    boolean isReady() {
        return time + (running ? timerDelay : startDelay) <= System.currentTimeMillis();
    }

    @Override
    void onCall() {
        running = true;
        time = System.currentTimeMillis();
    }
}
