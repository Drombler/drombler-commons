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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import org.drombler.commons.data.AbstractDataHandlerDescriptor;

/**
 * A Document Handler descriptor. It provides the meta data to create a Document Handler.
 *
 * @param <D> the type of the document handler
 * @author puce
 */
public class DocumentHandlerDescriptor<D> extends AbstractDataHandlerDescriptor<D> {

    private String mimeType;

    /**
     * Gets the MIME type.
     *
     * @return the mimeType
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Sets the MIME type.
     *
     * @param mimeType the mimeType
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * Creates a document handler for the specified file path. The _Data Handler_ class is expected to have a constructor with a single {@link Path} argument.
     *
     * @param filePath the file path
     * @return a document handler instance for the provided file path
     * @throws NoSuchMethodException if no constructor with a single {@link Path} argument can be found
     * @throws InstantiationException if the instance cannot be created
     * @throws IllegalAccessException if the instance cannot be created
     * @throws InvocationTargetException if the instance cannot be created
     */
    public D createDocumentHandler(Path filePath) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<? extends D> documentHandlerConstructor = getDataHandlerClass().getConstructor(Path.class);
        return documentHandlerConstructor.newInstance(filePath);
    }
}
