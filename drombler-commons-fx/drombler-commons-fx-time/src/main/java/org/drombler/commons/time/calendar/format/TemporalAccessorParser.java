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
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
import org.softsmithy.lib.text.AbstractParser;
import org.softsmithy.lib.text.Parser;

/**
 * A {@link Parser} for {@link TemporalAccessor}.
 *
 * @author puce
 */
public class TemporalAccessorParser extends AbstractParser<TemporalAccessor> {

    private final DateTimeFormatter dateTimeFormatter;

    /**
     * Creates a new instance of this class. Uses
     * {@link DateTimeFormatter#ofLocalizedDate(java.time.format.FormatStyle)} and
     * {@link FormatStyle#FULL} by default.
     *
     * @see DateTimeFormatter#ofLocalizedDate(java.time.format.FormatStyle)
     * @see FormatStyle#FULL
     */
    public TemporalAccessorParser() {
        this(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
    }

    /**
     * Creates a new instance of this class.
     *
     * @param dateTimeFormatter a {@link DateTimeFormatter}
     *
     */
    public TemporalAccessorParser(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TemporalAccessor parseString(String text) throws ParseException {
        return dateTimeFormatter.parse(text);
    }
}
