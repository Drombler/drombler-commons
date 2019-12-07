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
package org.drombler.commons.iso.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Map;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author puce
 */
public class ISOFileSystemProviderTest {

    private final ISOFileSystemProvider testee = new ISOFileSystemProvider();
    private URI isoFileURI;

    @BeforeEach
    public void setUp() throws URISyntaxException {
        this.isoFileURI = URI.create("iso:" + ISOFileSystemProviderTest.class.getResource("/test.iso").toURI().
                toString());
    }

    @Test
    public void testGetScheme() {
        assertEquals("iso", testee.getScheme());
    }

    @Test
    public void testNewFileSystem() throws Exception {
        Map env = null;
        assertNotNull(testee.newFileSystem(isoFileURI, env));
    }

    @Test
    public void testNewFileSystemFileSystemAlreadyExistsException() throws Exception {
        Map env = null;
        assertNotNull(testee.newFileSystem(isoFileURI, env));
        assertThrows(FileSystemAlreadyExistsException.class, () ->  testee.newFileSystem(isoFileURI, env));
    }


    @Test
    @Disabled
    public void testGetFileSystem() {
        System.out.println("getFileSystem");
        URI uri = null;
        ISOFileSystemProvider instance = new ISOFileSystemProvider();
        FileSystem expResult = null;
        FileSystem result = instance.getFileSystem(uri);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testGetPath() {
        System.out.println("getPath");
        URI uri = null;
        ISOFileSystemProvider instance = new ISOFileSystemProvider();
        Path expResult = null;
        Path result = instance.getPath(uri);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testNewByteChannel() throws Exception {
        System.out.println("newByteChannel");
        Path path = null;
        Set<? extends OpenOption> options = null;
        FileAttribute[] attrs = null;
        ISOFileSystemProvider instance = new ISOFileSystemProvider();
        SeekableByteChannel expResult = null;
        SeekableByteChannel result = instance.newByteChannel(path, options, attrs);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testNewDirectoryStream() throws Exception {
        System.out.println("newDirectoryStream");
        Path dir = null;
        DirectoryStream.Filter<? super Path> filter = null;
        ISOFileSystemProvider instance = new ISOFileSystemProvider();
        DirectoryStream<Path> expResult = null;
        DirectoryStream<Path> result = instance.newDirectoryStream(dir, filter);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testCreateDirectory() throws Exception {
        System.out.println("createDirectory");
        Path dir = null;
        FileAttribute[] attrs = null;
        ISOFileSystemProvider instance = new ISOFileSystemProvider();
        instance.createDirectory(dir, attrs);
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testDelete() throws Exception {
        System.out.println("delete");
        Path path = null;
        ISOFileSystemProvider instance = new ISOFileSystemProvider();
        instance.delete(path);
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testCopy() throws Exception {
        System.out.println("copy");
        Path source = null;
        Path target = null;
        CopyOption[] options = null;
        ISOFileSystemProvider instance = new ISOFileSystemProvider();
        instance.copy(source, target, options);
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testMove() throws Exception {
        System.out.println("move");
        Path source = null;
        Path target = null;
        CopyOption[] options = null;
        ISOFileSystemProvider instance = new ISOFileSystemProvider();
        instance.move(source, target, options);
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testIsSameFile() throws Exception {
        System.out.println("isSameFile");
        Path path = null;
        Path path2 = null;
        ISOFileSystemProvider instance = new ISOFileSystemProvider();
        boolean expResult = false;
        boolean result = instance.isSameFile(path, path2);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testIsHidden() throws Exception {
        System.out.println("isHidden");
        Path path = null;
        ISOFileSystemProvider instance = new ISOFileSystemProvider();
        boolean expResult = false;
        boolean result = instance.isHidden(path);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testGetFileStore() throws Exception {
        System.out.println("getFileStore");
        Path path = null;
        ISOFileSystemProvider instance = new ISOFileSystemProvider();
        FileStore expResult = null;
        FileStore result = instance.getFileStore(path);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testCheckAccess() throws Exception {
        System.out.println("checkAccess");
        Path path = null;
        AccessMode[] modes = null;
        ISOFileSystemProvider instance = new ISOFileSystemProvider();
        instance.checkAccess(path, modes);
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testGetFileAttributeView() {
        System.out.println("getFileAttributeView");
        ISOFileSystemProvider instance = new ISOFileSystemProvider();
        Object expResult = null;
        Object result = instance.getFileAttributeView(null, null);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testReadAttributes_3args_1() throws Exception {
        System.out.println("readAttributes");
        ISOFileSystemProvider instance = new ISOFileSystemProvider();
        Object expResult = null;
        Object result = instance.readAttributes(null, "foo");
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testReadAttributes_3args_2() throws Exception {
        System.out.println("readAttributes");
        Path path = null;
        String attributes = "";
        LinkOption[] options = null;
        ISOFileSystemProvider instance = new ISOFileSystemProvider();
        Map<String, Object> expResult = null;
        Map<String, Object> result = instance.readAttributes(path, attributes, options);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Disabled
    public void testSetAttribute() throws Exception {
        System.out.println("setAttribute");
        Path path = null;
        String attribute = "";
        Object value = null;
        LinkOption[] options = null;
        ISOFileSystemProvider instance = new ISOFileSystemProvider();
        instance.setAttribute(path, attribute, value, options);
        fail("The test case is a prototype.");
    }

}
