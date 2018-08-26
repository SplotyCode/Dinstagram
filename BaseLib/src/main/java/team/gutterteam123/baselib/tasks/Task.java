package team.gutterteam123.baselib.tasks;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class Task {

    @Getter
    private final boolean async;
    protected final Runnable runnable;

    abstract boolean isReady();

    abstract void onCall();

    abstract Runnable getRunnable();

}
