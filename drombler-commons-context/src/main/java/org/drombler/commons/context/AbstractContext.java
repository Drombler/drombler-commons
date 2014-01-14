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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A base class for {@link Context} implementations.
 *
 * @author puce
 */
public abstract class AbstractContext implements Context {

    private final Map<Class<?>, List<ContextListener>> listeners = new HashMap<>();

    /**
     * {@inheritDoc }
     */
    @Override
    public void addContextListener(Class<?> type, ContextListener listener) {
        if (!listeners.containsKey(type)) {
            getListeners().put(type, new ArrayList<ContextListener>());
        }
        getListeners().get(type).add(listener);

    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void removeContextListener(Class<?> type, ContextListener listener) {
        if (getListeners().containsKey(type)) {
            getListeners().get(type).remove(listener);
        }
    }

    /**
     * Fires a {@link ContextEvent} to each {@link ContextListener} listening for instances of type {@code type}.
     *
     * @param type
     */
    protected void fireContextEvent(Class<?> type) {
        if (getListeners().containsKey(type)) {
            ContextEvent event = new ContextEvent(this);
            for (ContextListener listener : getListeners().get(type)) {
                listener.contextChanged(event);
            }
        }
    }

    /**
     * Gets all registered {@link ContextListener}.
     *
     * @return a {@link Map} grouping all registered {@link ContextListener} by the type of instances they are listening
     * for.
     */
    protected Map<Class<?>, List<ContextListener>> getListeners() {
        return listeners;
    }
}
