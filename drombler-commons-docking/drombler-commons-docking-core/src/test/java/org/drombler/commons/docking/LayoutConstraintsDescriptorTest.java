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
package org.drombler.commons.docking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


/**
 *
 * @author puce
 */
public class LayoutConstraintsDescriptorTest {


    @Test
    @Disabled
    public void testGetLayoutConstraints() {
        System.out.println("getLayoutConstraints");
        double prefWidth = 0.0;
        double prefHeight = 0.0;
        LayoutConstraintsDescriptor expResult = null;
        LayoutConstraintsDescriptor result = LayoutConstraintsDescriptor.getLayoutConstraints(prefWidth, prefHeight);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testPrefWidth() {
        System.out.println("prefWidth");
        double prefWidth = 0.0;
        LayoutConstraintsDescriptor expResult = null;
        LayoutConstraintsDescriptor result = LayoutConstraintsDescriptor.prefWidth(prefWidth);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testPrefHeight() {
        System.out.println("prefHeight");
        double prefHeight = 0.0;
        LayoutConstraintsDescriptor expResult = null;
        LayoutConstraintsDescriptor result = LayoutConstraintsDescriptor.prefHeight(prefHeight);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testFlexible() {
        System.out.println("flexible");
        LayoutConstraintsDescriptor expResult = null;
        LayoutConstraintsDescriptor result = LayoutConstraintsDescriptor.flexible();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testGetPrefWidth() {
        System.out.println("getPrefWidth");
        LayoutConstraintsDescriptor instance = null;
        double expResult = 0.0;
        double result = instance.getPrefWidth();
        assertEquals(expResult, result, 0.0);
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testGetPrefHeight() {
        System.out.println("getPrefHeight");
        LayoutConstraintsDescriptor instance = null;
        double expResult = 0.0;
        double result = instance.getPrefHeight();
        assertEquals(expResult, result, 0.0);
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testIsFlexible() {
        System.out.println("isFlexible");
        double size = 0.0;
        boolean expResult = false;
        boolean result = LayoutConstraintsDescriptor.isFlexible(size);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testIsPreferred() {
        System.out.println("isPreferred");
        double size = 0.0;
        boolean expResult = false;
        boolean result = LayoutConstraintsDescriptor.isPreferred(size);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testToString() {
        System.out.println("toString");
        LayoutConstraintsDescriptor instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testHashCode() {
        System.out.println("hashCode");
        LayoutConstraintsDescriptor instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        LayoutConstraintsDescriptor instance = null;
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

}
