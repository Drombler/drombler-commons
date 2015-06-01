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

import java.nio.file.Path;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author puce
 */
@Ignore
public class ISOFileTypeDetectorTest {

    public ISOFileTypeDetectorTest() {
    }

    @Test
    public void testProbeContentType() throws Exception {
        System.out.println("probeContentType");
        Path path = null;
        ISOFileTypeDetector instance = new ISOFileTypeDetector();
        String expResult = "";
        String result = instance.probeContentType(path);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

}
