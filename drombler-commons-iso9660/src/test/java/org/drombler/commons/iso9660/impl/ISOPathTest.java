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
package org.drombler.commons.iso9660.impl;

import java.io.File;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author puce
 */
@Disabled
public class ISOPathTest {

    @Test
    public void testGetFileSystem() {
        System.out.println("getFileSystem");
        ISOPath instance = null;
        FileSystem expResult = null;
        FileSystem result = instance.getFileSystem();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testIsAbsolute() {
        System.out.println("isAbsolute");
        ISOPath instance = null;
        boolean expResult = false;
        boolean result = instance.isAbsolute();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetRoot() {
        System.out.println("getRoot");
        ISOPath instance = null;
        Path expResult = null;
        Path result = instance.getRoot();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetFileName() {
        System.out.println("getFileName");
        ISOPath instance = null;
        ISOPath expResult = null;
        ISOPath result = instance.getFileName();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetParent() {
        System.out.println("getParent");
        ISOPath instance = null;
        ISOPath expResult = null;
        ISOPath result = instance.getParent();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetNameCount() {
        System.out.println("getNameCount");
        ISOPath instance = null;
        int expResult = 0;
        int result = instance.getNameCount();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetName() {
        System.out.println("getName");
        int index = 0;
        ISOPath instance = null;
        ISOPath expResult = null;
        ISOPath result = instance.getName(index);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSubpath() {
        System.out.println("subpath");
        int beginIndex = 0;
        int endIndex = 0;
        ISOPath instance = null;
        Path expResult = null;
        Path result = instance.subpath(beginIndex, endIndex);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testStartsWith_Path() {
        System.out.println("startsWith");
        Path other = null;
        ISOPath instance = null;
        boolean expResult = false;
        boolean result = instance.startsWith(other);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testStartsWith_String() {
        System.out.println("startsWith");
        String other = "";
        ISOPath instance = null;
        boolean expResult = false;
        boolean result = instance.startsWith(other);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testEndsWith_Path() {
        System.out.println("endsWith");
        Path other = null;
        ISOPath instance = null;
        boolean expResult = false;
        boolean result = instance.endsWith(other);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testEndsWith_String() {
        System.out.println("endsWith");
        String other = "";
        ISOPath instance = null;
        boolean expResult = false;
        boolean result = instance.endsWith(other);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testNormalize() {
        System.out.println("normalize");
        ISOPath instance = null;
        Path expResult = null;
        Path result = instance.normalize();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testResolve_Path() {
        System.out.println("resolve");
        Path other = null;
        ISOPath instance = null;
        Path expResult = null;
        Path result = instance.resolve(other);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testResolve_String() {
        System.out.println("resolve");
        String other = "";
        ISOPath instance = null;
        Path expResult = null;
        Path result = instance.resolve(other);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testResolveSibling_Path() {
        System.out.println("resolveSibling");
        Path other = null;
        ISOPath instance = null;
        Path expResult = null;
        Path result = instance.resolveSibling(other);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testResolveSibling_String() {
        System.out.println("resolveSibling");
        String other = "";
        ISOPath instance = null;
        Path expResult = null;
        Path result = instance.resolveSibling(other);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testRelativize() {
        System.out.println("relativize");
        Path other = null;
        ISOPath instance = null;
        Path expResult = null;
        Path result = instance.relativize(other);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testToUri() {
        System.out.println("toUri");
        ISOPath instance = null;
        URI expResult = null;
        URI result = instance.toUri();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testToAbsolutePath() {
        System.out.println("toAbsolutePath");
        ISOPath instance = null;
        Path expResult = null;
        Path result = instance.toAbsolutePath();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testToRealPath() throws Exception {
        System.out.println("toRealPath");
        LinkOption[] options = null;
        ISOPath instance = null;
        Path expResult = null;
        Path result = instance.toRealPath(options);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testToFile() {
        System.out.println("toFile");
        ISOPath instance = null;
        File expResult = null;
        File result = instance.toFile();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testRegister_3args() throws Exception {
        System.out.println("register");
        WatchService watcher = null;
        WatchEvent.Kind[] events = null;
        WatchEvent.Modifier[] modifiers = null;
        ISOPath instance = null;
        WatchKey expResult = null;
        WatchKey result = instance.register(watcher, events, modifiers);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testRegister_WatchService_WatchEventKindArr() throws Exception {
        System.out.println("register");
        WatchService watcher = null;
        WatchEvent.Kind[] events = null;
        ISOPath instance = null;
        WatchKey expResult = null;
        WatchKey result = instance.register(watcher, events);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testIterator() {
        System.out.println("iterator");
        ISOPath instance = null;
        Iterator<Path> expResult = null;
        Iterator<Path> result = instance.iterator();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        Path other = null;
        ISOPath instance = null;
        int expResult = 0;
        int result = instance.compareTo(other);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        ISOPath instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        ISOPath instance = null;
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        ISOPath instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

}
