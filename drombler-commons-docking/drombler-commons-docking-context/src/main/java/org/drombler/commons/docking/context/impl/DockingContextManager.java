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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.ContextInjector;
import org.drombler.commons.context.ContextManager;
import org.drombler.commons.context.LocalProxyContext;
import org.drombler.commons.docking.DockableData;
import org.drombler.commons.docking.DockableEntry;
import org.drombler.commons.docking.DockableKind;
import org.drombler.commons.docking.context.DockingAreaContainer;
import static org.drombler.commons.docking.context.DockingAreaContainer.ACTIVE_DOCKABLE_PROPERTY_NAME;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softsmithy.lib.util.SetChangeEvent;
import org.softsmithy.lib.util.SetChangeListener;

/**
 * The DockingContextManager registers and unregisters the local {@link Context}s of Dockables depending on the Dockables registered in the specified {@link DockingAreaContainer} and sets the active
 * local {@link Context} depending on the active Dockable set in the specified {@link DockingAreaContainer}.
 *
 * TODO: API ?
 *
 * TODO: good to implement AutoCloseable?
 *
 * @param <D>
 * @param <DATA>
 * @param <E>
 * @see DockingAreaContainer#getDockables()
 * @see DockingAreaContainer#getActiveDockable()
 * @see ContextManager#registerLocalContext(java.lang.Object, org.drombler.commons.context.LocalProxyContext)
 * @see ContextManager#unregisterLocalContext(java.lang.Object)
 * @see ContextManager#setLocalContextActive(java.lang.Object)
 *
 * @author puce
 */
public class DockingContextManager<D, DATA extends DockableData, E extends DockableEntry<D, DATA>> implements AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(DockingContextManager.class);

    private final SetChangeListener<E> dockableSetChangeListener = new DockableListener();
    private final PropertyChangeListener activeDockableListener = new ActiveDockableListener();
    private final ContextManager contextManager;
    private final ContextInjector contextInjector;
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
    public DockingContextManager(DockingAreaContainer<D, DATA, E> dockingAreaContainer, ContextManager contextManager) {
        this.dockingAreaContainer = dockingAreaContainer;
        this.dockableDataModifiedManager = new DockableDataModifiedManager<>(this.dockingAreaContainer);
        this.contextManager = contextManager;
        this.contextInjector = new ContextInjector(contextManager);
        // TODO: handle vizualized/ unhandled Dockables
        this.dockingAreaContainer.addDockableSetChangeListener(dockableSetChangeListener);
        this.dockingAreaContainer.addPropertyChangeListener(ACTIVE_DOCKABLE_PROPERTY_NAME, activeDockableListener);
    }

    public Context getLocalContext(D dockable) {
        return contextManager.getLocalContext(dockable);
    }

    public ContextInjector getContextInjector() {
        return contextInjector;
    }

    public void addImplicitLocalContext(D dockable, Context... implicitLocalContexts) {
        LocalProxyContext localContext = contextManager.getLocalContext(dockable);
        if (localContext == null) { // dockable is not an instance of LocalContextProvider and no other implicit contexts have been associated
            localContext = LocalProxyContext.createLocalProxyContext(dockable);
            contextManager.registerLocalContext(dockable, localContext);
        }
        localContext.getImplicitContext().addContexts(Arrays.asList(implicitLocalContexts));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void close() {
        // TODO: handle vizualized/ unhandled Dockables
        dockingAreaContainer.removeDockableSetChangeListener(dockableSetChangeListener);
        dockingAreaContainer.removePropertyChangeListener(ACTIVE_DOCKABLE_PROPERTY_NAME, activeDockableListener);
        dockableDataModifiedManager.close();
    }

    private class DockableListener implements SetChangeListener<E> {

        @Override
        public void elementAdded(SetChangeEvent<E> event) {
            LOG.debug("Dockable added: {}", event.getElement());
            contextManager.registerLocalContext(event.getElement().getDockable());
        }

        @Override
        public void elementRemoved(SetChangeEvent<E> event) {
            LOG.debug("Dockable removed: {}", event.getElement());
            contextManager.unregisterLocalContext(event.getElement().getDockable());
        }

    }

    private class ActiveDockableListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent event) {
            LOG.debug("Active Dockable changed!");
            final E dockableEntry = (E) event.getNewValue();
            LocalProxyContext localContext = contextManager.getLocalContext(dockableEntry.getDockable());
            if (dockableEntry.getKind() == DockableKind.EDITOR || (localContext != null && localContext.hasExplicitContext())) {
                contextManager.setLocalContextActive(dockableEntry.getDockable());
            }
        }
    }
}
