package org.drombler.commons.data.file;

import org.drombler.commons.data.file.DocumentHandlerDescriptor;

public interface DocumentHandlerDescriptorRegistry {

    void registerDocumentHandlerDescriptor(DocumentHandlerDescriptor<?> documentHandlerDescriptor);

    void unregisterDocumentHandlerDescriptor(DocumentHandlerDescriptor<?> documentHandlerDescriptor);

    DocumentHandlerDescriptor<?> getDocumentHandlerDescriptor(String mimeType);

    void registerDocumentHandlerDescriptorListener(DocumentHandlerDescriptorListener listener);

    void unregisterDocumentHandlerDescriptorListener(DocumentHandlerDescriptorListener listener);

}
