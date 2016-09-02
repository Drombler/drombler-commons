package org.drombler.commons.data;

public interface DataHandlerDescriptorRegistry {

    void registerDataHandlerDescriptor(AbstractDataHandlerDescriptor<?> dataHandlerDescriptor);

    void unregisterDataHandlerDescriptor(AbstractDataHandlerDescriptor<?> dataHandlerDescriptor);

    AbstractDataHandlerDescriptor<?> getDataHandlerDescriptor(Object dataHandler);

    void registerDataHandlerDescriptorListener(DataHandlerDescriptorListener listener);

    void unregisterDataHandlerDescriptorListener(DataHandlerDescriptorListener listener);

}
