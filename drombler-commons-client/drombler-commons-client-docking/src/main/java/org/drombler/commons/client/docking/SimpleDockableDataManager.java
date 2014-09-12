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
 * A simple in-memory {@link DockablePreferencesManager}.
 *
 * Note: This class is some pre-work changing (once drag'n'drop * is supported) DockablePreferences.
 *
 * TODO: Thread-safe?
 *
 * @author puce
 * @param <D> the Dockable type
 * @param <DATA>
 */
public class SimpleDockableDataManager<D, DATA extends DockableData> implements DockableDataManager<D, DATA> {

    // TODO: Consider to use weak references
    private final Map<D, DATA> dockableDataMap = Collections.synchronizedMap(new HashMap<>());
    private final Map<Class<?>, DATA> classDockableDataMap = Collections.synchronizedMap(new HashMap<>());

    /**
     * {@inheritDoc }
     */
    @Override
    public DATA getDockableData(D dockable) {
        return dockableDataMap.get(dockable);
    }

    @Override
    public DATA getClassDockableData(D dockable) {
        return classDockableDataMap.get(dockable);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void registerDockableData(D dockable, DATA dockableData) {
        dockableDataMap.put(dockable, dockableData);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public DATA unregisterDockableData(D dockable) {
        return dockableDataMap.remove(dockable);
    }

    @Override
    public void registerClassDockableData(Class<?> type, DATA dockableData) {
        classDockableDataMap.put(type, dockableData);
    }

    @Override
    public DATA unregisterClassDockableData(Class<?> type) {
        return classDockableDataMap.remove(type);
    }

}
