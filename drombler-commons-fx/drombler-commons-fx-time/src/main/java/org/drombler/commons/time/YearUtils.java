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
package org.drombler.commons.time;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import org.softsmithy.lib.util.Comparables;

/**
 * Utility class for {@link Year}.
 *
 * @author puce
 */
public class YearUtils {

    private YearUtils() {
    }

    /**
     * Gets the {@link MonthOfYear}s of a specified {@link Year}. An optional
     * min {@link YearMonth} and max {@link YearMonth} will truncate the list of
     * {@link MonthOfYear}.
     *
     * E.g.:
     *
     * <ul> <li>year=2013, min = null, max=null: [JANUARY - DECEMBER] </li>
     * <li>year=2013, min = 2012-10, max=2014-02: [JANUARY - DECEMBER] </li>
     * <li>year=2013, min = 2013-02, max=2013-10: [FEBRUARY - OCTOBER] </li>
     * <li>year=2014, min = 2013-02, max=2013-10: [] </li> </ul>
     *
     * @param year the Year
     * @param minYearMonth the min {@link YearMonth} or null
     * @param maxYearMonth the max {@link YearMonth} or null
     * @return the {@link MonthOfYear}s of a specified {@link Year}
     */
    public static List<Month> getMonthList(Year year, YearMonth minYearMonth, YearMonth maxYearMonth) {
        if (minYearMonth != null && maxYearMonth != null && Comparables.isLess(maxYearMonth, minYearMonth)) {
            throw new IllegalArgumentException("minYearMonth must not be greater than maxYearMonth!");
        }
        Integer minYear = minYearMonth != null ? minYearMonth.getYear() : null;
        Integer maxYear = maxYearMonth != null ? maxYearMonth.getYear() : null;
        if (!Comparables.isInRange(year.getValue(), minYear, maxYear)) {
            return Collections.emptyList();
        } else {
            Month min = (minYearMonth != null && Comparables.isEqual(year.getValue(), minYear))
                    ? minYearMonth.getMonth()
                    : Month.JANUARY;
            Month max = (maxYearMonth != null && Comparables.isEqual(year.getValue(), maxYear))
                    ? maxYearMonth.getMonth()
                    : Month.DECEMBER;

            return new ArrayList<>(EnumSet.range(min, max));
        }
    }
}
