/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (Sourceforge.net user: puce).
 * Copyright 2012 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A base class for {@link Context} implementations.
 *
 * @author puce
 */
public abstract class AbstractContext implements Context {

    private final Map<Class<?>, List<ContextListener<?>>> listeners = new HashMap<>();
    private final Map<Class<?>, List<ContextListener<?>>> unmodifiableListeners = Collections.unmodifiableMap(listeners);

    /**
     * Creates a new instance of this class.
     */
    public AbstractContext() {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public <T> void addContextListener(Class<T> type, ContextListener<T> listener) {
        if (!listeners.containsKey(type)) {
            listeners.put(type, new ArrayList<>());
        }
        listeners.get(type).add(listener);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public <T> void removeContextListener(Class<T> type, ContextListener<T> listener) {
        if (listeners.containsKey(type)) {
            listeners.get(type).remove(listener);
        }
    }

    /**
     * Fires a {@link ContextEvent} to each {@link ContextListener} listening for instances of type {@code type}.
     *
     * @param <T> the specified type
     * @param type the type that changed
     */
    @SuppressWarnings("unchecked")
    protected <T> void fireContextEvent(Class<T> type) {
        if (listeners.containsKey(type)) {
            ContextEvent<T> event = new ContextEvent<>(this, type);
            listeners.get(type).forEach(listener -> ((ContextListener<T>) listener).contextChanged(event));
        }
    }

    /**
     * Gets all registered {@link ContextListener}.
     *
     * Note: the type parameters of entries match.
     *
     * @return a {@link Map} grouping all registered {@link ContextListener} by the type of instances they are listening
     * for.
     */
    protected final Map<Class<?>, List<ContextListener<?>>> getListeners() {
        return unmodifiableListeners;
    }
}
