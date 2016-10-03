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
package org.drombler.commons.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A registry for Document Handler descriptors.
 *
 * @author puce
 */
public class DataHandlerDescriptorRegistry {

    private final Map<Class<?>, AbstractDataHandlerDescriptor<?>> documentHandlerClasses = new HashMap<>();
    private final Set<DataHandlerDescriptorListener> listeners = new HashSet<>();

    public void registerDataHandlerDescriptor(AbstractDataHandlerDescriptor<?> dataHandlerDescriptor) {
        documentHandlerClasses.put(dataHandlerDescriptor.getDataHandlerClass(), dataHandlerDescriptor);
        fireDataHandlerDescriptorAdded(dataHandlerDescriptor);
    }

    public void unregisterDataHandlerDescriptor(AbstractDataHandlerDescriptor<?> dataHandlerDescriptor) {
        documentHandlerClasses.remove(dataHandlerDescriptor.getDataHandlerClass(), dataHandlerDescriptor);
        fireDataHandlerDescriptorRemoved(dataHandlerDescriptor);
    }

    public AbstractDataHandlerDescriptor<?> getDataHandlerDescriptor(Object dataHandler) {
        return documentHandlerClasses.get(dataHandler.getClass());
    }

    public void registerDataHandlerDescriptorListener(DataHandlerDescriptorListener listener) {
        listeners.add(listener);
    }

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
