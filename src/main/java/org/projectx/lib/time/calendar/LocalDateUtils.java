/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.time.calendar;

import javax.time.calendar.LocalDate;
import javax.time.calendar.YearMonth;

/**
 *
 * @author puce
 */
public class LocalDateUtils {

    private LocalDateUtils() {
    }

    public static YearMonth getYearMonth(LocalDate localDate) {
        return localDate != null ? YearMonth.of(localDate.getYear(), localDate.getMonthOfYear()) : null;
    }
}
