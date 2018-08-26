package team.gutterteam123.baselib.cache;

import java.util.function.Supplier;

public class SimpleCache<T> {

    private long time;
    private T value;
    private long started;

    private Supplier<T> objectGetter;

    public SimpleCache(long time, T value) {
        this.time = time;
        this.value = value;
        started = System.currentTimeMillis();
    }

    public SimpleCache(long time, Supplier<T> objectGetter) {
        this.time = time;
        this.objectGetter = objectGetter;
    }

    public T get() {
        long delay = System.currentTimeMillis() - started;
        if (delay > time || value == null) {
            value = objectGetter.get();
            started = System.currentTimeMillis();
        }
        return value;
    }
}
