/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.time.calendar;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.time.calendar.DayOfWeek;
import static javax.time.calendar.DayOfWeek.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author puce
 */
public class DayOfWeekUtilsTest {

    public DayOfWeekUtilsTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetOrderedDaysOfWeek() {
        System.out.println("testGetOrderedDaysOfWeek");
        List<DayOfWeek> orderedDaysOfWeek = DayOfWeekUtils.getOrderedDaysOfWeek(new Locale("de", "CH"));
        assertEquals(7, orderedDaysOfWeek.size());
        assertEquals(Arrays.asList(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY), orderedDaysOfWeek);
    }
}
