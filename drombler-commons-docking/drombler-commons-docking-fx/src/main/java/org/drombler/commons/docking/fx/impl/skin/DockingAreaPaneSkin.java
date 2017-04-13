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
import java.util.Iterator;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.drombler.commons.docking.fx.DockableCloseRequestEvent;
import org.drombler.commons.docking.fx.FXDockableData;
import org.drombler.commons.docking.fx.FXDockableEntry;
import org.drombler.commons.docking.fx.impl.DockingAreaPane;
import org.softsmithy.lib.util.PositionableAdapter;

/**
 *
 * @author puce
 */
public class DockingAreaPaneSkin implements Skin<DockingAreaPane> {

    private static final String DOCKABLE_MODIFIED_STYLE_CLASS = "dockable-modified";

    private final Map<FXDockableData, ChangeListener<? super Boolean>> dockableDataModifiedListeners = new HashMap<>();
    private DockingAreaPane control;
    private TabPane tabPane = new TabPane();
    private final TabManager tabManager = new TabManager();

    private final ListChangeListener<PositionableAdapter<FXDockableEntry>> dockablesChangeListener = change -> {
        while (change.next()) {
            if (change.wasPermutated()) {
                for (int i = change.getFrom(); i < change.getTo(); ++i) {
                    // TODO: ???
                }
            } else if (change.wasUpdated()) {
                // TODO: ???
            } else if (change.wasRemoved()) {
                Iterator<? extends PositionableAdapter<FXDockableEntry>> iterator = change.getRemoved().iterator();
                for (int i = change.getFrom() + change.getRemovedSize() - 1; i >= change.getFrom(); i--) {
                    removeTab(i, iterator.next());
                }
            } else if (change.wasAdded()) {
                for (int index = change.getFrom(); index < change.getTo(); index++) {
                    addTab(change.getList().get(index));
                }
            } else if (change.wasReplaced()) {
                // TODO: ???
            }
        }
    };

    private final ListChangeListener<Tab> tabsChangeListener = change -> {
        while (change.next()) {
            if (change.wasPermutated()) {
                for (int i = change.getFrom(); i < change.getTo(); ++i) {
                    // TODO: ???
                }
            } else if (change.wasUpdated()) {
                // TODO: ???
            } else if (change.wasRemoved()) {
                Iterator<? extends Tab> iterator = change.getRemoved().iterator();
                for (int i = change.getFrom() + change.getRemovedSize() - 1; i >= change.getFrom(); i--) {
                    removeDockable(i, iterator.next().getContent());
                }
                if (change.getFrom() == tabPane.getSelectionModel().getSelectedIndex()) {
                    // selectedIndex did not change, but selectedItem changed -> trigger selectedItem change
                    control.getSelectionModel().select(tabPane.getSelectionModel().getSelectedIndex());
                }
            } else if (change.wasAdded()) {
                // TODO: ???
            } else if (change.wasReplaced()) {
                // TODO: ???
            }
        }

    };
    private final ChangeListener<Number> dockableSelectedIndexChangeListener = (ov, oldValue, newValue)
            -> tabPane.getSelectionModel().select(newValue.intValue());

    private final ChangeListener<Number> tabSelectedIndexChangeListener = (ov, oldValue, newValue)
            -> control.getSelectionModel().select(newValue.intValue());

    public DockingAreaPaneSkin(DockingAreaPane control) {
        this.control = control;
        tabPane.setFocusTraversable(false);

        control.getDockables().addListener(dockablesChangeListener);

        tabPane.getTabs().addListener(tabsChangeListener);

        control.getSelectionModel().selectedIndexProperty().addListener(dockableSelectedIndexChangeListener);
        tabPane.getSelectionModel().selectedIndexProperty().addListener(tabSelectedIndexChangeListener);

        int selectedIndex = control.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0 && !control.getDockables().isEmpty()) {
            selectedIndex = 0;
        }
        // might change selection
        control.getDockables().forEach(dockable -> addTab(dockable));

        if (selectedIndex >= 0 && selectedIndex != control.getSelectionModel().getSelectedIndex()) {
            tabPane.getSelectionModel().select(selectedIndex);
        }

        control.getScene().getWindow().addEventHandler(DockableCloseRequestEvent.DOCKABLE_CLOSE_REQUEST, tabManager.getOnDockableCloseRequestHandler());
//        tabPane.focusedProperty().addListener(new ChangeListener<Boolean>() {
//
//            @Override
//            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newVlaue) {
//                if (newVlaue) {
//                    Tab tab = tabPane.getSelectionModel().getSelectedItem();
//                    if (tab != null) {
//                        System.out.println("Selected Tab Changed: " + tab.getText());
//                    } else {
//                        System.out.println("Selected Tab Changed: empty");
//                    }
//                }
//            }
//        });
    }

    @Override
    public DockingAreaPane getSkinnable() {
        return control;
    }

    @Override
    public Node getNode() {
        return tabPane;
    }

    @Override
    public void dispose() {
        control.getDockables().removeListener(dockablesChangeListener);
        tabPane.getTabs().removeListener(tabsChangeListener);
        control.getSelectionModel().selectedIndexProperty().removeListener(dockableSelectedIndexChangeListener);
        tabPane.getSelectionModel().selectedIndexProperty().removeListener(tabSelectedIndexChangeListener);

        control = null;
        tabPane = null;
    }

    private void addTab(PositionableAdapter<FXDockableEntry> dockable) {
        final Tab tab = new Tab();
        final FXDockableData dockableData = dockable.getAdapted().getDockableData();
        tab.textProperty().bind(dockableData.titleProperty());
        tab.graphicProperty().bind(dockableData.graphicProperty());
        tab.tooltipProperty().bind(dockableData.tooltipProperty());
        tab.contextMenuProperty().bind(dockableData.contextMenuProperty());
        tab.setContent(dockable.getAdapted().getDockable());

        tab.setOnCloseRequest(tabManager.createOnCloseRequestHandler(tab, dockable.getAdapted(), control));
        observeDockableData(dockableData, tab);

        tabPane.getTabs().add(control.getDockables().indexOf(dockable), tab);
    }

    private void removeTab(int index, PositionableAdapter<FXDockableEntry> dockableEntry) {
        FXDockableData dockableData = dockableEntry.getAdapted().getDockableData();
        ChangeListener<? super Boolean> listener = dockableDataModifiedListeners.remove(dockableData);
        dockableData.modifiedProperty().removeListener(listener);
        if (tabPane.getTabs().size() > index && tabPane.getTabs().get(index).getContent().equals(dockableEntry.getAdapted().getDockable())) {
            tabPane.getTabs().remove(index);
        }
    }

    private void removeDockable(int index, Node dockable) {
        if (control.getDockables().size() > index && control.getDockables().get(index).getAdapted().getDockable().equals(dockable)) {
            control.removeDockable(index);
        }
    }

    private void observeDockableData(FXDockableData dockableData, Tab tab) {
        ChangeListener<? super Boolean> dockableDataModifiedListener = (observable, oldValue, newValue)
                -> updateStyleClass(tab, newValue);
        dockableData.modifiedProperty().addListener(dockableDataModifiedListener);
        dockableDataModifiedListeners.put(dockableData, dockableDataModifiedListener);
        updateStyleClass(tab, dockableData.isModified());
    }

    private void updateStyleClass(Tab tab, boolean modified) {
        if (modified) {
            if (!tab.getStyleClass().contains(DOCKABLE_MODIFIED_STYLE_CLASS)) {
                tab.getStyleClass().add(DOCKABLE_MODIFIED_STYLE_CLASS);
            }
        } else {
            if (tab.getStyleClass().contains(DOCKABLE_MODIFIED_STYLE_CLASS)) {
                tab.getStyleClass().remove(DOCKABLE_MODIFIED_STYLE_CLASS);
            }
        }
    }
}
