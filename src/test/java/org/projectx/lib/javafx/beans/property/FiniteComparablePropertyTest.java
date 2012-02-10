/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.beans.property;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author puce
 */
public class FiniteComparablePropertyTest {

    private FiniteComparableProperty<Integer> test;

    public FiniteComparablePropertyTest() {
    }

    @Before
    public void setUp() {
        test = new FiniteComparableProperty<>();
    }

    @After
    public void tearDown() {
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
