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
 */
public class DataHandlerDescriptorRegistry {

    private final Map<Class<?>, AbstractDataHandlerDescriptor<?>> dataHandlerClasses = new HashMap<>();
    private final Set<AbstractDataHandlerDescriptor<?>> dataHandlerDescriptors = new HashSet<>();
    private final Set<AbstractDataHandlerDescriptor<?>> unmodifiableDataHandlerDescriptors = Collections.unmodifiableSet(dataHandlerDescriptors);
    private final Set<SetChangeListener<AbstractDataHandlerDescriptor<?>>> listeners = new HashSet<>();

    /**
     * Registers a data handler descriptor.
     *
     * @param dataHandlerDescriptor a data handler descriptor
     */
    public void registerDataHandlerDescriptor(AbstractDataHandlerDescriptor<?> dataHandlerDescriptor) {
        dataHandlerClasses.put(dataHandlerDescriptor.getDataHandlerClass(), dataHandlerDescriptor);
        dataHandlerDescriptors.add(dataHandlerDescriptor);
        fireDataHandlerDescriptorAdded(dataHandlerDescriptor);
    }

    /**
     * Unregisters a data handler descriptor.
     *
     * @param dataHandlerDescriptor a data handler descriptor
     */
    public void unregisterDataHandlerDescriptor(AbstractDataHandlerDescriptor<?> dataHandlerDescriptor) {
        dataHandlerClasses.remove(dataHandlerDescriptor.getDataHandlerClass(), dataHandlerDescriptor);
        dataHandlerDescriptors.remove(dataHandlerDescriptor);
        fireDataHandlerDescriptorRemoved(dataHandlerDescriptor);
    }

    /**
     * Gets the registered descriptor for the specified data handler.
     *
     * @param dataHandler a data handler
     * @return a data handler descriptor
     */
    public AbstractDataHandlerDescriptor<?> getDataHandlerDescriptor(Object dataHandler) {
        return dataHandlerClasses.get(dataHandler.getClass());
    }

    /**
     * Adds a change listener to this registry.
     *
     * @param listener a change listener
     */
    public void addDataHandlerDescriptorListener(SetChangeListener<AbstractDataHandlerDescriptor<?>> listener) {
        listeners.add(listener);
    }

    /**
     * Removes a change listener from this registry.
     *
     * @param listener a change listener
     */
    public void removeDataHandlerDescriptorListener(SetChangeListener<AbstractDataHandlerDescriptor<?>> listener) {
        listeners.remove(listener);
    }

    private void fireDataHandlerDescriptorAdded(AbstractDataHandlerDescriptor<?> dataHandlerDescriptor) {
        SetChangeEvent<AbstractDataHandlerDescriptor<?>> event = new SetChangeEvent<>(unmodifiableDataHandlerDescriptors, dataHandlerDescriptor);
        listeners.forEach(listener -> listener.elementAdded(event));
    }

    private <D> void fireDataHandlerDescriptorRemoved(AbstractDataHandlerDescriptor<D> dataHandlerDescriptor) {
        SetChangeEvent<AbstractDataHandlerDescriptor<?>> event = new SetChangeEvent<>(unmodifiableDataHandlerDescriptors, dataHandlerDescriptor);
        listeners.forEach(listener -> listener.elementRemoved(event));
    }
}
