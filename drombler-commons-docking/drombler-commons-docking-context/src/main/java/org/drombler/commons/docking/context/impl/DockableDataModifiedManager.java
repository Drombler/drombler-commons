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
 * Copyright 2015 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.docking.context.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.drombler.commons.action.command.Savable;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.ContextEvent;
import org.drombler.commons.context.ContextListener;
import org.drombler.commons.context.Contexts;
import org.drombler.commons.context.LocalContextProvider;
import org.drombler.commons.docking.DockableData;
import org.drombler.commons.docking.DockableEntry;
import org.drombler.commons.docking.context.DockingAreaContainer;
import org.drombler.commons.docking.context.DockingAreaContainerActiveDockableChangedEvent;
import org.drombler.commons.docking.context.DockingAreaContainerDockableEvent;
import org.drombler.commons.docking.context.DockingAreaContainerDockingAreaEvent;
import org.drombler.commons.docking.context.DockingAreaContainerListener;

/**
 * This manager listens for {@link Savable} in the local contexts of the Dockables and synchronizes the {@link FXDockableData#modifiedProperty()} accordingly.
 *
 * @author puce
 * @param <D>
 * @param <E>
 * @param <DATA>
 */
public class DockableDataModifiedManager<D, DATA extends DockableData, E extends DockableEntry<D, DATA>> implements AutoCloseable {

    private final Map<D, ContextListener> savableListeners = new HashMap<>();

    private final DockingAreaContainerListener<D, DATA, E> dockablesListener = new DockingAreaContainerListener<D, DATA, E>() {
        @Override
        public void dockingAreaAdded(DockingAreaContainerDockingAreaEvent<D, DATA, E> event) {
            // nothing to do
        }

        @Override
        public void dockingAreaRemoved(DockingAreaContainerDockingAreaEvent<D, DATA, E> event) {
            // nothing to do
        }

        @Override
        public void dockableAdded(DockingAreaContainerDockableEvent<D, DATA, E> event) {
            final E dockableEntry = event.getDockableEntry();
            final D dockable = dockableEntry.getDockable();
            if (dockable instanceof LocalContextProvider) {
                Context localContext = Contexts.getLocalContext(dockable);
                final ContextListener savableListener = (ContextEvent contextEvent)
                        -> updateDockableDataModified(dockableEntry.getDockableData(), localContext);
                savableListeners.put(dockable, savableListener);
                localContext.addContextListener(Savable.class, savableListener);
                updateDockableDataModified(dockableEntry.getDockableData(), localContext);
            }
        }

        @Override
        public void dockableRemoved(DockingAreaContainerDockableEvent<D, DATA, E> event) {
            final E dockableEntry = event.getDockableEntry();
            final D dockable = dockableEntry.getDockable();
            if (dockable instanceof LocalContextProvider) {
                ContextListener savableListener = savableListeners.remove(dockable);
                removeContextListener(dockable, savableListener);
            }
        }

        @Override
        public void activeDockableChanged(DockingAreaContainerActiveDockableChangedEvent<D, DATA, E> event) {
            // nothing to do
        }
    };

    private final DockingAreaContainer<D, DATA, E> dockingAreaContainer;

    public DockableDataModifiedManager(DockingAreaContainer<D, DATA, E> dockingAreaContainer) {
        this.dockingAreaContainer = dockingAreaContainer;
        this.dockingAreaContainer.addDockingAreaContainerListener(dockablesListener);
    }

    private void removeContextListener(D dockable, ContextListener savableListener) {
        Context localContext = Contexts.getLocalContext(dockable);
        localContext.removeContextListener(Savable.class, savableListener);
    }

    private void updateDockableDataModified(DATA dockableData, Context localContext) {
        dockableData.setModified(localContext.find(Savable.class) != null);
    }

    @Override
    public void close() {
        // TODO: handle vizualized/ unhandled Dockables?
        dockingAreaContainer.removeDockingAreaContainerListener(dockablesListener);
        for (Iterator<Map.Entry<D, ContextListener>> iterator = savableListeners.entrySet().iterator();
                iterator.hasNext();) {
            Map.Entry<D, ContextListener> entry = iterator.next();
            removeContextListener(entry.getKey(), entry.getValue());
            iterator.remove();
        }
    }
}
