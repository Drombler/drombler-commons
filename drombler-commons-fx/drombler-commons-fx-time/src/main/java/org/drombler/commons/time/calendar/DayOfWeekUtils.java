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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.time.DayOfWeek;
import java.time.temporal.WeekFields;

/**
 * Utility class for {@link DayOfWeek}.
 *
 * @author puce
 */
public class DayOfWeekUtils {

    private DayOfWeekUtils() {
    }
    /**
     * The number of days in a week.
     *
     * TODO: use a method instead of a constant?
     */
    public static final int DAYS_IN_WEEK = DayOfWeek.values().length;

    /**
     * Gets the {@link DayOfWeek}s in locale-sensitive order.
     *
     * E.g.: <ul> <li>locale=de_CH: [MONDAY - SUNDAY] </li> <li>locale=en_US:
     * [SUNDAY - SATURDAY] </li></ul>
     *
     * @see DayOfWeek#firstDayOfWeekFor(java.util.Locale)
     * @param locale the {@link Locale}
     * @return the {@link DayOfWeek}s in locale-sensitive order
     */
    public static List<DayOfWeek> getOrderedDaysOfWeek(Locale locale) {
        List<DayOfWeek> orderedDaysOfWeek = new ArrayList<>(DAYS_IN_WEEK);
        DayOfWeek firstDayOfWeek = WeekFields.of(locale).getFirstDayOfWeek();
        orderedDaysOfWeek.add(firstDayOfWeek);
        for (DayOfWeek dayOfWeek = firstDayOfWeek.plus(1); !dayOfWeek.equals(firstDayOfWeek); dayOfWeek = dayOfWeek.plus(1)) {
            orderedDaysOfWeek.add(dayOfWeek);
        }
        return orderedDaysOfWeek;
    }
}
