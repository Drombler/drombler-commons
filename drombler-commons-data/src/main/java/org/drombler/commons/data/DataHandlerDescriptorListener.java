package org.drombler.commons.data;

import java.util.EventListener;

/**
 *
 * @author puce
 */
public interface DataHandlerDescriptorListener extends EventListener {

    void dataHandlerDescriptorAdded(DataHandlerDescriptorEvent<?> event);

    void dataHandlerDescriptorRemoved(DataHandlerDescriptorEvent<?> event);
}
