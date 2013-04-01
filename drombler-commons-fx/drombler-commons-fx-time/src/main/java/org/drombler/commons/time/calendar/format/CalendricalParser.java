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

import java.text.ParseException;
import java.util.Locale;
import javax.time.calendar.Calendrical;
import javax.time.calendar.format.DateTimeFormatter;
import javax.time.calendar.format.DateTimeFormatters;
import org.softsmithy.lib.text.AbstractParser;
import org.softsmithy.lib.text.Parser;

/**
 * A {@link Parser} for {@link Calendrical}.
 *
 * @author puce
 */
public class CalendricalParser extends AbstractParser<Calendrical> {

    private final DateTimeFormatter dateTimeFormatter;

    /**
     * Creates a new instance of this class. Uses
     * {@link DateTimeFormatters#fullDate(java.util.Locale)} by default.
     *
     * @see DateTimeFormatters#fullDate(java.util.Locale)
     */
    public CalendricalParser() {
        this(DateTimeFormatters.fullDate(Locale.getDefault()));
    }

    /**
     * Creates a new instance of this class.
     *
     * @param dateTimeFormatter a {@link DateTimeFormatter} which supports parsing
     * 
     * @see DateTimeFormatter#isParseSupported() 
     */
    public CalendricalParser(DateTimeFormatter dateTimeFormatter) {
        if (!dateTimeFormatter.isParseSupported()) {
            throw new IllegalArgumentException("The specified DateTimeFormatter does not support the 'parse' operation!");
        }
        this.dateTimeFormatter = dateTimeFormatter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Calendrical parseString(String text) throws ParseException {
        return dateTimeFormatter.parse(text);
    }
}
