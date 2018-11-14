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
import java.util.Iterator;

/**
<<<<<<< OURS
 * TODO: Move to SoftSmity nio.file.spi?
=======
 * TODO: Move to SoftSmity?
>>>>>>> THEIRS
 *
 * @author puce
 */
public class PathIterator implements Iterator<Path> {

    private final Path path;
    private final int nameCount;

    public PathIterator(Path path) {
        this.path = path;
        this.nameCount = path.getNameCount();
    }
    private int index = 0;

    @Override
    public boolean hasNext() {
        return index < nameCount;
    }

    @Override
    public Path next() {
        return path.getName(index++);
    }
}
