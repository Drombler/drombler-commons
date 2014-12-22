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
package org.drombler.commons.fx.docking;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.SetChangeListener;
import javafx.scene.Node;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.ContextManager;
import org.drombler.commons.context.Contexts;
import org.drombler.commons.context.LocalContextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The DockingManager registers and unregisters the local {@link Context}s of Dockables depending on the Dockables
 * registered in the specified {@link DockingPane} and set the active local {@link Context} depending on the active
 * Dockable set in the specified {@link DockingPane}.
 *
 * TODO: API ?
 *
 * TODO: good to implement AutoCloseable?
 *
 * @see DockingPane#getDockables()
 * @see DockingPane#activeDockableProperty()
 * @see ContextManager#putLocalContext(java.lang.Object, org.drombler.commons.context.Context)
 * @see ContextManager#removeLocalContext(java.lang.Object)
 * @see ContextManager#setLocalContextActive(java.lang.Object)
 *
 * @author puce
 */
public class DockingManager implements AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(DockingManager.class);

    private final ChangeListener<Node> activeDockableListener = (ObservableValue<? extends Node> ov, Node oldValue, Node newValue) -> {
        // TODO: newValue == null ?
        LOG.debug("Active Dockable changed!");
        handleActiveDockableSelected(newValue);
    };
    private final SetChangeListener<FXDockableEntry> dockablesListener = (SetChangeListener.Change<? extends FXDockableEntry> change) -> {
        if (change.wasAdded()) {
            LOG.debug("Dockable added: {}", change.getElementAdded());
            handleDockableAdded(change.getElementAdded().getDockable());
        } else if (change.wasRemoved()) {
            LOG.debug("Dockable removed: {}", change.getElementRemoved());
            handleDockableRemoved(change.getElementRemoved().getDockable());
        }
    };
    private final DockingPane dockingPane;
    private final ContextManager contextManager;

    /**
     * Creates a new instance of this class.
     *
     * TODO: support multiple, floating stages
     *
     * @param dockingPane the {@link DockingPane}
     * @param contextManager the {@link ContextManager}
     */
    public DockingManager(DockingPane dockingPane, final ContextManager contextManager) {
        this.dockingPane = dockingPane;
        this.contextManager = contextManager;

        this.dockingPane.activeDockableProperty().addListener(activeDockableListener);

        // TODO: handle vizualized/ unhandled Dockables
        this.dockingPane.getDockables().addListener(dockablesListener);

    }

    private void handleDockableAdded(Node dockable) {
        contextManager.putLocalContext(dockable, getLocalContext(dockable));
    }

    private void handleDockableRemoved(Node dockable) {
        contextManager.removeLocalContext(dockable);
    }

    private void handleActiveDockableSelected(Node dockable) {
        contextManager.setLocalContextActive(dockable);
    }

    private Context getLocalContext(Node dockable) {
        if (dockable instanceof LocalContextProvider) {
            return ((LocalContextProvider) dockable).getLocalContext();
        } else {
            return Contexts.emptyContext();
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void close()  {
        this.dockingPane.activeDockableProperty().removeListener(activeDockableListener);

        // TODO: handle vizualized/ unhandled Dockables
        this.dockingPane.getDockables().removeListener(dockablesListener);
    }

}
