/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.time.calendar;

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
