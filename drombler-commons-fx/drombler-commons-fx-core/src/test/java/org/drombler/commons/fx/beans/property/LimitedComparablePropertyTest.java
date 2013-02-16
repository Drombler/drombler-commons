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
package org.drombler.commons.fx.beans.property;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author puce
 */
public class LimitedComparablePropertyTest {

    private LimitedComparableProperty<Integer> test;

    public LimitedComparablePropertyTest() {
    }

    @Before
    public void setUp() {
        test = new LimitedComparableProperty<>();
    }


    @Test
    public void testSetWithMinMax() {
        test.setMin(0);
        test.setMax(10);
        test.set(1);
        assertEquals(1, test.get().intValue());
        test.set(11);
        assertEquals(test.getMax().intValue(), test.get().intValue());
        test.set(-1);
        assertEquals(test.getMin().intValue(), test.get().intValue());
        test.set(null);
        assertNull(test.get());
    }

    @Test
    public void testSetValueWithMinMax() {
        test.setMin(0);
        test.setMax(10);
        test.setValue(1);
        assertEquals(1, test.get().intValue());
        test.setValue(11);
        assertEquals(test.getMax().intValue(), test.get().intValue());
        test.setValue(-1);
        assertEquals(test.getMin().intValue(), test.get().intValue());
        test.setValue(null);
        assertNull(test.get());
    }

    @Test
    public void testSetWithMin() {
        test.setMin(0);
        test.set(1);
        assertEquals(1, test.get().intValue());
        test.set(11);
        assertEquals(11, test.get().intValue());
        test.set(-1);
        assertEquals(test.getMin().intValue(), test.get().intValue());
        test.set(null);
        assertNull(test.get());
    }

    @Test
    public void testSetValueWithMin() {
        test.setMin(0);
        test.setValue(1);
        assertEquals(1, test.get().intValue());
        test.setValue(11);
        assertEquals(11, test.get().intValue());
        test.setValue(-1);
        assertEquals(test.getMin().intValue(), test.get().intValue());
        test.setValue(null);
        assertNull(test.get());
    }

    @Test
    public void testSetWithMax() {
        test.setMax(10);
        test.set(1);
        assertEquals(1, test.get().intValue());
        test.set(11);
        assertEquals(test.getMax().intValue(), test.get().intValue());
        test.set(-1);
        assertEquals(-1, test.get().intValue());
        test.set(null);
        assertNull(test.get());
    }

    @Test
    public void testSetValueWithMax() {
        test.setMax(10);
        test.setValue(1);
        assertEquals(1, test.get().intValue());
        test.setValue(11);
        assertEquals(test.getMax().intValue(), test.get().intValue());
        test.setValue(-1);
        assertEquals(-1, test.get().intValue());
        test.setValue(null);
        assertNull(test.get());
    }

    @Test
    public void testSet() {
        test.set(1);
        assertEquals(1, test.get().intValue());
        test.set(11);
        assertEquals(11, test.get().intValue());
        test.set(-1);
        assertEquals(-1, test.get().intValue());
        test.set(null);
        assertNull(test.get());
    }

    @Test
    public void testSetValue() {
        test.setValue(1);
        assertEquals(1, test.get().intValue());
        test.setValue(11);
        assertEquals(11, test.get().intValue());
        test.setValue(-1);
        assertEquals(-1, test.get().intValue());
        test.setValue(null);
        assertNull(test.get());
    }

    @Test
    public void testSetMin() {
        test.set(1);
        assertEquals(1, test.get().intValue());

        test.setMin(0);
        assertEquals(0, test.getMin().intValue());
        assertEquals(1, test.get().intValue());

        test.setMin(2);
        assertEquals(2, test.getMin().intValue());
        assertEquals(2, test.get().intValue());


        test.setMin(null);
        assertNull(test.getMin());
        assertEquals(2, test.get().intValue());

    }

    @Test
    public void testSetMinWithMax() {
        test.set(1);
        test.setMax(10);
        test.setMin(11);
        assertEquals(10, test.getMin().intValue());
        assertEquals(10, test.get().intValue());
    }

    @Test
    public void testSetMax() {
        test.set(5);
        assertEquals(5, test.get().intValue());

        test.setMax(10);
        assertEquals(10, test.getMax().intValue());
        assertEquals(5, test.get().intValue());

        test.setMax(4);
        assertEquals(4, test.getMax().intValue());
        assertEquals(4, test.get().intValue());


        test.setMax(null);
        assertNull(test.getMax());
        assertEquals(4, test.get().intValue());

    }

    @Test
    public void testSetMaxWithMin() {
        test.set(1);
        test.setMin(11);
        test.setMax(10);
        assertEquals(11, test.getMax().intValue());
        assertEquals(11, test.get().intValue());
    }
}
