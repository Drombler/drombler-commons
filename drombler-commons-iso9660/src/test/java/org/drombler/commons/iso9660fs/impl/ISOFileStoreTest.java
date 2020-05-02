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
 * Copyright 2014 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.iso9660fs.impl;

import org.drombler.commons.iso9660fs.impl.ISOFileStore;
import java.nio.file.attribute.FileAttributeView;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


/**
 *
 * @author puce
 */
@Disabled
public class ISOFileStoreTest {

    @Test
    public void testName() {
        System.out.println("name");
        ISOFileStore instance = null;
        String expResult = "";
        String result = instance.name();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testType() {
        System.out.println("type");
        ISOFileStore instance = null;
        String expResult = "";
        String result = instance.type();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testIsReadOnly() {
        System.out.println("isReadOnly");
        ISOFileStore instance = null;
        boolean expResult = false;
        boolean result = instance.isReadOnly();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetTotalSpace() throws Exception {
        System.out.println("getTotalSpace");
        ISOFileStore instance = null;
        long expResult = 0L;
        long result = instance.getTotalSpace();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetUsableSpace() {
        System.out.println("getUsableSpace");
        ISOFileStore instance = null;
        long expResult = 0L;
        long result = instance.getUsableSpace();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetUnallocatedSpace() {
        System.out.println("getUnallocatedSpace");
        ISOFileStore instance = null;
        long expResult = 0L;
        long result = instance.getUnallocatedSpace();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSupportsFileAttributeView_Class() {
        System.out.println("supportsFileAttributeView");
        Class<? extends FileAttributeView> type = null;
        ISOFileStore instance = null;
        boolean expResult = false;
        boolean result = instance.supportsFileAttributeView(type);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSupportsFileAttributeView_String() {
        System.out.println("supportsFileAttributeView");
        String name = "";
        ISOFileStore instance = null;
        boolean expResult = false;
        boolean result = instance.supportsFileAttributeView(name);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetFileStoreAttributeView() {
        System.out.println("getFileStoreAttributeView");
        ISOFileStore instance = null;
        Object expResult = null;
        Object result = instance.getFileStoreAttributeView(null);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetAttribute() throws Exception {
        System.out.println("getAttribute");
        String attribute = "";
        ISOFileStore instance = null;
        Object expResult = null;
        Object result = instance.getAttribute(attribute);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

}
