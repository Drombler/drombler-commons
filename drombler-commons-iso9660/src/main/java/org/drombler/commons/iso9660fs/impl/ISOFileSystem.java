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
package org.drombler.commons.iso9660fs.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.WatchService;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.drombler.commons.iso9660fs.ISODirectoryDescriptor;
import org.drombler.commons.iso9660fs.ISOPrimaryVolumeDescriptor;
import org.drombler.commons.iso9660fs.ISOVolumeDescriptor;
import org.drombler.commons.iso9660fs.ISOVolumeDescriptorType;

/**
 *
 * @author puce
 */
public class ISOFileSystem extends FileSystem {

    private static final String SEPARATOR = "/";
    public static final String EMPTY_PATH_STRING = "";
    public static final String CURRENT_PATH_STRING = ".";
    public static final String PARENT_PATH_STRING = "..";

    private final ISOFileSystemProvider fileSystemProvider;
    private final Path fileSystemPath;
    private final Map<String, ?> env;
    private final FileStore fileStore;
    private final List<FileStore> fileStores;
    private final ISOPath rootDirectory = new ISOPath(this, SEPARATOR, true);
    private final List<Path> rootDirectories = Collections.singletonList(rootDirectory);
    private final Path emptyPath = new ISOPath(this, EMPTY_PATH_STRING, false);
    private final ISOPath currentDirectory = new ISOPath(this, CURRENT_PATH_STRING, false);
    private final ISOPath parentDirectory = new ISOPath(this, PARENT_PATH_STRING, false);
    private final SeekableByteChannel byteChannel;
    private boolean open = true;
    private ISOPrimaryVolumeDescriptor primaryVolumeDescriptor;

    ISOFileSystem(ISOFileSystemProvider fileSystemProvider, Path fileSystemPath, Map<String, ?> env) throws IOException {
        this.fileSystemProvider = fileSystemProvider;
        this.fileSystemPath = fileSystemPath;
        this.env = env;
        this.fileStore = new ISOFileStore(fileSystemPath);
        this.fileStores = Collections.singletonList(fileStore);
        System.out.println("FileSystemPath: " + fileSystemPath);
        this.byteChannel = Files.newByteChannel(fileSystemPath);
        init();
    }

    private void init() throws IOException {
        readVolumeDescriptors();
    }

    private void readVolumeDescriptors() throws IOException {
        final int KiB_32 = 32768;
        byteChannel.position(KiB_32);

        ByteBuffer byteBuffer = ByteBuffer.allocate(ISOVolumeDescriptor.SECTOR_LENGTH);
        final int numBytes = byteChannel.read(byteBuffer);
        if (numBytes != ISOVolumeDescriptor.SECTOR_LENGTH) {
            throw new IOException("Too few data to read: " + numBytes);
        }
        byteBuffer.position(0);
        ISOVolumeDescriptor volumeDescriptor = ISOVolumeDescriptor.createISOVolumeDescriptor(byteBuffer);
        System.out.println(volumeDescriptor);
        if (volumeDescriptor.getType() == ISOVolumeDescriptorType.PRIMARY_VOLUME_DESCRIPTOR) {
            this.primaryVolumeDescriptor = (ISOPrimaryVolumeDescriptor) volumeDescriptor;
            ISODirectoryDescriptor rootDirectoryDescriptor = primaryVolumeDescriptor.getRootDirectoryDescriptor();
            long locationOfExtend = rootDirectoryDescriptor.getLocationOfExtend();
        }

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
        StringBuilder sb = new StringBuilder(first);
        for (String nameElement : more) {
            if (!nameElement.equals(EMPTY_PATH_STRING)) {
                if (sb.length() > 0) {
                    sb.append(getSeparator());
                }
                sb.append(nameElement);
            }
        }
        return ISOPath.valueOf(sb.toString(), this);
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
        throw new UnsupportedOperationException("This is a read-only file system! There's nothing to watch!");
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

    public SeekableByteChannel newByteChannel(Path path) {
        if (!path.getFileSystem().equals(this)) {
            throw new IllegalArgumentException("The specified path belongs to a different FileSystem! Path: " + path);
        }
        return null;
    }

}
