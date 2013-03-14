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
package org.drombler.commons.time.calendar;

import javax.time.calendar.LocalDate;
import javax.time.calendar.YearMonth;

/**
 * Utility class for {@link YearMonth}.
 *
 * @author puce
 */
public class YearMonthUtils {

    private YearMonthUtils() {
    }

    /**
     * Gets the first day of the specified {@link YearMonth}.
     *
     * @see YearMonth#atDay(int)
     * @param yearMonth a{@link YearMonth}
     * @return the first day of the specified {@link YearMonth}
     */
    public static LocalDate atFirstDay(YearMonth yearMonth) {
        return yearMonth.atDay(1);
    }

    /**
     * Gets the last day of the specified {@link YearMonth}.
     *
     * @see YearMonth#atDay(int)
     * @see YearMonth#lengthInDays()
     * @param yearMonth a{@link YearMonth}
     * @return the last day of the specified {@link YearMonth}
     */
    public static LocalDate atLastDay(YearMonth yearMonth) {
        return yearMonth.atDay(yearMonth.lengthInDays());
    }
}
