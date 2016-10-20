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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.softsmithy.lib.util.SetChangeEvent;
import org.softsmithy.lib.util.SetChangeListener;

/**
 * A registry for Document Handler descriptors.
 *
 * @author puce
 * @see DocumentHandlerDescriptor
 */
public class DocumentHandlerDescriptorRegistry {

    private final Map<String, DocumentHandlerDescriptor<?>> mimeTypes = new HashMap<>();
    private final Set<DocumentHandlerDescriptor<?>> documentHandlerDescriptors = new HashSet<>();
    private final Set<DocumentHandlerDescriptor<?>> unmodifiableDocumentHandlerDescriptors = Collections.unmodifiableSet(documentHandlerDescriptors);
    private final Set<SetChangeListener<DocumentHandlerDescriptor<?>>> listeners = new HashSet<>();

    /**
     * Registers a Document Handler descriptor.
     *
     * @param documentHandlerDescriptor the Document Handler descriptor to register
     */
    public void registerDocumentHandlerDescriptor(DocumentHandlerDescriptor<?> documentHandlerDescriptor) {
        mimeTypes.put(documentHandlerDescriptor.getMimeType().toLowerCase(), documentHandlerDescriptor);
        documentHandlerDescriptors.add(documentHandlerDescriptor);
        fireDocumentHandlerAdded(documentHandlerDescriptor);
    }

    /**
     * Unregisters a Document Handler descriptor.
     *
     * @param documentHandlerDescriptor the Document Handler descriptor to unregister
     */
    public void unregisterDocumentHandlerDescriptor(DocumentHandlerDescriptor<?> documentHandlerDescriptor) {
        mimeTypes.remove(documentHandlerDescriptor.getMimeType().toLowerCase(), documentHandlerDescriptor);
        documentHandlerDescriptors.remove(documentHandlerDescriptor);
        fireDocumentHandlerRemoved(documentHandlerDescriptor);
    }

    /**
     * Gets the registered Document Handler descriptor for the specified MIME type.
     *
     * @param mimeType the MIME type
     * @return the registered Document Handler descriptor for the specified MIME type
     */
    public DocumentHandlerDescriptor<?> getDocumentHandlerDescriptor(String mimeType) {
        return mimeTypes.get(mimeType.toLowerCase());
    }

    /**
     * Registers a Document Handler descriptor listener.
     *
     * @param listener a Document Handler descriptor listener
     */
    public void addDocumentHandlerDescriptorListener(SetChangeListener<DocumentHandlerDescriptor<?>> listener) {
        listeners.add(listener);
    }

    /**
     * Unregisters a Document Handler descriptor listener.
     *
     * @param listener a Document Handler descriptor listener
     */
    public void removeDocumentHandlerDescriptorListener(SetChangeListener<DocumentHandlerDescriptor<?>> listener) {
        listeners.remove(listener);
    }

    private void fireDocumentHandlerAdded(DocumentHandlerDescriptor<?> documentHandlerDescriptor) {
        SetChangeEvent<DocumentHandlerDescriptor<?>> event = new SetChangeEvent<>(unmodifiableDocumentHandlerDescriptors, documentHandlerDescriptor);
        listeners.forEach(listener -> listener.elementAdded(event));
    }

    private <D> void fireDocumentHandlerRemoved(DocumentHandlerDescriptor<D> documentHandlerDescriptor) {
        SetChangeEvent<DocumentHandlerDescriptor<?>> event = new SetChangeEvent<>(unmodifiableDocumentHandlerDescriptors, documentHandlerDescriptor);
        listeners.forEach(listener -> listener.elementRemoved(event));
    }
}
