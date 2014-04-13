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
 * Copyright 2014 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.client.docking;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author puce
 */
class DockableDescriptorManager<D> {
// TODO: Consider to use weak references

    private final Map<Class<?>, DockableInfo> defaultDockablePreferencesMap = Collections.synchronizedMap(
            new HashMap<Class<?>, DockableInfo>());
    private final Map<D, DockableInfo> dockablePreferencesMap = Collections.synchronizedMap(
            new HashMap<D, DockableInfo>());

    /**
     * Creates a new instance of this class.
     */
    public DockableDescriptorManager() {
    }

    public DockableInfo getDockablePreferences(D dockable) {
        // TODO: not thread-safe (map can change between calls)
        if (!dockablePreferencesMap.containsKey(dockable)) {
            dockablePreferencesMap.put(dockable, defaultDockablePreferencesMap.get(dockable.getClass()));
        }
        return dockablePreferencesMap.get(dockable);
    }

    public void registerDefaultDockablePreferences(Class<?> dockableClass, DockableInfo dockablePreferences) {
        defaultDockablePreferencesMap.put(dockableClass, dockablePreferences);
    }

    public DockableInfo unregisterDockablePreferences(D dockable) {
        return dockablePreferencesMap.remove(dockable);
    }

}
