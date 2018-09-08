package org.drombler.commons.fx.util.converter;

import java.text.NumberFormat;
import java.util.Locale;
import javafx.util.converter.CurrencyStringConverter;

/**
 *
 * @author puce
 */


public class XCurrencyStringConvert extends CurrencyStringConverter{

    public XCurrencyStringConvert() {
    }

    public XCurrencyStringConvert(Locale locale) {
        super(locale);
    }

    public XCurrencyStringConvert(String pattern) {
        super(pattern);
    }

    public XCurrencyStringConvert(Locale locale, String pattern) {
        super(locale, pattern);
    }

    public XCurrencyStringConvert(NumberFormat numberFormat) {
        super(numberFormat);
    }

    
    @Override
    public NumberFormat getNumberFormat() {
        return super.getNumberFormat();
    }
    
}
