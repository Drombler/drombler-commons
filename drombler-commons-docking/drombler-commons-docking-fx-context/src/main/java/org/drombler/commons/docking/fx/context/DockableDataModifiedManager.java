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
package org.drombler.commons.docking.fx.context;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javafx.collections.SetChangeListener;
import javafx.scene.Node;
import org.drombler.commons.action.command.Savable;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.ContextEvent;
import org.drombler.commons.context.ContextListener;
import org.drombler.commons.context.LocalContextProvider;
import org.drombler.commons.fx.docking.DockingPane;
import org.drombler.commons.fx.docking.FXDockableData;
import org.drombler.commons.fx.docking.FXDockableEntry;

/**
 * This manager listens for {@link Savable} in the local contexts of the Dockables and synchronizes the
 * {@link FXDockableData#modifiedProperty()} accordingly.
 *
 * @author puce
 */
public class DockableDataModifiedManager implements AutoCloseable {

    private final Map<Node, ContextListener> savableListeners = new HashMap<>();

    private final SetChangeListener<FXDockableEntry> dockablesListener = (SetChangeListener.Change<? extends FXDockableEntry> change) -> {
        if (change.wasAdded()) {
            final FXDockableEntry dockableEntry = change.getElementAdded();
            final Node dockable = dockableEntry.getDockable();
            if (dockable instanceof LocalContextProvider) {
                Context localContext = getLocalContext(dockable);
                final ContextListener savableListener = (ContextEvent event)
                        -> updateDockableDataModified(dockableEntry.getDockableData(), localContext);
                savableListeners.put(dockable, savableListener);
                localContext.addContextListener(Savable.class, savableListener);
                updateDockableDataModified(dockableEntry.getDockableData(), localContext);
            }
        }
        if (change.wasRemoved()) {
            final FXDockableEntry dockableEntry = change.getElementRemoved();
            final Node dockable = dockableEntry.getDockable();
            if (dockable instanceof LocalContextProvider) {
                ContextListener savableListener = savableListeners.remove(dockable);
                removeContextListener(dockable, savableListener);
            }
        }
    };

    private final DockingPane dockingPane;

    public DockableDataModifiedManager(DockingPane dockingPane) {
        this.dockingPane = dockingPane;
        this.dockingPane.getDockables().addListener(dockablesListener);
    }

    private void removeContextListener(Node dockable, ContextListener savableListener) {
        Context localContext = getLocalContext(dockable);
        localContext.removeContextListener(Savable.class, savableListener);
    }

    private Context getLocalContext(final Node dockable) {
        return ((LocalContextProvider) dockable).getLocalContext();
    }

    private void updateDockableDataModified(FXDockableData dockableData, Context localContext) {
        dockableData.setModified(localContext.find(Savable.class) != null);
    }

    @Override
    public void close() {
        // TODO: handle vizualized/ unhandled Dockables?
        dockingPane.getDockables().removeListener(dockablesListener);
        for (Iterator<Map.Entry<Node, ContextListener>> iterator = savableListeners.entrySet().iterator();
                iterator.hasNext();) {
            Map.Entry<Node, ContextListener> entry = iterator.next();
            removeContextListener(entry.getKey(), entry.getValue());
            iterator.remove();
        }
    }
}
