/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.time.calendar.format;

import java.util.Locale;
import javax.time.calendar.Calendrical;
import javax.time.calendar.format.DateTimeFormatter;
import javax.time.calendar.format.DateTimeFormatters;
import org.softsmithy.lib.text.FormatException;
import org.softsmithy.lib.text.Formatter;

/**
 *
 * @author puce
 */
public class CalendricalFormatter implements Formatter<Calendrical> {

    private final DateTimeFormatter dateTimeFormatter;

    public CalendricalFormatter() {
        this(DateTimeFormatters.fullDate(Locale.getDefault()));
    }

    public CalendricalFormatter(DateTimeFormatter dateTimeFormatter) {
        if (!dateTimeFormatter.isPrintSupported()) {
            throw new IllegalArgumentException("The specified DateTimeFormatter does not support the 'print' operation!");
        }
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public String format(Calendrical calendrical) throws FormatException {
        try {
            return dateTimeFormatter.print(calendrical);
        } catch (Exception ex) {
            throw new FormatException(ex);
        }
    }

    @Override
    public void format(Calendrical calendrical, Appendable appendable) throws FormatException {
        try {
            dateTimeFormatter.print(calendrical, appendable);
        } catch (Exception ex) {
            throw new FormatException(ex);
        }
    }
}
