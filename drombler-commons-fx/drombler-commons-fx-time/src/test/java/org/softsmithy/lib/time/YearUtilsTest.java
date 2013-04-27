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
package org.softsmithy.lib.time;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author puce
 */
public class YearUtilsTest {

    @Test
    public void testGetMonthListNoMinNoMax() {
        Year year = Year.of(2013);
        List<Month> result = YearUtils.getMonthList(year, null, null);
        Month[] monthOfYears = Month.values();
        assertEquals(Arrays.asList(monthOfYears), result);
    }

    @Test
    public void testGetMonthListNotSameYear() {
        Year year = Year.of(2013);
        YearMonth minYearMonth = YearMonth.of(2012, Month.SEPTEMBER);
        YearMonth maxYearMonth = YearMonth.of(2014, Month.MARCH);
        List<Month> result = YearUtils.getMonthList(year, minYearMonth, maxYearMonth);
        Month[] monthOfYears = Month.values();
        assertEquals(Arrays.asList(monthOfYears), result);
    }

    @Test
    public void testGetMonthListOutOfRange() {
        Year year = Year.of(2013);
        YearMonth minYearMonth = YearMonth.of(2014, Month.MAY);
        YearMonth maxYearMonth = YearMonth.of(2014, Month.NOVEMBER);
        List<Month> result = YearUtils.getMonthList(year, minYearMonth, maxYearMonth);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testGetMonthListMinNoMax() {
        Year year = Year.of(2013);
        YearMonth minYearMonth = YearMonth.of(2013, Month.FEBRUARY);
        List<Month> result = YearUtils.getMonthList(year, minYearMonth, null);
        assertEquals(new ArrayList<>(EnumSet.range(Month.FEBRUARY, Month.DECEMBER)), result);
    }

    @Test
    public void testGetMonthListMaxNoMin() {
        Year year = Year.of(2013);
        YearMonth maxYearMonth = YearMonth.of(2013, Month.OCTOBER);
        List<Month> result = YearUtils.getMonthList(year, null, maxYearMonth);
        assertEquals(new ArrayList<>(EnumSet.range(Month.JANUARY, Month.OCTOBER)), result);
    }

    @Test
    public void testGetMonthListMinAndMax() {
        Year year = Year.of(2013);
        YearMonth minYearMonth = YearMonth.of(2013, Month.FEBRUARY);
        YearMonth maxYearMonth = YearMonth.of(2013, Month.OCTOBER);
        List<Month> result = YearUtils.getMonthList(year, minYearMonth, maxYearMonth);
        assertEquals(new ArrayList<>(EnumSet.range(Month.FEBRUARY, Month.OCTOBER)), result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetMonthListException() {
        Year year = Year.of(2013);
        YearMonth minYearMonth = YearMonth.of(2013, Month.OCTOBER);
        YearMonth maxYearMonth = YearMonth.of(2013, Month.FEBRUARY);
        YearUtils.getMonthList(year, minYearMonth, maxYearMonth);
    }

    @Test
    public void testGetMonthListMaxFutureYear() {
        Year year = Year.of(2013);
        YearMonth minYearMonth = YearMonth.of(2013, Month.FEBRUARY);
        YearMonth maxYearMonth = YearMonth.of(2014, Month.OCTOBER);
        List<Month> result = YearUtils.getMonthList(year, minYearMonth, maxYearMonth);
        assertEquals(new ArrayList<>(EnumSet.range(Month.FEBRUARY, Month.DECEMBER)), result);
    }

    @Test
    public void testGetMonthListMaxFutureYearMinNull() {
        Year year = Year.of(2013);
        YearMonth maxYearMonth = YearMonth.of(2014, Month.OCTOBER);
        List<Month> result = YearUtils.getMonthList(year, null, maxYearMonth);
        assertEquals(new ArrayList<>(EnumSet.range(Month.JANUARY, Month.DECEMBER)), result);
    }

    @Test
    public void testGetMonthListMinPreviousYear() {
        Year year = Year.of(2013);
        YearMonth minYearMonth = YearMonth.of(2012, Month.FEBRUARY);
        YearMonth maxYearMonth = YearMonth.of(2013, Month.OCTOBER);
        List<Month> result = YearUtils.getMonthList(year, minYearMonth, maxYearMonth);
        assertEquals(new ArrayList<>(EnumSet.range(Month.JANUARY, Month.OCTOBER)), result);
    }

    @Test
    public void testGetMonthListMinPreviousYearMaxNull() {
        Year year = Year.of(2013);
        YearMonth minYearMonth = YearMonth.of(2012, Month.FEBRUARY);
        List<Month> result = YearUtils.getMonthList(year, minYearMonth, null);
        assertEquals(new ArrayList<>(EnumSet.range(Month.JANUARY, Month.DECEMBER)), result);
    }

    @Test
    public void testGetMonthListMinFutureYearMaxNull() {
        Year year = Year.of(2013);
        YearMonth minYearMonth = YearMonth.of(2014, Month.FEBRUARY);
        List<Month> result = YearUtils.getMonthList(year, minYearMonth, null);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testGetMonthListMaxPreviousYearMinNull() {
        Year year = Year.of(2013);
        YearMonth maxYearMonth = YearMonth.of(2012, Month.FEBRUARY);
        List<Month> result = YearUtils.getMonthList(year, null, maxYearMonth);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testGetMonthListMinMaxEqual() {
        Year year = Year.of(2013);
        YearMonth minYearMonth = YearMonth.of(2013, Month.FEBRUARY);
        YearMonth maxYearMonth = YearMonth.of(2013, Month.FEBRUARY);
        List<Month> result = YearUtils.getMonthList(year, minYearMonth, maxYearMonth);
        assertEquals(Arrays.asList(Month.FEBRUARY), result);
    }
}
