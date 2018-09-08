package org.drombler.commons.fx.util.converter;

import java.text.NumberFormat;
import java.util.Locale;
import javafx.util.converter.NumberStringConverter;

/**
 *
 * @author puce
 */


public class XNumberStringConverter extends NumberStringConverter{

    public XNumberStringConverter() {
    }

    public XNumberStringConverter(Locale locale) {
        super(locale);
    }

    public XNumberStringConverter(String pattern) {
        super(pattern);
    }

    public XNumberStringConverter(Locale locale, String pattern) {
        super(locale, pattern);
    }

    public XNumberStringConverter(NumberFormat numberFormat) {
        super(numberFormat);
    }

    @Override
    public NumberFormat getNumberFormat() {
        return super.getNumberFormat(); 
    }
    
}
