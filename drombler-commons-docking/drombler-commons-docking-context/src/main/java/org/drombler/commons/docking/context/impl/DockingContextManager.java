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
package org.drombler.commons.docking.context.impl;

import java.util.Arrays;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.ContextInjector;
import org.drombler.commons.context.ContextManager;
import org.drombler.commons.context.Contexts;
import org.drombler.commons.context.LocalContextProvider;
import org.drombler.commons.context.ProxyContext;
import org.drombler.commons.docking.DockableData;
import org.drombler.commons.docking.DockableEntry;
import org.drombler.commons.docking.context.DockingAreaContainer;
import org.drombler.commons.docking.context.DockingAreaContainerActiveDockableChangedEvent;
import org.drombler.commons.docking.context.DockingAreaContainerDockableEvent;
import org.drombler.commons.docking.context.DockingAreaContainerDockingAreaEvent;
import org.drombler.commons.docking.context.DockingAreaContainerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The DockingContextManager registers and unregisters the local {@link Context}s of Dockables depending on the Dockables registered in the specified {@link DockingPane} and set the active local
 * {@link Context} depending on the active Dockable set in the specified {@link DockingPane}.
 *
 * TODO: API ?
 *
 * TODO: good to implement AutoCloseable?
 *
 * @param <D>
 * @param <DATA>
 * @param <E>
 * @see DockingPane#getDockables()
 * @see DockingPane#activeDockableProperty()
 * @see ContextManager#putLocalContext(java.lang.Object, org.drombler.commons.context.Context)
 * @see ContextManager#removeLocalContext(java.lang.Object)
 * @see ContextManager#setLocalContextActive(java.lang.Object)
 *
 * @author puce
 */
public class DockingContextManager<D, DATA extends DockableData, E extends DockableEntry<D, DATA>> implements AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(DockingContextManager.class);

    private final ContextManager contextManager = new ContextManager();
    private final DockingAreaContainerListener<D, DATA, E> dockingAreaContainerListener = new ContextAwareListener();
    private final ContextInjector contextInjector = new ContextInjector(contextManager);
    private final DockingAreaContainer<D, DATA, E> dockingAreaContainer;
    private final DockableDataModifiedManager<D, DATA, E> dockableDataModifiedManager;

    /**
     * Creates a new instance of this class.
     *
     * TODO: support multiple, floating stages
     *
     * @param dockingAreaContainer the {@link DockingAreaContainer}
     * @param contextManager the {@link ContextManager}
     */
    public DockingContextManager(DockingAreaContainer<D, DATA, E> dockingAreaContainer) {
        this.dockingAreaContainer = dockingAreaContainer;
        this.dockableDataModifiedManager = new DockableDataModifiedManager<>(this.dockingAreaContainer);

        // TODO: handle vizualized/ unhandled Dockables
        this.dockingAreaContainer.addDockingAreaContainerListener(dockingAreaContainerListener);
    }

    public Context getActiveContext() {
        return contextManager.getActiveContext();
    }

    public Context getApplicationContext() {
        return contextManager.getApplicationContext();
    }

    public void inject(D dockable) {
        contextInjector.inject(dockable);
    }

    private void handleDockableAdded(D dockable) {
        contextManager.putLocalContext(dockable, createProxyContext(dockable));
    }

    private void handleDockableRemoved(E dockableEntry) {
        contextManager.removeLocalContext(dockableEntry.getDockable());
    }

    private void handleActiveDockableSelected(E dockableEntry) {
        contextManager.setLocalContextActive(dockableEntry.getDockable());
    }

    private Context createProxyContext(D dockable) {
        ProxyContext localProxyContext = new ProxyContext();
        if (dockable instanceof LocalContextProvider) {
            localProxyContext.addContext(Contexts.getLocalContext(dockable));
        }
        return localProxyContext;
    }

    public void addImplicitLocalContext(D dockable, Context... implicitLocalContexts) {
        ProxyContext localProxyContext = (ProxyContext) contextManager.getLocalContext(dockable);
        localProxyContext.addContexts(Arrays.asList(implicitLocalContexts));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void close() {
        // TODO: handle vizualized/ unhandled Dockables
        dockingAreaContainer.removeDockingAreaContainerListener(dockingAreaContainerListener);
        dockableDataModifiedManager.close();
    }

    private class ContextAwareListener implements DockingAreaContainerListener<D, DATA, E> {

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
            LOG.debug("Dockable added: {}", event.getDockableEntry());
            handleDockableAdded(event.getDockableEntry().getDockable());
        }

        @Override
        public void dockableRemoved(DockingAreaContainerDockableEvent<D, DATA, E> event) {
            LOG.debug("Dockable removed: {}", event.getDockableEntry());
            handleDockableRemoved(event.getDockableEntry());
        }

        @Override
        public void activeDockableChanged(DockingAreaContainerActiveDockableChangedEvent<D, DATA, E> event) {
            LOG.debug("Active Dockable changed!");
            handleActiveDockableSelected(event.getNewActiveDockable());
        }

    }
}
