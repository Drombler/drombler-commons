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
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import javax.time.calendar.MonthOfYear;
import javax.time.calendar.Year;
import javax.time.calendar.YearMonth;
import org.drombler.commons.fx.beans.property.LimitedComparableProperty;
import org.softsmithy.lib.util.Comparables;

/**
 *
 * @author puce
 */
public class YearUtils {

    private YearUtils() {
    }

    public static List<MonthOfYear> getMonthOfYearList(Year year, YearMonth minYearMonth, YearMonth maxYearMonth) {
        if (!Comparables.isInRange(year.getValue(), minYearMonth.getYear(), maxYearMonth.getYear())) {
            return Collections.emptyList();
        } else {
            Set<MonthOfYear> monthOfYearSet = EnumSet.allOf(MonthOfYear.class);

            if (minYearMonth != null && Comparables.isEqual(year.getValue(), minYearMonth.getYear())) {
                for (MonthOfYear moy : MonthOfYear.values()) {
                    if (Comparables.isLess(moy, minYearMonth.getMonthOfYear())) {
                        monthOfYearSet.remove(moy);
                    }
                }
            }
            
            if (maxYearMonth != null && Comparables.isEqual(year.getValue(), maxYearMonth.getYear())) {
                for (MonthOfYear moy : MonthOfYear.values()) {
                    if (Comparables.isGreater(moy, maxYearMonth.getMonthOfYear())) {
                        monthOfYearSet.remove(moy);
                    }
                }
            }

            return new ArrayList<>(monthOfYearSet);
        }
    }
}
