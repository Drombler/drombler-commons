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
 * Copyright 2016 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.docking.fx.context.impl;

import java.util.Iterator;
import java.util.SortedSet;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanPropertyBase;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import org.drombler.commons.action.command.Savable;
import org.drombler.commons.context.Contexts;
import org.drombler.commons.docking.fx.FXDockableEntry;
import org.drombler.commons.docking.fx.FXDockableEntryRenderer;
import org.drombler.commons.fx.fxml.FXMLLoaders;
import org.drombler.commons.fx.scene.control.RenderedListCellFactory;

/**
 *
 * @author puce
 */
public class SaveModifiedDockablesPane extends BorderPane {


    private final EmptyProperty empty = new EmptyProperty();

    @FXML
    private ListView<FXDockableEntry> modifiedDockablesListView;

    public SaveModifiedDockablesPane(SortedSet<FXDockableEntry> modifiedDockableEntries) {
        FXMLLoaders.loadRoot(this);
        modifiedDockablesListView.setCellFactory(new RenderedListCellFactory<>(new FXDockableEntryRenderer(16)));
        modifiedDockablesListView.getItems().setAll(modifiedDockableEntries);
        modifiedDockablesListView.getItems().addListener((ListChangeListener.Change<? extends FXDockableEntry> c) -> {
            if (modifiedDockablesListView.getItems().isEmpty()) {
                empty.set(true);
            }
        });
        if (modifiedDockablesListView.getItems().isEmpty()) {
            empty.set(true);
        }
        modifiedDockablesListView.getSelectionModel().select(0);
        modifiedDockablesListView.requestFocus();
    }

    public boolean isEmpty() {
        return emptyProperty().get();
    }

    public ReadOnlyBooleanProperty emptyProperty() {
        return empty;
    }

    @FXML
    private void save(ActionEvent event) {
        Node selectedDockable = modifiedDockablesListView.getSelectionModel().getSelectedItem().getDockable();
        Contexts.find(selectedDockable, Savable.class).save();
        if (Contexts.find(selectedDockable, Savable.class) == null) {
            removeSelectedFromList();
        }
    }

    private void removeSelectedFromList() {
        modifiedDockablesListView.getItems().remove(modifiedDockablesListView.getSelectionModel().getSelectedIndex());
    }

    @FXML
    private void discard(ActionEvent event) {
        removeSelectedFromList();
    }

    @FXML
    private void saveAll(ActionEvent event) {
        for (Iterator<FXDockableEntry> iterator = modifiedDockablesListView.getItems().iterator(); iterator.hasNext();) {
            Node dockable = iterator.next().getDockable();
            Contexts.find(dockable, Savable.class).save();
            if (Contexts.find(dockable, Savable.class) == null) {
                iterator.remove();
            }
        }
    }

    @FXML
    private void discardAll(ActionEvent event) {
        modifiedDockablesListView.getItems().clear();
    }


    private class EmptyProperty extends ReadOnlyBooleanPropertyBase {

        private boolean empty = false;

        @Override
        public final boolean get() {
            return empty;
        }

        private void set(boolean newValue) {
            if (empty != newValue) {
                empty = newValue;
                fireValueChangedEvent();
            }
        }

        @Override
        public Object getBean() {
            return SaveModifiedDockablesPane.this;
        }

        @Override
        public String getName() {
            return "empty";
        }
    }
}
