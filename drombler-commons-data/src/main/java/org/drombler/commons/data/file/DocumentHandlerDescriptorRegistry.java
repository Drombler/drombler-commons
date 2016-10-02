package org.drombler.commons.data.file;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A registry for Document Handler descriptors.
 *
 * @author puce
 * @see DocumentHandlerDescriptor
 */
public class DocumentHandlerDescriptorRegistry {

    private final Map<String, DocumentHandlerDescriptor<?>> mimeTypes = new HashMap<>();
    private final Set<DocumentHandlerDescriptorListener> listeners = new HashSet<>();

    /**
     * Registers a Document Handler descriptor.
     *
     * @param documentHandlerDescriptor the Document Handler descriptor to register
     */
    public void registerDocumentHandlerDescriptor(DocumentHandlerDescriptor<?> documentHandlerDescriptor) {
        mimeTypes.put(documentHandlerDescriptor.getMimeType().toLowerCase(), documentHandlerDescriptor);
        fireDocumentHandlerAdded(documentHandlerDescriptor);
    }

    /**
     * Unregisters a Document Handler descriptor.
     *
     * @param documentHandlerDescriptor the Document Handler descriptor to unregister
     */
    public void unregisterDocumentHandlerDescriptor(DocumentHandlerDescriptor<?> documentHandlerDescriptor) {
        mimeTypes.remove(documentHandlerDescriptor.getMimeType().toLowerCase(), documentHandlerDescriptor);
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
    public void registerDocumentHandlerDescriptorListener(DocumentHandlerDescriptorListener listener) {
        listeners.add(listener);
    }

    /**
     * Unregisters a Document Handler descriptor listener.
     *
     * @param listener a Document Handler descriptor listener
     */
    public void unregisterDocumentHandlerDescriptorListener(DocumentHandlerDescriptorListener listener) {
        listeners.remove(listener);
    }

    private <D> void fireDocumentHandlerAdded(DocumentHandlerDescriptor<D> documentHandlerDescriptor) {
        DocumentHandlerDescriptorEvent<D> event = new DocumentHandlerDescriptorEvent<>(this, documentHandlerDescriptor);
        listeners.forEach(listener -> listener.documentHandlerDescriptorAdded(event));
    }

    private <D> void fireDocumentHandlerRemoved(DocumentHandlerDescriptor<D> documentHandlerDescriptor) {
        DocumentHandlerDescriptorEvent<D> event = new DocumentHandlerDescriptorEvent<>(this, documentHandlerDescriptor);
        listeners.forEach(listener -> listener.documentHandlerDescriptorRemoved(event));
    }
}
