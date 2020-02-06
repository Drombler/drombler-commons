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

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Iterator;

/**
 * // TODO: Move to SoftSmithy?
 *
 * @author puce
 */
public class PathComparator implements Comparator<Path> {

    @Override
    public int compare(Path path1, Path path2) {
        if (!path1.getFileSystem().provider().equals(path2.getFileSystem().provider())) {
            throw new ClassCastException("other has a different file system provider!");
        }
        if (path1.isAbsolute() && !path2.isAbsolute()) {
            return -1;
        } else if (!path1.isAbsolute() && path2.isAbsolute()) {
            return 1;
        } else {
            return compare(path1.iterator(), path2.iterator());
        }
    }

    private int compare(Iterator<Path> path1Iterator, Iterator<Path> path2Iterator) {
        if (!path1Iterator.hasNext() && path2Iterator.hasNext()) {
            return -1;
        } else if (path1Iterator.hasNext() && !path2Iterator.hasNext()) {
            return 1;
        } else if (!path1Iterator.hasNext() && !path2Iterator.hasNext()) {
            return 0;
        } else {
            Path path1Name = path1Iterator.next();
            Path path2Name = path2Iterator.next();
            // ignore case since ISO only supports uppercase names
            int compareTo = path1Name.toString().compareToIgnoreCase(path2Name.toString());
            if (compareTo != 0) {
                return compareTo;
            } else {
                // recursion
                return compare(path1Iterator, path2Iterator);
            }
        }
    }
}
