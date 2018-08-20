package team.gutterteam123.baselib.argparser.converters;

import team.gutterteam123.baselib.argparser.Converter;

import java.lang.reflect.Field;

public class StringConverter extends Converter<String> {

    public StringConverter() {
        super(String.class);
    }

    @Override
    protected void convert(Field field, String value, Object object) throws ReflectiveOperationException {
        field.set(object, value);
    }
}
