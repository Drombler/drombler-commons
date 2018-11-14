
package org.drombler.commons.iso.impl;

import java.nio.file.FileStore;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.WatchService;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


/**
 *
 * @author puce
 */
@Disabled
public class ISOFileSystemTest {


    @Test
    public void testProvider() {
        System.out.println("provider");
        ISOFileSystem instance = null;
        FileSystemProvider expResult = null;
        FileSystemProvider result = instance.provider();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testClose() throws Exception {
        System.out.println("close");
        ISOFileSystem instance = null;
        instance.close();
        fail("The test case is a prototype.");
    }

    @Test
    public void testIsOpen() {
        System.out.println("isOpen");
        ISOFileSystem instance = null;
        boolean expResult = false;
        boolean result = instance.isOpen();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testIsReadOnly() {
        System.out.println("isReadOnly");
        ISOFileSystem instance = null;
        boolean expResult = false;
        boolean result = instance.isReadOnly();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetSeparator() {
        System.out.println("getSeparator");
        ISOFileSystem instance = null;
        String expResult = "";
        String result = instance.getSeparator();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetRootDirectories() {
        System.out.println("getRootDirectories");
        ISOFileSystem instance = null;
        Iterable<Path> expResult = null;
        Iterable<Path> result = instance.getRootDirectories();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetFileStores() {
        System.out.println("getFileStores");
        ISOFileSystem instance = null;
        Iterable<FileStore> expResult = null;
        Iterable<FileStore> result = instance.getFileStores();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSupportedFileAttributeViews() {
        System.out.println("supportedFileAttributeViews");
        ISOFileSystem instance = null;
        Set<String> expResult = null;
        Set<String> result = instance.supportedFileAttributeViews();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetPath() {
        System.out.println("getPath");
        String first = "";
        String[] more = null;
        ISOFileSystem instance = null;
        Path expResult = null;
        Path result = instance.getPath(first, more);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetPathMatcher() {
        System.out.println("getPathMatcher");
        String syntaxAndPattern = "";
        ISOFileSystem instance = null;
        PathMatcher expResult = null;
        PathMatcher result = instance.getPathMatcher(syntaxAndPattern);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetUserPrincipalLookupService() {
        System.out.println("getUserPrincipalLookupService");
        ISOFileSystem instance = null;
        UserPrincipalLookupService expResult = null;
        UserPrincipalLookupService result = instance.getUserPrincipalLookupService();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testNewWatchService() throws Exception {
        System.out.println("newWatchService");
        ISOFileSystem instance = null;
        WatchService expResult = null;
        WatchService result = instance.newWatchService();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetRootDirectory() {
        System.out.println("getRootDirectory");
        ISOFileSystem instance = null;
        ISOPath expResult = null;
        ISOPath result = instance.getRootDirectory();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetEmptyPath() {
        System.out.println("getEmptyPath");
        ISOFileSystem instance = null;
        Path expResult = null;
        Path result = instance.getEmptyPath();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetDefaultDirectory() {
        System.out.println("getDefaultDirectory");
        ISOFileSystem instance = null;
        ISOPath expResult = null;
        ISOPath result = instance.getDefaultDirectory();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetFileSystemPath() {
        System.out.println("getFileSystemPath");
        ISOFileSystem instance = null;
        Path expResult = null;
        Path result = instance.getFileSystemPath();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetCurrentDirectory() {
        System.out.println("getCurrentDirectory");
        ISOFileSystem instance = null;
        ISOPath expResult = null;
        ISOPath result = instance.getCurrentDirectory();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetParentDirectory() {
        System.out.println("getParentDirectory");
        ISOFileSystem instance = null;
        ISOPath expResult = null;
        ISOPath result = instance.getParentDirectory();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

}
