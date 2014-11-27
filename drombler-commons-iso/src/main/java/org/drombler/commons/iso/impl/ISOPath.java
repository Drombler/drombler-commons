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
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

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
    // TODO: make singleton
    private final PathComparator pathComparator = new PathComparator();

    public ISOPath(ISOFileSystem fileSystem, String fileName, boolean root, ISOPath... parentPaths) {
        this.fileSystem = fileSystem;
        this.fileName = Objects.requireNonNull(fileName);
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
    public ISOPath getParent() {
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
        return resolve(this, other);
    }

    private Path resolve(ISOPath path1, Path path2) {
        if (path2.isAbsolute()) {
            return path2;
        }
        if (isEmpty(path2)) {
            return path1;
        }
        ISOPath path = path1;
        for (int i = 0; i < path2.getNameCount(); i++) {
            path = new ISOPath(path1.fileSystem, path2.getName(i).toString(), false, createParentPaths(path));
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
            throw new InvalidPathException(String.valueOf(other), "other must not be null!");
        }
        if (other.equals("")) {
            return fileSystem.getEmptyPath();
        }
        ISOPath path = null;
        final boolean absolute = isAbsolute(other);
        if (absolute) {
            path = fileSystem.getRootDirectory();
        }
        String[] pathNames = other.split(fileSystem.getSeparator(), -1);
        for (int i = absolute ? 1 : 0; i < pathNames.length; i++) {
            if (pathNames[i].equals("")) {
                throw new InvalidPathException(other, "other must not contain empty path names!", i);
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
        if (parentPaths.length == 0) {
            return other;
        } else {
            return resolve(getParent(), other);
        }
    }

    @Override
    public Path resolveSibling(String other) {
        return resolveSibling(toPath(other));
    }

    @Override
    public Path relativize(Path other) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public URI toUri() {
        try {
            return new URI(fileSystem.provider().getScheme(), fileSystem.getFileSystemPath().toUri().toString(),
                    toAbsolutePath().toString());
        } catch (URISyntaxException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public Path toAbsolutePath() {
        if (isAbsolute()) {
            return this;
        } else {
            return resolve(fileSystem.getDefaultDirectory(), this);
        }
    }

    @Override
    public Path toRealPath(LinkOption... options) throws IOException {
        Path absolutePath = toAbsolutePath();
        Path realPath = fileSystem.getRootDirectory();
        for (Path pathName : absolutePath) {
            if (pathName.equals(fileSystem.getCurrentDirectory())) {
                continue;
            } else if (pathName.equals(fileSystem.getParentDirectory())) {
                if (realPath.getNameCount() > 0) {
                    realPath = realPath.getParent();
                } else {
                    throw new IOException("Path does not exist! Invalid path: " + toString());
                }
            } else {
                realPath = realPath.resolve(pathName.toString().toUpperCase());
            }
        }
        // spec requires to return the real path of an existing file or throw a IOException, if the file does not exist.
        fileSystem.provider().checkAccess(realPath);
        return realPath;
    }

    @Override
    public File toFile() {
        throw new UnsupportedOperationException("This Path is not associated with the default provider!");
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
        return new PathIterator(this);
    }

    @Override
    public int compareTo(Path other) {
        return pathComparator.compare(this, other);
    }

    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ISOPath)) {
            return false;
        }

        ISOPath other = (ISOPath) obj;

        boolean equals = Objects.equals(fileSystem, other.fileSystem)
                && root == other.root
                && equalsFileName(fileName, other.fileName)
                && Objects.equals(parentPaths.length, other.parentPaths.length);
        if (equals) {
            for (int i = 0; i < parentPaths.length; i++) {
                if (!equalsFileName(parentPaths[i].fileName, other.parentPaths[i].fileName)) {
                    equals = false;
                    break;
                }
            }
        }
        return equals;
    }

    private boolean equalsFileName(String fileName, String otherFileName) {
        return fileName.equalsIgnoreCase(otherFileName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (parentPaths.length > 0) {
            int index = 0;
            if (parentPaths[index].equals(fileSystem.getRootDirectory())) {
                sb.append(parentPaths[index]);
                index++;
            }
            while (index < parentPaths.length) {
                sb.append(parentPaths[index]);
                sb.append(fileSystem.getSeparator());
                index++;
            }
        }
        sb.append(fileName);
        return sb.toString();
    }

}
