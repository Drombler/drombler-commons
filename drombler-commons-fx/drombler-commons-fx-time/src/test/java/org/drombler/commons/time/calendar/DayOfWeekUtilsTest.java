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

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.time.calendar.DayOfWeek;
import static javax.time.calendar.DayOfWeek.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author puce
 */
public class DayOfWeekUtilsTest {

    @Test
    public void testGetOrderedDaysOfWeekDeCh() {
        System.out.println("testGetOrderedDaysOfWeek");
        List<DayOfWeek> orderedDaysOfWeek = DayOfWeekUtils.getOrderedDaysOfWeek(new Locale("de", "CH"));
        assertEquals(7, orderedDaysOfWeek.size());
        assertEquals(Arrays.asList(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY), orderedDaysOfWeek);
    }

    @Test
    public void testGetOrderedDaysOfWeekEnUs() {
        System.out.println("testGetOrderedDaysOfWeek");
        List<DayOfWeek> orderedDaysOfWeek = DayOfWeekUtils.getOrderedDaysOfWeek(new Locale("en", "US"));
        assertEquals(7, orderedDaysOfWeek.size());
        assertEquals(Arrays.asList(SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY), orderedDaysOfWeek);
    }
}
