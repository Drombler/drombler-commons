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
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import org.drombler.commons.fx.scene.control.RenderedTreeCellFactory;

/**
 *
 * @author puce
 */
public class PathTreeViewSample extends Application {
    private PathTreeViewSamplePane root;

    @Override
    public void start(Stage primaryStage) {
        this.root = new PathTreeViewSamplePane();

        Scene scene = new Scene(root, 600, 250);
        primaryStage.setTitle("Path TreeView Sample");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TreeView<Path> createPathTreeView() {
        TreeView<Path> treeView = new TreeView<>();

        RenderedTreeCellFactory<Path> renderedTreeCellFactory = new RenderedTreeCellFactory<>();
        renderedTreeCellFactory.registerDataRenderer(Path.class, new PathRenderer());

        treeView.setCellFactory(renderedTreeCellFactory);
//        treeView.setRoot(new PathTreeItem(path));

        treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        return treeView;
    }

    @Override
    public void stop() throws Exception {
        if (root != null) {
            root.close();
        }
        super.stop();
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
