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
package org.drombler.commons.docking.fx.context;

import java.util.Set;
import javafx.collections.SetChangeListener;
import javafx.scene.Node;
import org.drombler.commons.context.Context;
import org.drombler.commons.docking.DockingAreaDescriptor;
import org.drombler.commons.docking.context.AbstractDockingAreaContainerAdapter;
import org.drombler.commons.docking.fx.DockingPane;
import org.drombler.commons.docking.fx.FXDockableData;
import org.drombler.commons.docking.fx.FXDockableDataFactory;
import org.drombler.commons.docking.fx.FXDockableEntry;
import org.drombler.commons.docking.fx.FXDockableEntryFactory;

/**
 *
 * @author puce
 */
public class DockingPaneDockingAreaContainerAdapter extends AbstractDockingAreaContainerAdapter<Node, FXDockableData, FXDockableEntry> {

    private final DockingPane dockingPane;

    public DockingPaneDockingAreaContainerAdapter(DockingPane dockingPane) {
        super(new FXDockableEntryFactory(), new FXDockableDataFactory()); // TODO remove?

        this.dockingPane = dockingPane;

        dockingPane.getDockingAreaDescriptors().addListener(
                (SetChangeListener.Change<? extends DockingAreaDescriptor> change) -> {
                    if (change.wasAdded()) {
                        fireDockingAreaAdded(change.getElementAdded().getId());
                    } else {
                        if (change.wasRemoved()) {
                            fireDockingAreaRemoved(change.getElementRemoved().getId());
                        }
                    }
                });
        dockingPane.getDockables().addListener(
                (SetChangeListener.Change<? extends FXDockableEntry> change) -> {
                    if (change.wasAdded()) {
                        fireDockableAdded(change.getElementAdded());
                    } else {
                        if (change.wasRemoved()) {
                            fireDockableRemoved(change.getElementRemoved());
                        }
                    }
                });
    }


    /**
     * {@inheritDoc }
     */
    @Override
    public boolean addDockingArea(DockingAreaDescriptor dockingAreaDescriptor) {
        return dockingPane.getDockingAreaDescriptors().add(dockingAreaDescriptor);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean addDockable(FXDockableEntry dockableEntry, boolean active, Context... implicitLocalContexts) {
        boolean added = dockingPane.getDockables().add(dockableEntry);
        addImplicitLocalContext(dockableEntry.getDockable(), implicitLocalContexts);
        if (active) {
            dockingPane.setActiveDockable(dockableEntry);
        }
        return added;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getDefaultEditorAreaId() {
        return dockingPane.getDefaultEditorAreaId();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Set<FXDockableEntry> getDockables() {
        return dockingPane.getDockables();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setActiveDockable(FXDockableEntry dockableEntry) {
        dockingPane.setActiveDockable(dockableEntry);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public FXDockableEntry getActiveDockable() {
        return dockingPane.getActiveDockable();
    }




}
