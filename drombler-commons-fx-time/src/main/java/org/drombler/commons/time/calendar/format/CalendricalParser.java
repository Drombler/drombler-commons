/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.commons.time.calendar.format;

import java.text.ParseException;
import java.util.Locale;
import javax.time.calendar.Calendrical;
import javax.time.calendar.format.DateTimeFormatter;
import javax.time.calendar.format.DateTimeFormatters;
import org.softsmithy.lib.text.AbstractParser;

/**
 *
 * @author puce
 */
public class CalendricalParser extends AbstractParser<Calendrical> {

    private final DateTimeFormatter dateTimeFormatter;

    public CalendricalParser() {
        this(DateTimeFormatters.fullDate(Locale.getDefault()));
    }

    public CalendricalParser(DateTimeFormatter dateTimeFormatter) {
        if (!dateTimeFormatter.isParseSupported()) {
            throw new IllegalArgumentException("The specified DateTimeFormatter does not support the 'parse' operation!");
        }
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public Calendrical parseString(String text) throws ParseException {
        return dateTimeFormatter.parse(text);
    }
}
