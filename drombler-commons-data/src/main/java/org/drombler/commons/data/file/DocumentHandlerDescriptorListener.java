package org.drombler.commons.data.file;

import java.util.EventListener;

/**
 *
 * @author puce
 */
// TODO: DocumentHandlerDescriptorRegistryListener ?
public interface DocumentHandlerDescriptorListener extends EventListener {

    void documentHandlerDescriptorAdded(DocumentHandlerDescriptorEvent<?> event);

    void documentHandlerDescriptorRemoved(DocumentHandlerDescriptorEvent<?> event);
}
