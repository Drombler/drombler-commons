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
package org.drombler.commons.docking.fx.impl.skin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.SetChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.layout.BorderPane;
import org.drombler.commons.docking.DockingAreaDescriptor;
import org.drombler.commons.docking.fx.DockingPane;
import org.drombler.commons.docking.fx.FXDockableEntry;
import org.drombler.commons.docking.fx.impl.DockingAreaPane;
import org.drombler.commons.docking.fx.impl.DockingSplitPane;
import org.drombler.commons.docking.spi.DockingAreaManager;
import org.drombler.commons.docking.spi.SplitLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softsmithy.lib.util.PositionableAdapter;

/**
 *
 * @author puce
 */
public class DockingPaneSkin implements Skin<DockingPane> {

    private static final Logger LOG = LoggerFactory.getLogger(DockingPaneSkin.class);

    private DockingPane control;
    private BorderPane pane = new BorderPane();
    private DockingSplitPane rootSplitPane = new DockingSplitPane(0, SplitLevel.ROOT);
    private final DockingAreaManager<DockingAreaPane> rootDockingAreaManager = new DockingAreaManager<>(null, 0,
            SplitLevel.ROOT);
    private final Map<String, DockingAreaPane> dockingAreaPanes = new HashMap<>();
    private final ChangeListener<Node> focusOwnerChangeListener = new FocusOwnerChangeListener();
    private final SetChangeListener<DockingAreaDescriptor> dockingAreaDescriptorSetChangeListener = change -> {
        if (change.wasAdded()) {
            addDockingArea(change.getElementAdded());
        } else if (change.wasRemoved()) {
// TODO: remove DockingArea
        }
    };

    private final SetChangeListener<FXDockableEntry> dockableEntrySetChangeListener = change -> {
        if (change.wasAdded()) {
            addDockable(change.getElementAdded());
        } else if (change.wasRemoved()) {
            removeDockable(change.getElementRemoved());
        }
    };

    public DockingPaneSkin(DockingPane control) {
        this.control = control;
        pane.setCenter(rootSplitPane);
        this.control.getDockingAreaDescriptors().addListener(dockingAreaDescriptorSetChangeListener);
        this.control.getDockables().addListener(dockableEntrySetChangeListener);

        this.control.getDockingAreaDescriptors().forEach(dockingAreaDescriptor -> addDockingArea(dockingAreaDescriptor));
        this.control.getDockables().forEach(this::addDockable);

        this.control.sceneProperty().addListener((ov, oldValue, newValue) -> {
            if (oldValue != null) {
                oldValue.focusOwnerProperty().removeListener(focusOwnerChangeListener);
            }
            if (newValue != null) {
                newValue.focusOwnerProperty().addListener(focusOwnerChangeListener);
            }
        });
        this.control.getScene().focusOwnerProperty().addListener(focusOwnerChangeListener);
        this.control.activeDockableProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                LOG.debug("Selecting newly active Dockable: '{}'...", newValue.getDockableData().getTitle());
                dockingAreaPanes.values().stream()
                        .filter(dockingAreaPane -> dockingAreaPane.containsDockable(newValue))
                        .findFirst()
                        .ifPresent(dockingAreaPane -> {
                            dockingAreaPane.getDockables().stream()
                                    .filter(adapter -> adapter.getAdapted().equals(newValue))
                                    .findFirst()
                                    .ifPresent(adapter -> {
                                LOG.debug("Select newly active Dockable: '{}'!", newValue.getDockableData().getTitle());
                                        dockingAreaPane.getSelectionModel().select(adapter);
                            });
                        });
            }
        });
    }

    @Override
    public DockingPane getSkinnable() {
        return control;
    }

    @Override
    public Node getNode() {
        return pane;
    }

    @Override
    public void dispose() {
        control.getDockables().removeListener(dockableEntrySetChangeListener);
        control.getDockingAreaDescriptors().removeListener(dockingAreaDescriptorSetChangeListener);
        control.getScene().focusOwnerProperty().removeListener(focusOwnerChangeListener);
        control = null;
        pane = null;
        rootSplitPane = null;
    }

    private void addDockingArea(DockingAreaDescriptor dockingAreaDescriptor) {
        LOG.debug("Added docking area ({}): {}", dockingAreaDescriptor.getKind(), dockingAreaDescriptor.getId());
        DockingAreaPane dockingArea = createDockingArea(dockingAreaDescriptor);

        dockingArea.getSelectionModel().selectedItemProperty().
                addListener((ov, oldValue, newValue) -> {
                    if (newValue != null) {
//                        control.setActiveDockable(newValue.getAdapted().getDockable());
                        LOG.debug("Request focus for newly selected Dockable: '{}'!",
                                newValue.getAdapted().getDockableData().getTitle());
                        newValue.getAdapted().getDockable().requestFocus();
                    }
                });

        dockingArea.getDockables().addListener(
                (ListChangeListener.Change<? extends PositionableAdapter<FXDockableEntry>> change) -> {
                    while (change.next()) {
                        if (change.wasPermutated()) {
                            for (int i = change.getFrom(); i < change.getTo(); ++i) {
                                // TODO: ???
                            }
                        } else if (change.wasUpdated()) {
                            // TODO: ???
                        } else if (change.wasRemoved()) {
                            removeDockables(change.getRemoved());
                        } else if (change.wasAdded()) {
                            // TODO: ???
                        } else if (change.wasReplaced()) {
                            // TODO: ???
                        }
                    }
                });
        dockingAreaPanes.put(dockingArea.getAreaId(), dockingArea);
        rootDockingAreaManager.addDockingArea(dockingAreaDescriptor.getParentPath(), dockingArea);
        handleDockingArea(dockingArea);
    }

    private void removeDockables(List<? extends PositionableAdapter<FXDockableEntry>> dockableEntries) {
        dockableEntries.forEach(dockablePane -> control.removeDockable(dockablePane.getAdapted().getDockable()));
    }

    private DockingAreaPane createDockingArea(DockingAreaDescriptor dockingAreaDescriptor) {
        DockingAreaPane dockingAreaPane = new DockingAreaPane(dockingAreaDescriptor.getId(),
                dockingAreaDescriptor.getKind(), dockingAreaDescriptor.getPosition(),
                dockingAreaDescriptor.isPermanent(), dockingAreaDescriptor.getLayoutConstraints());
        return dockingAreaPane;
    }

    private void addDockable(FXDockableEntry dockableEntry) {
        DockingAreaPane dockingArea = getDockingArea(dockableEntry.getDockablePreferences().getAreaId());
        if (dockingArea != null) { // TODO: needed?
            dockingArea.addDockable(new PositionableAdapter<>(dockableEntry,
                    dockableEntry.getDockablePreferences().getPosition()));
//            this.control.setActiveDockable(dockableEntry.getDockable());
                    LOG.debug("Dockable '{}' added to the Docking Area '{}'.", dockableEntry.getDockableData().getTitle(),
                    dockableEntry.getDockablePreferences().getAreaId());
        }
    }

    private void removeDockable(FXDockableEntry dockableEntry) {
        dockingAreaPanes.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(dockingAreaPane -> dockingAreaPane.containsDockable(dockableEntry))
                .findFirst()
                .ifPresent(dockingAreaPane -> dockingAreaPane.removeDockable(dockableEntry));
    }

    private DockingAreaPane getDockingArea(String areaId) {
        return dockingAreaPanes.get(areaId);
    }

    private void handleDockingArea(DockingAreaPane dockingArea) {
        LOG.debug("Adding docking area: {}", dockingArea.getAreaId());
        if (!dockingArea.isPermanent()) {
            dockingArea.getDockables().addListener(new DockingAreaChangeListener(dockingArea));
        }
        if (dockingArea.isVisualizable()) {
            rootSplitPane.addDockingArea(dockingArea);
        }
    }

    private class DockingAreaChangeListener implements ListChangeListener<PositionableAdapter<FXDockableEntry>> {

        private final DockingAreaPane dockingArea;

        public DockingAreaChangeListener(DockingAreaPane dockingArea) {
            this.dockingArea = dockingArea;
        }

        @Override
        public void onChanged(ListChangeListener.Change<? extends PositionableAdapter<FXDockableEntry>> change) {
            if (dockingArea.isVisualizable() && !dockingArea.isVisualized()) {
                LOG.debug("Add visualizable docking area: {}", dockingArea.getAreaId());
                rootSplitPane.addDockingArea(dockingArea);
            } else if (!dockingArea.isVisualizable() && dockingArea.isVisualized()) {
                LOG.debug("Remove non-visualizable docking area: {}", dockingArea.getAreaId());
                rootSplitPane.removeDockingArea(dockingArea);
            }
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof DockingAreaChangeListener)) {
                return false;
            }
            DockingAreaChangeListener listener = (DockingAreaChangeListener) obj;
            return Objects.equals(dockingArea, listener.dockingArea);
        }

        @Override
        public int hashCode() {
            return Objects.hash(dockingArea);
        }
    }

    private class FocusOwnerChangeListener implements ChangeListener<Node> {

        @Override
        public synchronized void changed(ObservableValue<? extends Node> ov, Node oldValue, Node newValue) {
            Node currentNode = newValue;
            LOG.debug("New focusOwner: {}", currentNode);
            while (currentNode != null && currentNode != control) {
                if (currentNode instanceof DockingAreaPane) {
                    LOG.debug("DockingAreaPane found: {}!", currentNode);
                    PositionableAdapter<FXDockableEntry> selectedItem = ((DockingAreaPane) currentNode).
                            getSelectionModel().getSelectedItem();
                    if (selectedItem != null) {
                        LOG.debug("Dockable found!");
                        control.setActiveDockable(selectedItem.getAdapted());
                        LOG.debug("Changed active Dockable: '{}'!", selectedItem.getAdapted().getDockableData().
                                getTitle());
                    }
                    break;
                } else {
                    currentNode = currentNode.getParent();
                }
            }
        }
    }

//    private class FocusOwnerChangeListener implements ChangeListener<Node> {
//
//        @Override
//        public void changed(ObservableValue<? extends Node> ov, Node oldValue, Node newValue) {
//            Node currentNode = newValue;
//            if (currentNode instanceof TabPane) {
//                Tab selectedItem = ((TabPane) currentNode).getSelectionModel().getSelectedItem();
//                if (selectedItem != null) {
//                    currentNode = selectedItem.getContent();
//
//                }
//            }
//            while (currentNode != null) {
//                if (currentNode instanceof DockablePane) {
////                    System.out.println("Active Context Changed 2: " + ((DockablePane) currentNode).getTitle());
////                    activeContext.setContexts(((DockablePane) currentNode).getContext());
//                    setActiveDockable((DockablePane) currentNode);
//                    break;
//                } else {
//                    currentNode = currentNode.getParent();
//                }
//            }
//        }
//    }
}
