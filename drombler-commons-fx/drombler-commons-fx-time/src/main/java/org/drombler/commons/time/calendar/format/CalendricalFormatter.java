/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (Sourceforge.net user: puce).
 * Copyright 2012 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.time.calendar.format;

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
