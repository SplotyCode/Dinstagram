package team.gutterteam123.baselib.cache;

import java.util.function.Supplier;

public class ResetableCache<T> implements Cache<T> {

    private T cachedValue;
    private long started;
    private long time;

    private Supplier<T> objectGetter;

    public void reset() {
        started = System.currentTimeMillis();
    }

    public ResetableCache(long time, Supplier<T> objectGetter) {
        this.time = time;
        this.objectGetter = objectGetter;
    }

    @Override
    public T get() {
        long delay = System.currentTimeMillis() - started;
        if (delay > time || cachedValue == null) {
            cachedValue = objectGetter.get();
            started = System.currentTimeMillis();
        }
        return cachedValue;
    }
}
