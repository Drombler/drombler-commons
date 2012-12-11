/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.commons.time.calendar;

import javax.time.calendar.LocalDate;
import javax.time.calendar.YearMonth;

/**
 *
 * @author puce
 */
public class YearMonthUtils {

    private YearMonthUtils() {
    }

    public static LocalDate atFirstDay(YearMonth yearMonth) {
        return yearMonth.atDay(1);
    }

    public static LocalDate atLastDay(YearMonth yearMonth) {
        return yearMonth.atDay(yearMonth.lengthInDays());
    }
}
