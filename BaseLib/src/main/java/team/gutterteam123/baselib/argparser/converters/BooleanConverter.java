package team.gutterteam123.baselib.argparser.converters;

import team.gutterteam123.baselib.argparser.Converter;

import java.lang.reflect.Field;

public class BooleanConverter extends Converter<Boolean> {

    public BooleanConverter() {
        super(boolean.class);
    }

    @Override
    protected void convert(Field field, String value, Object object) throws ReflectiveOperationException {
        field.setBoolean(object, Boolean.valueOf(value));
    }
}
