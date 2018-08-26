package team.gutterteam123.baselib.tasks;

import com.google.common.base.Supplier;

public class CancelableTimerTask extends TimerTask {

    private Supplier<Boolean> supplier;
    private boolean result;

    public CancelableTimerTask(boolean async, Supplier<Boolean> supplier, long timerDelay, long startDelay) {
        super(async, null, timerDelay, startDelay);
        this.supplier = supplier;
    }

    @Override
    void onCall() {
        super.onCall();
        if (result) {
            TaskManager.getInstance().getTasks().remove(this);
        }
    }

    @Override
    Runnable getRunnable() {
        return () -> result = supplier.get();
    }
}
