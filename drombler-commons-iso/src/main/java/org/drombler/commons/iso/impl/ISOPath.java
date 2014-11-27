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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author puce
 */
public class ISOPath implements Path {

    private final ISOFileSystem fileSystem;
    private final String fileName;
    private final boolean root;
    private final ISOPath[] parentPaths;
    private final ISOPath fileNamePath;

    public ISOPath(ISOFileSystem fileSystem, String fileName, boolean root, ISOPath... parentPaths) {
        this.fileSystem = fileSystem;
        this.fileName = fileName;
        this.root = root;
        this.parentPaths = parentPaths;
        if (parentPaths.length == 0) {
            this.fileNamePath = this;
        } else {
            this.fileNamePath = new ISOPath(fileSystem, fileName, false);
        }
    }

    @Override
    public FileSystem getFileSystem() {
        return fileSystem;
    }

    @Override
    public boolean isAbsolute() {
        return root || (parentPaths.length > 0 && parentPaths[0].root);
    }

    @Override
    public Path getRoot() {
        if (isAbsolute()) {
            return parentPaths[0];
        } else {
            return null;
        }
    }

    @Override
    public Path getFileName() {
        return fileNamePath;
    }

    @Override
    public Path getParent() {
        if (parentPaths.length > 0) {
            return parentPaths[parentPaths.length - 1];
        } else {
            return null;
        }
    }

    @Override
    public int getNameCount() {
        if (isAbsolute()) {
            return parentPaths.length;
        } else {
            return parentPaths.length + 1;
        }
    }

    @Override
    public Path getName(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("index must not be negative!");
        }
        if (index >= getNameCount()) {
            throw new IllegalArgumentException(
                    "index must not be greater or equal the name count! Name count: " + getNameCount());
        }
        if (index == getNameCount() - 1) {
            return getFileName();
        } else {
            if (isAbsolute()) {
                return parentPaths[index + 1].getFileName();
            } else {
                return parentPaths[index].getFileName();
            }
        }
    }

    @Override
    public Path subpath(int beginIndex, int endIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean startsWith(Path other) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean startsWith(String other) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean endsWith(Path other) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean endsWith(String other) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Path normalize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Path resolve(Path other) {
        if (other.isAbsolute()) {
            return other;
        }
        if (isEmpty(other)) {
            return this;
        }
        ISOPath path = this;
        for (int i = 0; i < other.getNameCount(); i++) {
            path = new ISOPath(fileSystem, other.getName(i).toString(), false, createParentPaths(path));
        }
        return path;
    }

    private boolean isEmpty(Path path) {
        return path.getNameCount() == 1 && path.getFileName().toString().equals("");
    }

    private ISOPath[] createParentPaths(ISOPath path) {
        if (path == null) {
            return new ISOPath[0];
        } else {
            ISOPath[] result = Arrays.copyOf(path.parentPaths, path.parentPaths.length + 1);
            result[result.length - 1] = path;
            return result;
        }
    }

    @Override
    public Path resolve(String other) {
        return resolve(toPath(other));
    }

    private Path toPath(String other) {
        if (other == null) {
            throw new IllegalArgumentException("other must not be null!");
        }
        if (other.equals("")) {
            return fileSystem.getEmptyPath();
        }
        ISOPath path = null;
        final boolean absolute = isAbsolute(other);
        if (absolute) {
            path = fileSystem.getRootDirectory();
        }
        String[] pathNames = other.split(ISOFileSystem.SEPARATOR, -1);
        for (int i = absolute ? 1 : 0; i < pathNames.length; i++) {
            if (pathNames[i].equals("")) {
                throw new IllegalArgumentException("other must not contain empty path names! other: " + other);
            }
            path = new ISOPath(fileSystem, pathNames[i], false, createParentPaths(path));
        }
        return path;
    }

    private boolean isAbsolute(String other) {
        return other.startsWith(fileSystem.getRootDirectory().toString());
    }

    @Override
    public Path resolveSibling(Path other) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Path resolveSibling(String other) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Path relativize(Path other) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public URI toUri() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Path toAbsolutePath() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Path toRealPath(LinkOption... options) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public File toFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WatchKey register(WatchService watcher, WatchEvent.Kind<?>[] events, WatchEvent.Modifier... modifiers)
            throws IOException {
        throw new UnsupportedOperationException("This is a read-only file system! There's nothing to watch!");
    }

    @Override
    public WatchKey register(WatchService watcher, WatchEvent.Kind<?>... events) throws IOException {
        throw new UnsupportedOperationException("This is a read-only file system! There's nothing to watch!");
    }

    @Override
    public Iterator<Path> iterator() {
        return new Iterator<Path>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < getNameCount();
            }

            @Override
            public Path next() {
                return getName(index++);
            }
        };
    }

    @Override
    public int compareTo(Path other) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
