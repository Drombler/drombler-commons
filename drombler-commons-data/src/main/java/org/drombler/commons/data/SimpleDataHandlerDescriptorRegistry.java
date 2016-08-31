package org.drombler.commons.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 *
 * @author puce
 */
public class SimpleDataHandlerDescriptorRegistry implements DataHandlerDescriptorRegistry {

    private final Map<Class<?>, AbstractDataHandlerDescriptor<?>> documentHandlerClasses = new HashMap<>();
    private final Set<DataHandlerDescriptorListener> listeners = new HashSet<>();

    @Override
    public void registerDataHandlerDescriptor(AbstractDataHandlerDescriptor<?> dataHandlerDescriptor) {
        documentHandlerClasses.put(dataHandlerDescriptor.getDataHandlerClass(), dataHandlerDescriptor);
        fireDataHandlerDescriptorAdded(dataHandlerDescriptor);
    }

    @Override
    public void unregisterDataHandlerDescriptor(AbstractDataHandlerDescriptor<?> dataHandlerDescriptor) {
        documentHandlerClasses.remove(dataHandlerDescriptor.getDataHandlerClass(), dataHandlerDescriptor);
        fireDataHandlerDescriptorRemoved(dataHandlerDescriptor);
    }

    @Override
    public AbstractDataHandlerDescriptor<?> getDataHandlerDescriptor(Object dataHandler) {
        return documentHandlerClasses.get(dataHandler.getClass());
    }

    @Override
    public void registerDataHandlerDescriptorListener(DataHandlerDescriptorListener listener) {
        listeners.add(listener);
    }

    @Override
    public void unregisterDataHandlerDescriptorListener(DataHandlerDescriptorListener listener) {
        listeners.remove(listener);
    }

    private <D> void fireDataHandlerDescriptorAdded(AbstractDataHandlerDescriptor<D> dataHandlerDescriptor) {
        DataHandlerDescriptorEvent<D> event = new DataHandlerDescriptorEvent<>(this, dataHandlerDescriptor);
        listeners.forEach(listener -> listener.dataHandlerDescriptorAdded(event));
    }

    private <D> void fireDataHandlerDescriptorRemoved(AbstractDataHandlerDescriptor<D> dataHandlerDescriptor) {
        DataHandlerDescriptorEvent<D> event = new DataHandlerDescriptorEvent<>(this, dataHandlerDescriptor);
        listeners.forEach(listener -> listener.dataHandlerDescriptorRemoved(event));
    }

}
