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
import javax.time.calendar.DayOfWeek;

/**
 *
 * @author puce
 */
public class DayOfWeekUtils {

    private DayOfWeekUtils() {
    }

    public static List<DayOfWeek> getOrderedDaysOfWeek(Locale locale) {
        List<DayOfWeek> orderedDaysOfWeek = new ArrayList<>(7);
        DayOfWeek firstDayOfWeek = DayOfWeek.firstDayOfWeekFor(locale);
        orderedDaysOfWeek.add(firstDayOfWeek);
        for (DayOfWeek dayOfWeek = firstDayOfWeek.next(); !dayOfWeek.equals(firstDayOfWeek); dayOfWeek = dayOfWeek.next()) {
            orderedDaysOfWeek.add(dayOfWeek);
        }
        return orderedDaysOfWeek;
    }
}
