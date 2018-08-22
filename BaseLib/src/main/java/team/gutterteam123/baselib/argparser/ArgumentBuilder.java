package team.gutterteam123.baselib.argparser;

import com.google.common.reflect.ClassPath;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
public class ArgumentBuilder {

    private Object object;
    private String[] input;

    private Map<Class, Converter> converters = new HashMap<>();

    public ArgumentBuilder addConverter(Converter converter) {
        converters.put(converter.getClazz(), converter);
        return this;
    }

    public ArgumentBuilder addConverter(Class clazz, Converter converter) {
        converters.put(clazz, converter);
        return this;
    }

    public ArgumentBuilder setInput(String[] input) {
        this.input = input;
        return this;
    }

    public ArgumentBuilder setObject(Object object) {
        this.object = object;
        return this;
    }

    public void build() {
        if (input == null || object == null) throw new BuilderException("Input or Object is null");
        addDefaults();

        Set<Argument> arguments = new HashSet<>();
        for (Field field : object.getClass().getFields()) {
            if (field.isAnnotationPresent(Parameter.class)) {
                arguments.add(new Argument(field, field.getAnnotation(Parameter.class)));
            }
        }

        Argument argument = null;

        try {
            for (String value : input) {
                if (value.startsWith("-")) {
                    argument = arguments.stream().filter(par -> par.getName().equalsIgnoreCase(value.substring(1))).findFirst().orElseThrow(() -> new ArithmeticException("Invalid key " + value));
                    arguments.remove(argument);
                    if (argument.getField().getType() == boolean.class) {
                        argument.getField().setAccessible(true);
                        argument.getField().set(object, true);
                        argument = null;
                    }
                } else {
                    if (argument == null) throw new BuilderException("Needed an key for: " + value);
                    argument.getField().setAccessible(true);
                    Converter converter = converters.get(argument.getField().getType());
                    if (converter == null)
                        throw new BuilderException("Could not find converter for " + argument.getField().getType().getSimpleName());
                    converter.convert(argument.getField(), value, object);
                    argument = null;
                }
            }
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
        }

        for (Argument stillPresent : arguments) {
            if (stillPresent.getParameter().needed()) {
                throw new BuilderException(stillPresent.getName() + " needs to be specified");
            }
        }
    }

    private void addDefaults() {
        try {
            for (ClassPath.ClassInfo clazz : ClassPath.from(getClass().getClassLoader()).getTopLevelClasses("team.gutterteam123.baselib.argparser.converters")) {
                Converter converter = (Converter) clazz.load().newInstance();
                addConverter(converter);
            }
        } catch (IOException | ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    @Getter @Setter @AllArgsConstructor
    private static class Argument {

        private Field field;
        private Parameter parameter;

        public String getName() {
            return parameter.name();
        }

    }

}
