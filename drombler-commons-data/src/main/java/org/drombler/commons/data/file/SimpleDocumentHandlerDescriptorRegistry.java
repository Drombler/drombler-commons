package org.drombler.commons.data.file;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 *
 * @author puce
 */
public class SimpleDocumentHandlerDescriptorRegistry implements DocumentHandlerDescriptorRegistry {

    private final Map<String, DocumentHandlerDescriptor<?>> mimeTypes = new HashMap<>();
    private final Set<DocumentHandlerDescriptorListener> listeners = new HashSet<>();

    @Override
    public void registerDocumentHandlerDescriptor(DocumentHandlerDescriptor<?> documentHandlerDescriptor) {
        mimeTypes.put(documentHandlerDescriptor.getMimeType().toLowerCase(), documentHandlerDescriptor);
        fireDocumentHandlerAdded(documentHandlerDescriptor);
    }

    @Override
    public void unregisterDocumentHandlerDescriptor(DocumentHandlerDescriptor<?> documentHandlerDescriptor) {
        mimeTypes.remove(documentHandlerDescriptor.getMimeType().toLowerCase(), documentHandlerDescriptor);
        fireDocumentHandlerRemoved(documentHandlerDescriptor);
    }

    @Override
    public DocumentHandlerDescriptor<?> getDocumentHandlerDescriptor(String mimeType) {
        return mimeTypes.get(mimeType.toLowerCase());
    }

    @Override
    public void registerDocumentHandlerDescriptorListener(DocumentHandlerDescriptorListener listener) {
        listeners.add(listener);
    }

    @Override
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
