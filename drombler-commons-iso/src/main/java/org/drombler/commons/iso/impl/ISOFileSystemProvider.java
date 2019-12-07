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
import java.net.URI;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.spi.FileSystemProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.softsmithy.lib.util.ServiceProvider;

//https://docs.oracle.com/javase/8/docs/technotes/guides/io/fsp/filesystemprovider.html
//http://hg.openjdk.java.net/jdk8u/jdk8u/jdk/file/9dc67d03e6e5/src/share/demo/nio/zipfs/src/com/sun/nio/zipfs
//http://wiki.osdev.org/ISO_9660
/**
 *
 * @author puce
 */
@ServiceProvider(serviceClass = FileSystemProvider.class)
public class ISOFileSystemProvider extends FileSystemProvider {

    private static final String SCHEME = "iso";
    // TODO: good?
//    private static final String RESOURCE_SEPARATOR = "!/";

    private final Map<Path, ISOFileSystem> fileSystems = new HashMap<>();

    @Override
    public String getScheme() {
        return SCHEME;
    }

    @Override
    public FileSystem newFileSystem(URI uri, Map<String, ?> env) throws IOException {
        Path fileSystemPath = getFileSystemPath(uri);
        if (!Files.isRegularFile(fileSystemPath)) {
            throw new IOException("The FileSystem file is not a regular file: " + fileSystemPath);
        }
        synchronized (this) {
            if (fileSystems.containsKey(fileSystemPath)) {
                throw new FileSystemAlreadyExistsException("FileSystem already exists for: " + fileSystemPath);
            }
            ISOFileSystem fileSystem = new ISOFileSystem(this, fileSystemPath, env);
            fileSystems.put(fileSystemPath, fileSystem);
            return fileSystem;
        }
    }

    private Path getFileSystemPath(URI uri) throws IOException {
        String scheme = uri.getScheme();
        if (scheme == null || !scheme.equalsIgnoreCase(getScheme())) {
            throw new IllegalArgumentException("Unsupported scheme: " + scheme);
        }
        String rawSchemeSpecificPart = uri.getRawSchemeSpecificPart();
//        int resourceSeparatorIndex = rawSchemeSpecificPart.indexOf(RESOURCE_SEPARATOR);
//        if (resourceSeparatorIndex != -1) {
//            rawSchemeSpecificPart = rawSchemeSpecificPart.substring(0, resourceSeparatorIndex);
//        }
        return Paths.get(URI.create(rawSchemeSpecificPart)).toAbsolutePath().toRealPath();

    }

    @Override
    public FileSystem getFileSystem(URI uri) {
        try {
            Path fileSystemPath = getFileSystemPath(uri);
            synchronized (this) {
                if (fileSystems.containsKey(fileSystemPath)) {
                    return fileSystems.get(fileSystemPath);
                } else {
                    throw new FileSystemNotFoundException("No existing FileSystem found for path: " + fileSystemPath);
                }
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    @Override
    public Path getPath(URI uri) {
        String fragment = uri.getFragment();
        if (fragment != null) {
            return getFileSystem(uri).getPath(fragment);
        } else {
            throw new IllegalArgumentException("No fragment specified!");
        }
    }

    @Override
    public SeekableByteChannel newByteChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs)
            throws IOException {
        if (fileSystems.containsKey(path)) {
            return fileSystems.get(path).newByteChannel(path);
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DirectoryStream<Path> newDirectoryStream(Path dir,
            DirectoryStream.Filter<? super Path> filter) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createDirectory(Path dir,
            FileAttribute<?>... attrs) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Path path) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void copy(Path source, Path target, CopyOption... options) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void move(Path source, Path target, CopyOption... options) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isSameFile(Path path, Path path2) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isHidden(Path path) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FileStore getFileStore(Path path) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void checkAccess(Path path, AccessMode... modes) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <V extends FileAttributeView> V getFileAttributeView(Path path, Class<V> type, LinkOption... options) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <A extends BasicFileAttributes> A readAttributes(Path path, Class<A> type, LinkOption... options) throws
            IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, Object> readAttributes(Path path, String attributes, LinkOption... options) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setAttribute(Path path, String attribute, Object value, LinkOption... options) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
