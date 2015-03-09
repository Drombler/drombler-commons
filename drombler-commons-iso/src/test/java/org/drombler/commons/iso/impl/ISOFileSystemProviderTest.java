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
import java.nio.file.FileSystemAlreadyExistsException;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author puce
 */
public class ISOFileSystemProviderTest {

    private ISOFileSystemProvider testee = new ISOFileSystemProvider();
    private URI isoFileURI;

    @Before
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

    @Test(expected = FileSystemAlreadyExistsException.class)
    public void testNewFileSystemFileSystemAlreadyExistsException() throws Exception {
        Map env = null;
        assertNotNull(testee.newFileSystem(isoFileURI, env));
        testee.newFileSystem(isoFileURI, env);
    }

}
