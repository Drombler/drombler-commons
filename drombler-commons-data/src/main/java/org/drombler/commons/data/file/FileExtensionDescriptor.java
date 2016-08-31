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
 *
 * @author puce
 */
public class FileExtensionDescriptor {

    private final String displayName;
    private final String mimeType;
    private final List<String> fileExtensions;

    public FileExtensionDescriptor(String displayName, String mimeType, List<String> fileExtensions) {
        this.displayName = displayName;
        this.mimeType = mimeType;
        this.fileExtensions = fileExtensions;
    }

    /**
     * @return the mimeType
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @return the fileExtensions
     */
    public List<String> getFileExtensions() {
        return fileExtensions;
    }

}
