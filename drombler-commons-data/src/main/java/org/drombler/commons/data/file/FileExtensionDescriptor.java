/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2016 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.data.file;

import java.util.List;

/**
 * A file extension registration descriptor. It associates file extensions with a MIME type.
 *
 * @author puce
 */
public class FileExtensionDescriptor {

    private final String displayName;
    private final String mimeType;
    private final List<String> fileExtensions;

    /**
     * Creates a new instance of this class.
     *
     * @param displayName the text to be displayed, e.g. as the text for filters in file dialogs
     * @param mimeType
     * @param fileExtensions
     */
    public FileExtensionDescriptor(String displayName, String mimeType, List<String> fileExtensions) {
        this.displayName = displayName;
        this.mimeType = mimeType;
        this.fileExtensions = fileExtensions;
    }

    /**
     * Gets text to be displayed, e.g. as the text for filters in file dialogs.
     *
     * @return the displayName the text to be displayed, e.g. as the text for filters in file dialogs
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets the MIME type for the specified file extensions.
     *
     * @return the MIME type for the specified file extensions.
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Gets the file extensions for the specified MIME type.
     *
     * @return the file extensions for the specified MIME type
     */
    // TODO: Set instead of List?
    public List<String> getFileExtensions() {
        return fileExtensions;
    }

}
