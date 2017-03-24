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
package org.drombler.commons.docking;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple in-memory {@link DockablePreferencesManager}.
 *
 * Note: This class is some pre-work changing (once drag'n'drop * is supported) DockablePreferences.
 *
 * TODO: Thread-safe?
 *
 * @author puce
 * @param <D> the Dockable type
 */
public class SimpleDockablePreferencesManager<D> implements DockablePreferencesManager<D> {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleDockablePreferencesManager.class);

    // TODO: Consider to use weak references
    private final Map<Class<?>, DockablePreferences> defaultDockablePreferencesMap = Collections.synchronizedMap(
            new HashMap<Class<?>, DockablePreferences>());
    private final Map<D, DockablePreferences> dockablePreferencesMap = Collections.synchronizedMap(
            new HashMap<D, DockablePreferences>());

    /**
     * Creates a new instance of this class.
     */
    public SimpleDockablePreferencesManager() {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public DockablePreferences getDockablePreferences(D dockable) {
        // TODO: not thread-safe (map can change between calls)
        if (!dockablePreferencesMap.containsKey(dockable)) {
            dockablePreferencesMap.put(dockable, new DockablePreferences(defaultDockablePreferencesMap.get(dockable.
                    getClass())));
            LOG.debug("Registered dockable preferences for: {}", dockable);
        }
        return dockablePreferencesMap.get(dockable);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void registerDefaultDockablePreferences(Class<?> dockableClass, DockablePreferences dockablePreferences) {
        defaultDockablePreferencesMap.put(dockableClass, dockablePreferences);
        LOG.debug("Registered default dockable preferences for: {}", dockableClass);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public DockablePreferences unregisterDefaultDockablePreferences(Class<?> dockableClass) {
        DockablePreferences dockablePreferences = defaultDockablePreferencesMap.remove(dockableClass);
        LOG.debug("Unregistered default dockable preferences for: {}", dockableClass);
        return dockablePreferences;
    }

//    @Override
//    public void registerDockablePreferences(D dockable, DockablePreferences dockablePreferences) {
//        dockablePreferencesMap.put(dockable, dockablePreferences);
//    }
    /**
     * {@inheritDoc }
     */
    @Override
    public DockablePreferences unregisterDockablePreferences(D dockable) {
        DockablePreferences dockablePreferences = dockablePreferencesMap.remove(dockable);
        LOG.debug("Unregistered dockable preferences for: {}", dockable);
        return dockablePreferences;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void reset() {
        dockablePreferencesMap.clear();
    }
}
