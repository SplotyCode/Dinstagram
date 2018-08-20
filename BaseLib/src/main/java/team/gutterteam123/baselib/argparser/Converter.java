package team.gutterteam123.baselib.argparser;

import lombok.Getter;

import java.lang.reflect.Field;

public abstract class Converter<T> {

    @Getter private final Class<T> clazz;

    public Converter(Class<T> clazz) {
        this.clazz = clazz;
    }

    protected abstract void convert(Field field, String value, Object object) throws ReflectiveOperationException;

}
