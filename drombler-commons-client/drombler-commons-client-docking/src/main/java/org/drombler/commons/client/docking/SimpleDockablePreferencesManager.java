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
package org.drombler.commons.client.docking;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO: Thread-safe?
 *
 * @author puce
 */
public class SimpleDockablePreferencesManager<D> implements DockablePreferencesManager<D> {

    // TODO: Consider to use weak references
    private final Map<Class<?>, DockablePreferences> defaultDockablePreferencesMap = Collections.synchronizedMap(
            new HashMap<Class<?>, DockablePreferences>());
    private final Map<D, DockablePreferences> dockablePreferencesMap = Collections.synchronizedMap(
            new HashMap<D, DockablePreferences>());

    @Override
    public DockablePreferences getDockablePreferences(D dockable) {
        if (dockablePreferencesMap.containsKey(dockable)) {
            return dockablePreferencesMap.get(dockable);
        } else {
            return defaultDockablePreferencesMap.get(dockable.getClass());
        }
    }

    @Override
    public void registerDefaultDockablePreferences(Class<?> dockableClass, DockablePreferences dockablePreferences) {
        defaultDockablePreferencesMap.put(dockableClass, dockablePreferences);
    }

//    @Override
//    public void registerDockablePreferences(D dockable, DockablePreferences dockablePreferences) {
//        dockablePreferencesMap.put(dockable, dockablePreferences);
//    }
    @Override
    public DockablePreferences unregisterDockablePreferences(D dockable) {
        return dockablePreferencesMap.remove(dockable);
    }

    @Override
    public void reset() {
        dockablePreferencesMap.clear();
    }
}
