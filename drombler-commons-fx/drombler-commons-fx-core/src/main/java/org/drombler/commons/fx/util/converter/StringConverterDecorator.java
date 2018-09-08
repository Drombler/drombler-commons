package org.drombler.commons.fx.util.converter;

import javafx.util.StringConverter;

/**
 *
 * @author puce
 */


public class StringConverterDecorator<T> extends StringConverter<T>{

    private final StringConverter<T> converter;

    public StringConverterDecorator(StringConverter<T> converter) {
        this.converter = converter;
    }

    
    @Override
    public String toString(T object) {
        return converter.toString(object);
    }

    @Override
    public T fromString(String string) {
        return converter.fromString(string);
    }
    
}
