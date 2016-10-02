package org.drombler.commons.data.file;

import java.util.EventObject;

/**
 *
 * @author puce
 */
public class DocumentHandlerDescriptorEvent<D> extends EventObject {

    private static final long serialVersionUID = 1837696044825618602L;

    private final DocumentHandlerDescriptor<D> documentHandlerDescriptor;

    public DocumentHandlerDescriptorEvent(DocumentHandlerDescriptorRegistry source, DocumentHandlerDescriptor<D> documentHandlerDescriptor) {
        super(source);
        this.documentHandlerDescriptor = documentHandlerDescriptor;
    }

    /**
     * @return the documentHandlerDescriptor
     */
    public DocumentHandlerDescriptor<D> getDocumentHandlerDescriptor() {
        return documentHandlerDescriptor;
    }

}
