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

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.WatchService;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author puce
 */
public class ISOFileSystem extends FileSystem {

    private static final String SEPARATOR = "/";

    private final ISOFileSystemProvider fileSystemProvider;
    private final Path fileSystemPath;
    private final Map<String, ?> env;
    private final FileStore fileStore = new ISOFileStore();
    private final List<FileStore> fileStores = Collections.singletonList(fileStore);
    private final ISOPath rootDirectory = new ISOPath(this, SEPARATOR, true);
    private final List<Path> rootDirectories = Collections.singletonList(rootDirectory);
    private final Path emptyPath = new ISOPath(this, "", false);
    private final ISOPath currentDirectory = new ISOPath(this, ".", false);
    private final ISOPath parentDirectory = new ISOPath(this, "..", false);
    private boolean open = true;

    ISOFileSystem(ISOFileSystemProvider fileSystemProvider, Path fileSystemPath, Map<String, ?> env) {
        this.fileSystemProvider = fileSystemProvider;
        this.fileSystemPath = fileSystemPath;
        this.env = env;
    }

    @Override
    public FileSystemProvider provider() {
        return fileSystemProvider;
    }

    @Override
    public void close() throws IOException {
        open = false;
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public boolean isReadOnly() {
        return fileStore.isReadOnly();
    }

    @Override
    public String getSeparator() {
        return SEPARATOR;
    }

    @Override
    public Iterable<Path> getRootDirectories() {
        return rootDirectories;
    }

    @Override
    public Iterable<FileStore> getFileStores() {
        return fileStores;
    }

    @Override
    public Set<String> supportedFileAttributeViews() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Path getPath(String first, String... more) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PathMatcher getPathMatcher(String syntaxAndPattern) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserPrincipalLookupService getUserPrincipalLookupService() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WatchService newWatchService() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ISOPath getRootDirectory() {
        return rootDirectory;
    }

    public Path getEmptyPath() {
        return emptyPath;
    }

    public ISOPath getDefaultDirectory() {
        return rootDirectory;
    }

    public Path getFileSystemPath() {
        return fileSystemPath;
    }

    public ISOPath getCurrentDirectory() {
        return currentDirectory;
    }

    public ISOPath getParentDirectory() {
        return parentDirectory;
    }

}
