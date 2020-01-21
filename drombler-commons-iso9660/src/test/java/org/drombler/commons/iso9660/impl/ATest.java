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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import org.junit.jupiter.api.Test;

/**
 *
 * @author puce
 */
public class ATest {

    @Test
    public void test() throws URISyntaxException, IOException {
        URI isoURI = new URI("iso:" + ATest.class.getResource("/test.iso").toURI().toString());
        ISOFileSystemProvider isoFileSystemProvider = new ISOFileSystemProvider();
        try (FileSystem isoFileSystem = isoFileSystemProvider.newFileSystem(isoURI, null)) {
isoFileSystem.getRootDirectories().forEach(System.out::println);
        }
    }
}
