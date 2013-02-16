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
import java.util.List;
import javax.time.calendar.Year;
import javax.time.calendar.YearMonth;
import org.junit.Test;


import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author puce
 */
public class YearUtilsTest {
    
    public YearUtilsTest() {
    }

    @Test
    @Ignore
    public void testGetMonthOfYearList() {
        System.out.println("getMonthOfYearList");
        Year year = null;
        YearMonth minYearMonth = null;
        YearMonth maxYearMonth = null;
        List expResult = null;
        List result = YearUtils.getMonthOfYearList(year, minYearMonth, maxYearMonth);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
}
