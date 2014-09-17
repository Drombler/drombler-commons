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
 * TODO: API ?
 *
 * @author puce
 */
public class DockingManager {

    private static final Logger LOG = LoggerFactory.getLogger(DockingManager.class);

    private final DockingPane dockingPane;
    private final ContextManager contextManager;

    /**
     * TODO: support multiple, floating stages
     *
     * @param dockingPane
     * @param contextManager
     */
    public DockingManager(DockingPane dockingPane, final ContextManager contextManager) {
        this.dockingPane = dockingPane;
        this.contextManager = contextManager;

        // TODO: remove listeners
        this.dockingPane.activeDockableProperty().addListener(
                (ObservableValue<? extends Node> ov, Node oldValue, Node newValue) -> {
            // TODO: newValue == null ?
            LOG.debug("Active Dockable changed!");
            contextManager.setLocalContextActive(newValue);
        });

        // TODO: handle vizualized/ unhandled Dockables
        this.dockingPane.getDockables().addListener(
                (SetChangeListener.Change<? extends FXDockableEntry> change) -> {
                    if (change.wasAdded()) {
                        LOG.debug("Dockable added: {}", change.getElementAdded());
                        handleDockableAdded(change.getElementAdded().getDockable());
                    } else if (change.wasRemoved()) {
                        LOG.debug("Dockable removed: {}", change.getElementAdded());
                        handleDockableRemoved(change.getElementRemoved().getDockable());
                    }
                });

    }

    private void handleDockableAdded(Node dockable) {
        contextManager.putLocalContext(dockable, getLocalContext(dockable));
    }

    private void handleDockableRemoved(Node dockable) {
        contextManager.removeLocalContext(dockable);
    }

    private Context getLocalContext(Node dockable) {
        if (dockable instanceof LocalContextProvider) {
            return ((LocalContextProvider) dockable).getLocalContext();
        } else {
            return Contexts.emptyContext();
        }
    }

}
