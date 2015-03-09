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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileStoreAttributeView;

/**
 *
 * @author puce
 */
public class ISOFileStore extends FileStore {

    private static final String TYPE = "ISO 9660 Image File";
    private static final String BASIC_FILE_ATTRIBUTE_VIEW_NAME = "basic";

    private final Path fileSystemPath;

    public ISOFileStore(Path fileSystemPath) {
        this.fileSystemPath = fileSystemPath;
    }

    @Override
    public String name() {
        return fileSystemPath.getFileName().toString();
    }

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Override
    public long getTotalSpace() throws IOException {
        return Files.size(fileSystemPath);
    }

    @Override
    public long getUsableSpace() {
        return 0;
    }

    @Override
    public long getUnallocatedSpace() {
        return 0;
    }

    @Override
    public boolean supportsFileAttributeView(Class<? extends FileAttributeView> type) {
        return type.equals(BasicFileAttributeView.class);
    }

    @Override
    public boolean supportsFileAttributeView(String name) {
        return name.equals(BASIC_FILE_ATTRIBUTE_VIEW_NAME);
    }

    @Override
    public <V extends FileStoreAttributeView> V getFileStoreAttributeView(Class<V> type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getAttribute(String attribute) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
