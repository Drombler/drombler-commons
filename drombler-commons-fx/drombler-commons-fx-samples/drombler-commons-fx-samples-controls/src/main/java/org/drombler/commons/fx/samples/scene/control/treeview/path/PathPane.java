/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is provided by Drombler GmbH. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2020 Drombler GmbH. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.fx.samples.scene.control.treeview.path;

import java.nio.file.Path;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import org.drombler.commons.fx.fxml.FXMLLoaders;
import org.drombler.commons.fx.scene.control.RenderedTreeCellFactory;

/**
 *
 * @author puce
 */
public class PathPane extends GridPane {

    @FXML
    private TreeView<Path> treeView;

    private final ObjectProperty<Path> rootPath = new SimpleObjectProperty<>(this, "rootPath");

    public PathPane() {
        FXMLLoaders.loadRoot(this);

        RenderedTreeCellFactory<Path> renderedTreeCellFactory = new RenderedTreeCellFactory<>();
        renderedTreeCellFactory.registerDataRenderer(Path.class, new PathRenderer());

        treeView.setCellFactory(renderedTreeCellFactory);
        treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        rootPath.addListener((ov, oldVAlue, newValue) -> {
            treeView.setRoot(new PathTreeItem(newValue));
        });

    }

    public final Path getRootPath() {
        return rootPathProperty().get();
    }

    public final void setRootPath(Path rootPath) {
        rootPathProperty().set(rootPath);
    }

    public ObjectProperty<Path> rootPathProperty() {
        return rootPath;
    }
}
