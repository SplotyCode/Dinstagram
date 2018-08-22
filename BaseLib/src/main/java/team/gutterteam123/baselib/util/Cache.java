package team.gutterteam123.baselib.util;

import java.util.function.Supplier;

public class Cache<T> {

    private long time;
    private T value;
    private long started;

    private Supplier<T> objectGetter;

    public Cache(long time, T value) {
        this.time = time;
        this.value = value;
        started = System.currentTimeMillis();
    }

    public Cache(long time, Supplier<T> objectGetter) {
        this.time = time;
        this.objectGetter = objectGetter;
        value = objectGetter.get();
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
