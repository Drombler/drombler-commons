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
import org.drombler.commons.client.docking.DockableEntry;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.ContextManager;
import org.drombler.commons.context.Contexts;
import org.drombler.commons.context.LocalContextProvider;

/**
 * TODO: API ?
 *
 * @author puce
 */
public class DockingManager {

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


        this.dockingPane.activeDockableProperty().addListener(new ChangeListener<DockablePane>() {

            @Override
            public void changed(ObservableValue<? extends DockablePane> ov, DockablePane oldValue, DockablePane newValue) {
                // TODO: newValue == null ?
                contextManager.setLocalContextActive(newValue);
            }
        });

        // TODO: handle vizualized/ unhandled Dockables
        this.dockingPane.getDockables().addListener(new SetChangeListener<DockableEntry<? extends DockablePane>>() {

            @Override
            public void onChanged(SetChangeListener.Change<? extends DockableEntry<? extends DockablePane>> change) {
                if (change.wasAdded()) {
                    handleDockableAdded(change.getElementAdded().getDockable());
                } else if (change.wasRemoved()) {
                    handleDockableRemoved(change.getElementRemoved().getDockable());
                }
            }
        });

    }
    private void handleDockableAdded(DockablePane dockable) {
        contextManager.putLocalContext(dockable, getLocalContext(dockable));
    }

    private void handleDockableRemoved(DockablePane dockable) {
        contextManager.removeLocalContext(dockable);
    }

    private Context getLocalContext(DockablePane dockable) {
        if (dockable instanceof LocalContextProvider) {
            return ((LocalContextProvider) dockable).getLocalContext();
        } else {
            return Contexts.emptyContext();
        }
    }

}
