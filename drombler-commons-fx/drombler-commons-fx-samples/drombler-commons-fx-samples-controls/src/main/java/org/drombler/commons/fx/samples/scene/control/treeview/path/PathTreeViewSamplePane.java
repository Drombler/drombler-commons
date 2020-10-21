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

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import org.drombler.commons.action.fx.ButtonUtils;
import org.drombler.commons.action.fx.MenuItemUtils;
import org.drombler.commons.fx.fxml.FXMLLoaders;
import org.drombler.commons.fx.scene.control.StatusBar;

/**
 *
 * @author puce
 */
public class PathTreeViewSamplePane extends GridPane implements AutoCloseable {

    private final ChooseDirectoryAction chooseDirectoryAction = new ChooseDirectoryAction();
    private final ChooseFileAction chooseFileAction = new ChooseFileAction();

    private FileSystem fileSystem;

    @FXML
    private MenuBar menuBar;

    @FXML
    private ToolBar toolBar;

    @FXML
    private PathPane pathPane;

    @FXML
    private StatusBar statusBar;

    public PathTreeViewSamplePane() {
        FXMLLoaders.loadRoot(this);

        chooseDirectoryAction.selectedDirectoryPathProperty().addListener((ov, oldValue, newValue) -> {
            closeFileSystem();
            pathPane.setRootPath(newValue);
        });

        chooseFileAction.selectedFilePathProperty().addListener((ov, oldValue, newValue) -> {
            try {
                this.fileSystem = FileSystems.newFileSystem(newValue, null);
                Path rootDirPath = fileSystem.getRootDirectories().iterator().next();
                System.out.println("FileSystem: " + fileSystem);
                System.out.println("rootDirPath: " + rootDirPath);
                pathPane.setRootPath(rootDirPath);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        Button chooseDirectoryButton = new Button();
        ButtonUtils.configureToolbarButton(chooseDirectoryButton, chooseDirectoryAction, 24);
        toolBar.getItems().add(chooseDirectoryButton);

        Button chooseFileButton = new Button();
        ButtonUtils.configureToolbarButton(chooseFileButton, chooseFileAction, 24);
        toolBar.getItems().add(chooseFileButton);

        Menu fileMenu = new Menu("_File");

        MenuItem chooseDirectoryMenuItem = new MenuItem();
        MenuItemUtils.configureMenuItem(chooseDirectoryMenuItem, chooseDirectoryAction, 16);
        fileMenu.getItems().add(chooseDirectoryMenuItem);

        MenuItem chooseFileMenuItem = new MenuItem();
        MenuItemUtils.configureMenuItem(chooseFileMenuItem, chooseFileAction, 16);
        fileMenu.getItems().add(chooseFileMenuItem);

        menuBar.getMenus().add(fileMenu);
    }

    @Override
    public void close() {
        closeFileSystem();
    }

    public void closeFileSystem() {
        if (fileSystem != null && fileSystem.isOpen()) {
            try {
                fileSystem.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
