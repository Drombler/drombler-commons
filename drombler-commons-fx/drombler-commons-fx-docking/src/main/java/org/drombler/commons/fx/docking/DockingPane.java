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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.drombler.commons.client.docking.DockablePreferences;
import org.drombler.commons.client.docking.DockingAreaContainer;
import org.drombler.commons.client.docking.DockingAreaContainerDockingAreaEvent;
import org.drombler.commons.client.docking.DockingAreaContainerListener;
import org.drombler.commons.client.docking.DockingAreaDescriptor;
import org.drombler.commons.client.docking.SplitLevel;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.ContextWrapper;
import org.drombler.commons.context.ProxyContext;
import org.drombler.commons.fx.docking.impl.DockingAreaManager;
import org.drombler.commons.fx.docking.impl.DockingAreaPane;
import org.drombler.commons.fx.docking.impl.skin.Stylesheets;
import org.softsmithy.lib.util.PositionableAdapter;

/**
 *
 * @author puce
 */
public class DockingPane extends Control implements DockingAreaContainer<DockablePane> {//extends BorderPane {// GridPane {

    private static final String DEFAULT_STYLE_CLASS = "docking-pane";
    private final Map<String, DockingAreaPane> dockingAreaPanes = new HashMap<>();
    private final DockingAreaManager rootDockingAreaManager = new DockingAreaManager(null, 0, SplitLevel.ROOT);
    private final List<DockingAreaContainerListener<DockingAreaPane, DockablePane>> listeners = new ArrayList<>();
    private final Map<String, List<DockableEntry>> unresolvedDockables = new HashMap<>();
    private final ChangeListener<Node> focusOwnerChangeListener = new FocusOwnerChangeListener();
    private final ProxyContext applicationContext = new ProxyContext();
    private final ProxyContext activeContext = new ProxyContext();
    private final Context applicationContextWrapper = new ContextWrapper(applicationContext);
    private final Context activeContextWrapper = new ContextWrapper(activeContext);

    public DockingPane() {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);
    }

    @Override
    protected String getUserAgentStylesheet() {
        return Stylesheets.getDefaultStylesheet();
    }

    @Override
    public void addDockingArea(DockingAreaDescriptor dockingAreaDescriptor) {
//        System.out.println(DockingPane.class.getName() + ": added docking area: " + dockingArea.getAreaId());
        DockingAreaPane dockingArea = createDockingArea(dockingAreaDescriptor);

        dockingArea.getSelectionModel().selectedItemProperty().
                addListener(new ChangeListener<PositionableAdapter<DockablePane>>() {
                    @Override
                    public void changed(ObservableValue<? extends PositionableAdapter<DockablePane>> ov,
                            PositionableAdapter<DockablePane> oldValue, PositionableAdapter<DockablePane> newValue) {
                        if (newValue != null) {
//                    System.out.println("Active Context Changed 1: " + newValue.getAdapted().getTitle());
                            activeContext.setContexts(newValue.getAdapted().getContext());
                        }
                    }
                });

        dockingArea.getDockables().addListener(new ListChangeListener<PositionableAdapter<DockablePane>>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends PositionableAdapter<DockablePane>> change) {
                while (change.next()) {
                    if (change.wasPermutated()) {
                        for (int i = change.getFrom(); i < change.getTo(); ++i) {
                            // TODO: ???
                        }
                    } else if (change.wasUpdated()) {
                        // TODO: ???
                    } else if (change.wasRemoved()) {
                        removeContexts(change.getRemoved());
                    } else if (change.wasAdded()) {
                        // TODO: ???
                    } else if (change.wasReplaced()) {
                        // TODO: ???
                    }
                }
            }
        });
//        dockingArea.focusedProperty().addListener(new ChangeListener<Boolean>() {
//
//            @Override
//            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newVlaue) {
//                if (newVlaue) {
//                    PositionableAdapter<DockablePane> selectedItem = dockingArea.getSelectionModel().getSelectedItem();
//                    if (selectedItem != null) {
//                        System.out.println("Selected Dockable Changed 2: " + selectedItem.getAdapted().getTitle());
//                    } else {
//                        System.out.println("Selected Dockable Changed 2: empty");
//                    }
//                }
//            }
//        });
        dockingAreaPanes.put(dockingArea.getAreaId(), dockingArea);
        rootDockingAreaManager.addDockingArea(dockingAreaDescriptor.getPath(), dockingArea);
        resolveUnresolvedDockables(dockingArea.getAreaId());
        DockingAreaContainerDockingAreaEvent<DockingAreaPane, DockablePane> event
                = new DockingAreaContainerDockingAreaEvent<>(this, dockingArea.getAreaId(), dockingArea);
        fireDockingPaneChangeEvent(event);
    }

    private DockingAreaPane createDockingArea(DockingAreaDescriptor dockingAreaDescriptor) {
        DockingAreaPane dockingAreaPane = new DockingAreaPane(dockingAreaDescriptor.getId(),
                dockingAreaDescriptor.getPosition(), dockingAreaDescriptor.isPermanent());
        dockingAreaPane.setLayoutConstraints(dockingAreaDescriptor.getLayoutConstraints());
        return dockingAreaPane;
    }
//    private DockingAreaPane getCurrentDockingArea() {
//        DockingSplitPane splitPane = rootSplitPane;
//        DockingSplitPane currentSplitPane = splitPane;
//        while (splitPane != null) {
//            currentSplitPane = splitPane;
//            splitPane = splitPane.getParentSplitPane();
//        }
//        return (DockingAreaPane) ((!currentSplitPane.getItems().isEmpty()) ? currentSplitPane.getItems().get(0) : null);
//    }
//    private void splitArea(DockingAreaPane currentDockingAreaPane, DockingAreaPane dockingAreaPane) {
//        DockingSplitPane currentParentSplitPane = currentDockingAreaPane.getParentSplitPane();
//        Side side = getSide(currentDockingAreaPane, dockingAreaPane);
//        int currentIndex = currentParentSplitPane.getItems().indexOf(currentDockingAreaPane);
//        if ((side.isHorizontal() && currentParentSplitPane.getOrientation().equals(Orientation.HORIZONTAL))
//                || side.isVertical() && currentParentSplitPane.getOrientation().equals(Orientation.VERTICAL)) {
//            if (BEFORE_SIDES.contains(side)) {
//                currentParentSplitPane.add(currentIndex, dockingAreaPane);
//            } else {
//                currentParentSplitPane.add(currentIndex + 1, dockingAreaPane);
//            }
//        } else {
//            DockingSplitPane splitPane = new DockingSplitPane();
//            splitPane.setOrientation(side.isHorizontal() ? Orientation.HORIZONTAL : Orientation.VERTICAL);
//            if (BEFORE_SIDES.contains(side)) {
//                splitPane.addAll(Arrays.asList(dockingAreaPane, currentDockingAreaPane));
//            } else {
//                splitPane.addAll(Arrays.asList(currentDockingAreaPane, dockingAreaPane));
//            }
//            currentParentSplitPane.set(currentIndex, splitPane);
//        }
//    }
//
//    private Side getSide(DockingAreaPane currentDockingAreaPane, DockingAreaPane dockingAreaPane) {
//        return Side.BOTTOM;
//    }

    @Override
    public void addDockable(DockablePane dockablePane, DockablePreferences dockablePreferences) {
        DockingAreaPane dockingArea = getDockingArea(dockablePreferences.getAreaId());
        if (dockingArea != null) { // TODO: needed?
            boolean docked = dockingArea.addDockable(new PositionableAdapter<>(dockablePane,
                    dockablePreferences.getPosition()));
            if (docked) {
                applicationContext.addContext(dockablePane.getContext());
            }
        } else {
            if (!unresolvedDockables.containsKey(dockablePreferences.getAreaId())) {
                unresolvedDockables.put(dockablePreferences.getAreaId(), new ArrayList<DockableEntry>());
            }
            unresolvedDockables.get(dockablePreferences.getAreaId()).add(new DockableEntry(dockablePane,
                    dockablePreferences));
        }
    }

    private void removeContexts(List<? extends PositionableAdapter<DockablePane>> removedDockables) {
        for (PositionableAdapter<DockablePane> adapter : removedDockables) {
            applicationContext.removeContext(adapter.getAdapted().getContext());
            activeContext.removeContext(adapter.getAdapted().getContext());
        }
    }

    /*
     * package-private, for unit testing
     */
    // TODO remove DockingAreaPane in signature
    DockingAreaPane getDockingArea(String areaId) {
        return dockingAreaPanes.get(areaId);
    }

    // TODO remove DockingAreaPane in signature
    public void addDockingAreaContainerListener(DockingAreaContainerListener<DockingAreaPane, DockablePane> listener) {
        listeners.add(listener);
    }

    // TODO remove DockingAreaPane in signature
    public void removeDockingAreaContainerListener(DockingAreaContainerListener<DockingAreaPane, DockablePane> listener) {
        listeners.remove(listener);
    }

    private void fireDockingPaneChangeEvent(DockingAreaContainerDockingAreaEvent<DockingAreaPane, DockablePane> event) {
        for (DockingAreaContainerListener<DockingAreaPane, DockablePane> listener : listeners) {
            listener.dockingAreaAdded(event);
        }
    }

    private void resolveUnresolvedDockables(String areaId) {
        if (unresolvedDockables.containsKey(areaId)) {
            List<DockableEntry> dockableEntries = unresolvedDockables.remove(areaId);
            for (DockableEntry dockableEntry : dockableEntries) {
                addDockable(dockableEntry.getDockablePane(), dockableEntry.dockablePreferences);
            }
        }
    }
//    public static class DockingAreaEntry {
//
//        private final DockingAreaPane dockingArea;
//        private final List<Integer> preferredPath;
//
//        public DockingAreaEntry(DockingAreaPane dockingArea, List<Integer> preferredPath) {
//            this.dockingArea = dockingArea;
//            this.preferredPath = preferredPath;
//        }
//
//        /**
//         * @return the dockingArea
//         */
//        public DockingAreaPane getDockingArea() {
//            return dockingArea;
//        }
//
//        /**
//         * @return the preferredPath
//         */
//        public List<Integer> getPreferredPath() {
//            return Collections.unmodifiableList(preferredPath);
//        }
//    }

    // TODO remove DockingAreaPane in signature
    public Collection<DockingAreaPane> getAllDockingAreas() {
        return dockingAreaPanes.values();
    }

    public Context getApplicationContext() {
        return applicationContextWrapper;
    }

    public Context getActiveContext() {
        return activeContextWrapper;
    }

    public ChangeListener<Node> getFocusOwnerChangeListener() {
        return focusOwnerChangeListener;
    }

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
                    activeContext.setContexts(((DockablePane) currentNode).getContext());
                    break;
                } else {
                    currentNode = currentNode.getParent();
                }
            }
        }
    }

    private static class DockableEntry {

        private final DockablePane dockablePane;
        private final DockablePreferences dockablePreferences;

        public DockableEntry(DockablePane dockablePane, DockablePreferences dockablePreferences) {
            this.dockablePane = dockablePane;
            this.dockablePreferences = dockablePreferences;
        }

        /**
         * @return the dockablePane
         */
        public DockablePane getDockablePane() {
            return dockablePane;
        }

        /**
         * @return the dockablePreferences
         */
        public DockablePreferences getDockablePreferences() {
            return dockablePreferences;
        }

    }
}
