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
package org.drombler.commons.fx.docking.impl.skin;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.drombler.commons.fx.docking.DockablePane;
import org.drombler.commons.fx.docking.impl.DockingAreaPane;
import org.softsmithy.lib.util.PositionableAdapter;

/**
 *
 * @author puce
 */
public class DockingAreaPaneSkin implements Skin<DockingAreaPane> {

    private DockingAreaPane control;
    private TabPane tabPane = new TabPane();

    public DockingAreaPaneSkin(DockingAreaPane control) {
        this.control = control;
//        Tab tab = new Tab();
//        tab.setText("Test");
//        tab.setContent(new Label("Hello world!"));
//        tabPane.getTabs().add(tab);

        control.getDockables().addListener(new ListChangeListener<PositionableAdapter<DockablePane>>() {

            @Override
            public void onChanged(Change<? extends PositionableAdapter<DockablePane>> change) {
                while (change.next()) {
                    if (change.wasPermutated()) {
                        for (int i = change.getFrom(); i < change.getTo(); ++i) {
                            // TODO: ???
                        }
                    } else if (change.wasUpdated()) {
                        // TODO: ???
                    } else if (change.wasRemoved()) {
                        for (PositionableAdapter<DockablePane> remitem : change.getRemoved()) {
                            // TODO: ???
                        }
                    } else if (change.wasAdded()) {
                        for (int index = change.getFrom(); index < change.getTo(); index++) {
                            addTab(change.getList().get(index));
                        }
                    } else if (change.wasReplaced()) {
                        // TODO: ???
                    }
                }
            }
        });

        tabPane.getTabs().addListener(new ListChangeListener<Tab>() {

            @Override
            public void onChanged(Change<? extends Tab> change) {
                while (change.next()) {
                    if (change.wasPermutated()) {
                        for (int i = change.getFrom(); i < change.getTo(); ++i) {
                            // TODO: ???
                        }
                    } else if (change.wasUpdated()) {
                        // TODO: ???
                    } else if (change.wasRemoved()) {
                        DockingAreaPaneSkin.this.control.removeDockable(change.getFrom());
                    } else if (change.wasAdded()) {
                        // TODO: ???
                    } else if (change.wasReplaced()) {
                        // TODO: ???
                    }
                }
            }
        });

        for (PositionableAdapter<DockablePane> dockable : control.getDockables()) {
            addTab(dockable);
        }

        control.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                tabPane.getSelectionModel().select(newValue.intValue());
            }
        });

        tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                DockingAreaPaneSkin.this.control.getSelectionModel().select(newValue.intValue());
            }
        });

        tabPane.getSelectionModel().select(control.getSelectionModel().getSelectedIndex());
        
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
        control = null;
        tabPane = null;
    }

    private void addTab(PositionableAdapter<DockablePane> dockable) {
        Tab tab = new Tab();
        tab.textProperty().bind(dockable.getAdapted().titleProperty());
        tab.graphicProperty().bind(dockable.getAdapted().graphicProperty());
        tab.contextMenuProperty().bind(dockable.getAdapted().contextMenuProperty());
        tab.setContent(dockable.getAdapted());
        tabPane.getTabs().add(control.getDockables().indexOf(dockable), tab);
    }
}