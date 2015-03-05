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
package org.drombler.commons.fx.docking;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Control;
import org.drombler.commons.client.docking.DockingAreaDescriptor;
import org.drombler.commons.fx.docking.impl.skin.Stylesheets;

/**
 * The DockingPane splits up the content area into any number of Docking Areas. The Docking Areas can be resized using
 * the dividers. Each Docking Area can hold any number of Dockable Panes, which are layed out as Tabs.
 *
 * Wiki: http://wiki.drombler.org/DockingFramework
 *
 * @author puce
 */
public class DockingPane extends Control {//extends BorderPane {// GridPane {

    private static final String DEFAULT_STYLE_CLASS = "docking-pane";

    private final ObservableSet<DockingAreaDescriptor> dockingAreaDescriptors = FXCollections.observableSet();
    private final ObservableSet<FXDockableEntry> dockables = FXCollections.observableSet();
    /**
     * The active Dockable.
     */
    private final ObjectProperty<Node> activeDockable = new SimpleObjectProperty<>(this, "activeDockable");
    // TODO: needed? useful?
    private final Set<String> dockingAreaIds = new HashSet<>();
    // TODO: needed? useful?
    private final Map<Node, FXDockableEntry> dockableEntryMap = new HashMap<>();

    /**
     * Creates a new instance of this class.
     */
    public DockingPane() {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);
        dockingAreaDescriptors.addListener((SetChangeListener.Change<? extends DockingAreaDescriptor> change) -> {
            if (change.wasAdded()) {
                dockingAreaIds.add(change.getElementAdded().getId());
            } else if (change.wasRemoved()) {
                dockingAreaIds.remove(change.getElementRemoved().getId());
            }
        });
        dockables.addListener((SetChangeListener.Change<? extends FXDockableEntry> change) -> {
            if (change.wasAdded()) {
//                if (dockingAreaIds.contains(dockableEntry.getDockablePreferences().getAreaId())) { // TODO: needed?
                dockableEntryMap.put(change.getElementAdded().getDockable(), change.getElementAdded());
            } else if (change.wasRemoved()) {
                dockableEntryMap.remove(change.getElementRemoved().getDockable());
            }
        });
        setFocusTraversable(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserAgentStylesheet() {
        return Stylesheets.getDefaultStylesheet();
    }

    // TODO: needed? useful?
    public void removeDockable(Node dockable) {
        FXDockableEntry dockableEntry = dockableEntryMap.get(dockable);
        if (dockableEntry != null) {
            removeDockable(dockableEntry);
        }
    }

    private void removeDockable(FXDockableEntry dockableEntry) {
        dockables.remove(dockableEntry);
    }

    /**
     * Gets the {@link DockingAreaDescriptor}s.
     *
     * @return the DockingAreaDescriptors
     */
    public ObservableSet<DockingAreaDescriptor> getDockingAreaDescriptors() {
        return dockingAreaDescriptors;
    }

    /**
     * Gets the Dockables.
     *
     * @return the Dockables.
     */
    public ObservableSet<FXDockableEntry> getDockables() {
        return dockables;
    }

    public final Node getActiveDockable() {
        return activeDockableProperty().get();
    }

    public final void setActiveDockable(Node dockable) {
        activeDockableProperty().set(dockable);
    }

    public ObjectProperty<Node> activeDockableProperty() {
        return activeDockable;
    }
}
