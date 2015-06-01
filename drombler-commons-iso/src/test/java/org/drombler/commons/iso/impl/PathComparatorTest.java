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
public class PathComparatorTest {

    public PathComparatorTest() {
    }

    @Test
    public void testCompare() {
        System.out.println("compare");
        Path path1 = null;
        Path path2 = null;
        PathComparator instance = new PathComparator();
        int expResult = 0;
        int result = instance.compare(path1, path2);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

}
