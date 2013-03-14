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
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import javax.time.calendar.MonthOfYear;
import javax.time.calendar.Year;
import javax.time.calendar.YearMonth;


import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author puce
 */
public class YearUtilsTest {

    @Test
    public void testGetMonthOfYearListNoMinNoMax() {
        Year year = Year.of(2013);
        List<MonthOfYear> result = YearUtils.getMonthOfYearList(year, null, null);
        MonthOfYear[] monthOfYears = MonthOfYear.values();
        assertEquals(Arrays.asList(monthOfYears), result);
    }

    @Test
    public void testGetMonthOfYearListNotSameYear() {
        Year year = Year.of(2013);
        YearMonth minYearMonth = YearMonth.of(2012, MonthOfYear.SEPTEMBER);
        YearMonth maxYearMonth = YearMonth.of(2014, MonthOfYear.MARCH);
        List<MonthOfYear> result = YearUtils.getMonthOfYearList(year, minYearMonth, maxYearMonth);
        MonthOfYear[] monthOfYears = MonthOfYear.values();
        assertEquals(Arrays.asList(monthOfYears), result);
    }

    @Test
    public void testGetMonthOfYearListOutOfRange() {
        Year year = Year.of(2013);
        YearMonth minYearMonth = YearMonth.of(2014, MonthOfYear.MAY);
        YearMonth maxYearMonth = YearMonth.of(2014, MonthOfYear.NOVEMBER);
        List<MonthOfYear> result = YearUtils.getMonthOfYearList(year, minYearMonth, maxYearMonth);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testGetMonthOfYearListMinNoMax() {
        Year year = Year.of(2013);
        YearMonth minYearMonth = YearMonth.of(2013, MonthOfYear.FEBRUARY);
        List<MonthOfYear> result = YearUtils.getMonthOfYearList(year, minYearMonth, null);
        assertEquals(new ArrayList<>(EnumSet.range(MonthOfYear.FEBRUARY, MonthOfYear.DECEMBER)), result);
    }

    @Test
    public void testGetMonthOfYearListMaxNoMin() {
        Year year = Year.of(2013);
        YearMonth maxYearMonth = YearMonth.of(2013, MonthOfYear.OCTOBER);
        List<MonthOfYear> result = YearUtils.getMonthOfYearList(year, null, maxYearMonth);
        assertEquals(new ArrayList<>(EnumSet.range(MonthOfYear.JANUARY, MonthOfYear.OCTOBER)), result);
    }

    @Test
    public void testGetMonthOfYearListMinAndMax() {
        Year year = Year.of(2013);
        YearMonth minYearMonth = YearMonth.of(2013, MonthOfYear.FEBRUARY);
        YearMonth maxYearMonth = YearMonth.of(2013, MonthOfYear.OCTOBER);
        List<MonthOfYear> result = YearUtils.getMonthOfYearList(year, minYearMonth, maxYearMonth);
        assertEquals(new ArrayList<>(EnumSet.range(MonthOfYear.FEBRUARY, MonthOfYear.OCTOBER)), result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetMonthOfYearListException() {
        Year year = Year.of(2013);
        YearMonth minYearMonth = YearMonth.of(2013, MonthOfYear.OCTOBER);
        YearMonth maxYearMonth = YearMonth.of(2013, MonthOfYear.FEBRUARY);
        YearUtils.getMonthOfYearList(year, minYearMonth, maxYearMonth);
    }

    @Test
    public void testGetMonthOfYearListMaxFutureYear() {
        Year year = Year.of(2013);
        YearMonth minYearMonth = YearMonth.of(2013, MonthOfYear.FEBRUARY);
        YearMonth maxYearMonth = YearMonth.of(2014, MonthOfYear.OCTOBER);
        List<MonthOfYear> result = YearUtils.getMonthOfYearList(year, minYearMonth, maxYearMonth);
        assertEquals(new ArrayList<>(EnumSet.range(MonthOfYear.FEBRUARY, MonthOfYear.DECEMBER)), result);
    }

    @Test
    public void testGetMonthOfYearListMaxFutureYearMinNull() {
        Year year = Year.of(2013);
        YearMonth maxYearMonth = YearMonth.of(2014, MonthOfYear.OCTOBER);
        List<MonthOfYear> result = YearUtils.getMonthOfYearList(year, null, maxYearMonth);
        assertEquals(new ArrayList<>(EnumSet.range(MonthOfYear.JANUARY, MonthOfYear.DECEMBER)), result);
    }

    @Test
    public void testGetMonthOfYearListMinPreviousYear() {
        Year year = Year.of(2013);
        YearMonth minYearMonth = YearMonth.of(2012, MonthOfYear.FEBRUARY);
        YearMonth maxYearMonth = YearMonth.of(2013, MonthOfYear.OCTOBER);
        List<MonthOfYear> result = YearUtils.getMonthOfYearList(year, minYearMonth, maxYearMonth);
        assertEquals(new ArrayList<>(EnumSet.range(MonthOfYear.JANUARY, MonthOfYear.OCTOBER)), result);
    }

    @Test
    public void testGetMonthOfYearListMinPreviousYearMaxNull() {
        Year year = Year.of(2013);
        YearMonth minYearMonth = YearMonth.of(2012, MonthOfYear.FEBRUARY);
        List<MonthOfYear> result = YearUtils.getMonthOfYearList(year, minYearMonth, null);
        assertEquals(new ArrayList<>(EnumSet.range(MonthOfYear.JANUARY, MonthOfYear.DECEMBER)), result);
    }

    @Test
    public void testGetMonthOfYearListMinFutureYearMaxNull() {
        Year year = Year.of(2013);
        YearMonth minYearMonth = YearMonth.of(2014, MonthOfYear.FEBRUARY);
        List<MonthOfYear> result = YearUtils.getMonthOfYearList(year, minYearMonth, null);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testGetMonthOfYearListMaxPreviousYearMinNull() {
        Year year = Year.of(2013);
        YearMonth maxYearMonth = YearMonth.of(2012, MonthOfYear.FEBRUARY);
        List<MonthOfYear> result = YearUtils.getMonthOfYearList(year, null, maxYearMonth);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testGetMonthOfYearListMinMaxEqual() {
        Year year = Year.of(2013);
        YearMonth minYearMonth = YearMonth.of(2013, MonthOfYear.FEBRUARY);
        YearMonth maxYearMonth = YearMonth.of(2013, MonthOfYear.FEBRUARY);
        List<MonthOfYear> result = YearUtils.getMonthOfYearList(year, minYearMonth, maxYearMonth);
        assertEquals(Arrays.asList(MonthOfYear.FEBRUARY), result);
    }
}
