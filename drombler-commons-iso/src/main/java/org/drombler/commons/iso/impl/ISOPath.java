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
    public ISOPath getFileName() {
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
    public ISOPath getName(int index) {
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
        if (beginIndex < 0) {
            throw new IllegalArgumentException("beginIndex must not be negative!");
        }
        if (beginIndex >= getNameCount()) {
            throw new IllegalArgumentException("beginIndex must not be greater than or equal to the number of elements!");
        }
        if (endIndex <= beginIndex) {
            throw new IllegalArgumentException("endIndex must not be less than or equal to beginIndex!");
        }
        if (endIndex > getNameCount()) {
            throw new IllegalArgumentException("endIndex must not be greater than the number of elements!");
        }
        Path subpath = getName(beginIndex);
        for (int index = beginIndex + 1; index < endIndex; index++) {
            subpath.resolve(getName(index));
        }
        return subpath;
    }

    @Override
    public boolean startsWith(Path other) {
        if (!fileSystem.equals(other.getFileSystem())) {
            return false;
        }

        if (getNameCount() < other.getNameCount()) {
            return false;
        }

        if (!isAbsolute() && other.isAbsolute()) {
            return false;
        }

        if (isAbsolute() && !other.isAbsolute()) {
            // TODO: correct?
            return false;
        }

        for (int index = 0; index < other.getNameCount(); index++) {
            if (!equalsFileName(getName(index).toString(), other.getName(index).toString())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean startsWith(String other) {
        return startsWith(valueOf(other));
    }

    @Override
    public boolean endsWith(Path other) {
        if (!fileSystem.equals(other.getFileSystem())) {
            return false;
        }

        if (getNameCount() < other.getNameCount()) {
            return false;
        }

        if (!isAbsolute() && other.isAbsolute()) {
            return false;
        }

        if (isAbsolute() && other.isAbsolute()) {
            return equals(other);
        }

        for (int index = other.getNameCount() - 1; index >= 0; index--) {
            if (!equalsFileName(getName(index).toString(), other.getName(index).toString())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean endsWith(String other) {
        if (other.endsWith(fileSystem.getSeparator())) {
            return endsWith(valueOf(other.substring(0, other.length() - fileSystem.getSeparator().length())));
        } else {
            return endsWith(valueOf(other));
        }
    }

    @Override
    public Path normalize() {
        if (!containsReduntantElementNames()) {
            return this;
        }
        ISOPath normalizedPath = isAbsolute() ? fileSystem.getRootDirectory() : null;
        for (int index = 0; index < getNameCount(); index++) {
            final ISOPath pathName = getName(index);
            if (normalizedPath == null) {
                // pathName could ne "." or ".."
                normalizedPath = pathName;
            } else if (!pathName.equals(fileSystem.getCurrentDirectory())) {
                if (pathName.equals(fileSystem.getParentDirectory())) {
                    if (!normalizedPath.equals(fileSystem.getRootDirectory())
                            && !normalizedPath.getFileName().equals(fileSystem.getParentDirectory())) {
                        // normalizedPath.getParent() could be null
                        normalizedPath = normalizedPath.getParent();
                    } else {
                        normalizedPath = normalize(normalizedPath, pathName);
                    }
                } else {
                    normalizedPath = normalize(normalizedPath, pathName);
                }
            }
        }
        if (normalizedPath != null) {
            if (normalizedPath.isAbsolute() && containsOnlyReduntantElementNames(normalizedPath)) {
                return fileSystem.getEmptyPath();
            } else {
                return normalizedPath;
            }
        } else {
            return fileSystem.getEmptyPath();
        }
    }

    private ISOPath normalize(ISOPath normalizedPath, final ISOPath pathName) {
        if (normalizedPath.equals(fileSystem.getCurrentDirectory())) {
            return pathName;
        } else {
            return createResolvedPath(normalizedPath, pathName);
        }
    }

    private boolean containsOnlyReduntantElementNames(ISOPath path) {
        for (int index = 0; index < path.getNameCount(); index++) {
            final Path pathName = path.getName(index);
            if (!pathName.equals(fileSystem.getCurrentDirectory()) && !pathName.equals(fileSystem.getParentDirectory())) {
                return false;
            }
        }
        return true;
    }

    private boolean containsReduntantElementNames() {
        for (int index = 0; index < getNameCount(); index++) {
            final ISOPath pathName = getName(index);
            if (pathName.equals(fileSystem.getCurrentDirectory()) || pathName.equals(fileSystem.getParentDirectory())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Path resolve(Path other) {
        return resolve(this, other);
    }

    private Path resolve(ISOPath path1, Path path2) {
        if (path2.isAbsolute()) {
            return path2;
        }
        if (path2.equals(fileSystem.getEmptyPath())) {
            return path1;
        }
        return createResolvedPath(path1, path2);
    }

    private ISOPath createResolvedPath(ISOPath path1, Path path2) {
        ISOPath path = path1;
        for (int i = 0; i < path2.getNameCount(); i++) {
            path = new ISOPath(path1.fileSystem, path2.getName(i).toString(), false, createParentPaths(path));
        }
        return path;
    }

    public static ISOPath[] createParentPaths(ISOPath path) {
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
        return resolve(valueOf(other));
    }

    private Path valueOf(String pathString) {
        return valueOf(pathString, fileSystem);
    }

    public static Path valueOf(String pathString, ISOFileSystem fileSystem) {
        if (pathString == null) {
            throw new InvalidPathException(String.valueOf(pathString), "other must not be null!");
        }
        if (pathString.equals(ISOFileSystem.EMPTY_PATH_STRING)) {
            return fileSystem.getEmptyPath();
        }
        if (pathString.equals(ISOFileSystem.CURRENT_PATH_STRING)) {
            return fileSystem.getCurrentDirectory();
        }
        if (pathString.equals(ISOFileSystem.PARENT_PATH_STRING)) {
            return fileSystem.getParentDirectory();
        }
        ISOPath path = null;
        final boolean absolute = isAbsolute(pathString, fileSystem);
        if (absolute) {
            path = fileSystem.getRootDirectory();
        }
        String[] pathNames = pathString.split(fileSystem.getSeparator(), -1);
        for (int i = absolute ? 1 : 0; i < pathNames.length; i++) {
            if (pathNames[i].equals(ISOFileSystem.EMPTY_PATH_STRING)) {
                throw new InvalidPathException(pathString, "other must not contain empty path names!", i);
            }
            path = new ISOPath(fileSystem, pathNames[i], false, createParentPaths(path));
        }
        return path;
    }

    private static boolean isAbsolute(String pathString, ISOFileSystem fileSystem) {
        return pathString.startsWith(fileSystem.getRootDirectory().toString());
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
        return resolveSibling(valueOf(other));
    }

    @Override
    public Path relativize(Path other) {
        if (isAbsolute() && !other.isAbsolute()) {
            throw new IllegalArgumentException("other must be absolute as well!");
        }
        if (!isAbsolute() && other.isAbsolute()) {
            throw new IllegalArgumentException("other must be relative as well!");
        }

        if (equals(other)) {
            return fileSystem.getEmptyPath();
        }

        return relativize(this, other, null);
    }

    private Path relativize(ISOPath path1, Path path2, Path relativePath) throws IllegalArgumentException {
        if (path2.startsWith(path1)) {
            Path subpath = path2.subpath(path1.getNameCount(), path2.getNameCount());
            if (relativePath == null) {
                return subpath;
            } else {
                return relativePath.resolve(subpath);
            }
        } else if (path1.getParent() == null) {
            throw new IllegalArgumentException("A relative path cannot be constructed for path: " + path2);
        } else {
            return relativize(path1.getParent(), path2,
                    relativePath == null ? path1.fileSystem.getParentDirectory()
                            : relativePath.resolve(path1.fileSystem.getParentDirectory()));
        }
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
            if (!pathName.equals(fileSystem.getCurrentDirectory())) {
                if (pathName.equals(fileSystem.getParentDirectory())) {
                    if (realPath.getNameCount() > 0) {
                        realPath = realPath.getParent();
                    } else {
                        throw new IOException("Path does not exist! Invalid path: " + toString());
                    }
                } else {
                    realPath = realPath.resolve(pathName.toString().toUpperCase());
                }
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
        // TODO
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

        if (Objects.equals(fileSystem, other.fileSystem) && root == other.root) {
            return equalsNames(other);
        } else {
            return false;
        }
    }

    private boolean equalsNames(ISOPath other) {
        if (getNameCount() != other.getNameCount()) {
            return false;
        }
        for (int i = 0; i < getNameCount(); i++) {
            if (!equalsFileName(getName(i).fileName, other.getName(i).fileName)) {
                return false;
            }
        }
        return true;
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
