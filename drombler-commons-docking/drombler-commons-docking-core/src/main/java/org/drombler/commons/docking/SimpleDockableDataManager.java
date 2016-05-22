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
 * A simple in-memory {@link DockableDataManager}.
 *
 *
 * TODO: Thread-safe?
 *
 * @author puce
 * @param <D> the Dockable type
 * @param <DATA> the DockableData type
 */
public class SimpleDockableDataManager<D, DATA extends DockableData> implements DockableDataManager<D, DATA> {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleDockableDataManager.class);

    // TODO: Consider to use weak references
    private final Map<D, DATA> dockableDataMap = Collections.synchronizedMap(new HashMap<>());
    private final Map<Class<?>, DATA> classDockableDataMap = Collections.synchronizedMap(new HashMap<>());

    /**
     * Creates a new instance of this class.
     */
    public SimpleDockableDataManager() {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public DATA getDockableData(D dockable) {
        return dockableDataMap.get(dockable);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void registerDockableData(D dockable, DATA dockableData) {
        dockableDataMap.put(dockable, dockableData);
        LOG.debug("Registered dockable: {}", dockableData.getTitle());
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public DATA unregisterDockableData(D dockable) {
        DATA dockableData = dockableDataMap.remove(dockable);
        LOG.debug("Unregistered dockable: {}", dockableData != null ? dockableData.getTitle() : null);
        return dockableData;
    }
}
