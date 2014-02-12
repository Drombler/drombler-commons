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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.drombler.commons.client.docking.DockableEntry;
import org.drombler.commons.client.docking.DockingAreaContainer;
import org.drombler.commons.client.docking.DockingAreaContainerDockingAreaEvent;
import org.drombler.commons.client.docking.DockingAreaContainerListener;
import org.drombler.commons.client.docking.DockingAreaDescriptor;
import org.drombler.commons.fx.docking.impl.skin.Stylesheets;

/**
 *
 * @author puce
 */
public class DockingPane extends Control implements DockingAreaContainer<DockablePane> {//extends BorderPane {// GridPane {

    private static final String DEFAULT_STYLE_CLASS = "docking-pane";

    private final ChangeListener<Node> focusOwnerChangeListener = new FocusOwnerChangeListener();
    private final List<DockingAreaContainerListener<DockablePane>> listeners = new ArrayList<>();

//    private final ChangeListener<DockablePane> dockableSelectionChangeListener = new DockableSelectionChangeListener();
//    private final ProxyContext applicationContext = new ProxyContext();
//    private final ProxyContext activeContext = new ProxyContext();
//    private final Context applicationContextWrapper = new ContextWrapper(applicationContext);
//    private final Context activeContextWrapper = new ContextWrapper(activeContext);
    private final ObservableSet<DockingAreaDescriptor> dockingAreaDescriptors = FXCollections.observableSet();
    private final ObservableSet<DockableEntry<? extends DockablePane>> dockables = FXCollections.observableSet();
    private final ObjectProperty<DockablePane> activeDockable = new SimpleObjectProperty<>(this, "activeDockable");
    private final Set<String> dockingAreaIds = new HashSet<>();
    private final Map<DockablePane, DockableEntry<? extends DockablePane>> dockableEntryMap = new HashMap<>();

    public DockingPane() {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);
        sceneProperty().addListener(new ChangeListener<Scene>() {

            @Override
            public void changed(ObservableValue<? extends Scene> ov, Scene oldValue, Scene newValue) {
                if (oldValue != null) {
                    oldValue.focusOwnerProperty().removeListener(focusOwnerChangeListener);
                }
                if (newValue != null) {
                    newValue.focusOwnerProperty().addListener(focusOwnerChangeListener);
                }
            }
        });
    }

    @Override
    protected String getUserAgentStylesheet() {
        return Stylesheets.getDefaultStylesheet();
    }

    @Override
    public boolean addDockingArea(DockingAreaDescriptor dockingAreaDescriptor) {
//        System.out.println(DockingPane.class.getName() + ": added docking area: " + dockingArea.getAreaId());

        if (dockingAreaIds.add(dockingAreaDescriptor.getId())) {
            dockingAreaDescriptors.add(dockingAreaDescriptor);
            fireDockingAreaAdded(dockingAreaDescriptor.getId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addDockable(DockableEntry<? extends DockablePane> dockableEntry) {
        if (dockingAreaIds.contains(dockableEntry.getDockablePreferences().getAreaId())) { // TODO: needed?
            dockables.add(dockableEntry);
            dockableEntryMap.put(dockableEntry.getDockable(), dockableEntry);
            return true;
        } else {
            return false;
        }
    }

    public void removeDockable(DockablePane dockable) {
        DockableEntry<? extends DockablePane> dockableEntry = dockableEntryMap.remove(dockable);
        if (dockableEntry != null) {
            dockables.remove(dockableEntry);
        }
    }

    public ObservableSet<DockingAreaDescriptor> getDockingAreaDescriptors() {
        return dockingAreaDescriptors;
    }

    public ObservableSet<DockableEntry<? extends DockablePane>> getDockables() {
        return dockables;
    }

    public final DockablePane getActiveDockable() {
        return activeDockableProperty().get();
    }

    public final void setActiveDockable(DockablePane dockable) {
        activeDockableProperty().set(dockable);
    }

    public ObjectProperty<DockablePane> activeDockableProperty() {
        return activeDockable;
    }

    @Override
    public void addDockingAreaContainerListener(DockingAreaContainerListener<DockablePane> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeDockingAreaContainerListener(DockingAreaContainerListener<DockablePane> listener) {
        listeners.remove(listener);
    }

    private void fireDockingAreaAdded(String dockingAreaId) {
        DockingAreaContainerDockingAreaEvent<DockablePane> event = new DockingAreaContainerDockingAreaEvent<>(this,
                dockingAreaId);
        for (DockingAreaContainerListener<DockablePane> listener : listeners) {
            listener.dockingAreaAdded(event);
        }
    }
//
//    private void removeContext(DockablePane dockable) {
//        applicationContext.removeContext(dockable.getContext());
//        activeContext.removeContext(dockable.getContext());
//    }
//
//    public Context getApplicationContext() {
//        return applicationContextWrapper;
//    }
//
//    public Context getActiveContext() {
//        return activeContextWrapper;
//    }



//    public ChangeListener<Node> getFocusOwnerChangeListener() {
//        return focusOwnerChangeListener;
//    }
//    public ChangeListener<DockablePane> getDockableSelectionChangeListener() {
//        return dockableSelectionChangeListener;
//    }
//    private class DockableSelectionChangeListener implements ChangeListener<DockablePane> {
//                    @Override
//        public void changed(ObservableValue<? extends DockablePane> ov, DockablePane oldValue, DockablePane newValue) {
//            if (newValue != null) {
////                    System.out.println("Active Context Changed 1: " + newValue.getAdapted().getTitle());
//                activeContext.setContexts(newValue.getContext());
//            }
//        }
//    }
    private class FocusOwnerChangeListener implements ChangeListener<Node> {

        @Override
        public void changed(ObservableValue<? extends Node> ov, Node oldValue, Node newValue) {
            Node currentNode = newValue;
            if (currentNode instanceof TabPane) {
                Tab selectedItem = ((TabPane) currentNode).getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    currentNode = selectedItem.getContent();
                }
            }
            while (currentNode != null) {
                if (currentNode instanceof DockablePane) {
//                    System.out.println("Active Context Changed 2: " + ((DockablePane) currentNode).getTitle());
//                    activeContext.setContexts(((DockablePane) currentNode).getContext());
                    setActiveDockable((DockablePane) currentNode);
                    break;
                } else {
                    currentNode = currentNode.getParent();
                }
            }
        }
    }
}
