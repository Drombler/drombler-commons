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
 * Copyright 2014 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.fx.samples.context;

import java.util.Arrays;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.drombler.commons.client.docking.DockableEntry;
import org.drombler.commons.client.docking.DockablePreferences;
import org.drombler.commons.client.docking.DockablePreferencesManager;
import org.drombler.commons.client.docking.DockingAreaDescriptor;
import org.drombler.commons.client.docking.LayoutConstraintsDescriptor;
import org.drombler.commons.client.docking.SimpleDockablePreferencesManager;
import org.drombler.commons.context.ContextManager;
import org.drombler.commons.fx.docking.DockablePane;
import org.drombler.commons.fx.docking.DockingManager;
import org.drombler.commons.fx.docking.DockingPane;

/**
 *
 * @author puce
 */
public class ContextSampleApplication extends Application {

    public static final String RIGHT_AREA_ID = "right";
    public static final String CENTER_AREA_ID = "center";

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = new BorderPane();
        DockingPane dockingPane = new DockingPane();
        ContextManager contextManager = new ContextManager();
        DockingManager dockingManager = new DockingManager(dockingPane, contextManager);
        borderPane.setCenter(dockingPane);

        MenuBar menuBar = new MenuBar();
        borderPane.setTop(menuBar);
        Menu fileMenu = new Menu("File");
        final MenuItem saveMenuItem = new MenuItem("Save");
        final SaveAction saveAction = new SaveAction(saveMenuItem);
        saveAction.setActiveContext(contextManager.getActiveContext());
        saveMenuItem.setOnAction(saveAction);
        fileMenu.getItems().add(saveMenuItem);
        final MenuItem saveAllMenuItem = new MenuItem("Save All");
        final SaveAllAction saveAllAction = new SaveAllAction(saveAllMenuItem);
        saveAllAction.setApplicationContext(contextManager.getApplicationContext());
        saveAllMenuItem.setOnAction(saveAllAction);
        fileMenu.getItems().add(saveAllMenuItem);
        Menu windowMenu = new Menu("Window");
        menuBar.getMenus().add(fileMenu);
        menuBar.getMenus().add(windowMenu);

        addDockingAreas(dockingPane);

        DockablePreferencesManager<DockablePane> dockablePreferencesManager = new SimpleDockablePreferencesManager<>();
        registerDefaultDockablePreferences(dockablePreferencesManager);

        ContextProviderPane contextProviderPane1 = new ContextProviderPane(new Sample("Sample 1"));
        dockingPane.getDockables().add(new DockableEntry<>(contextProviderPane1, dockablePreferencesManager.
                getDockablePreferences(contextProviderPane1)));
        MenuItem contextProviderPane1MenuItem = createDockablePaneMenuItem(contextProviderPane1, dockingPane,
                dockablePreferencesManager);
        windowMenu.getItems().add(contextProviderPane1MenuItem);

        ContextProviderPane contextProviderPane2 = new ContextProviderPane(new Sample("Sample 2"));
        final DockablePreferences dockablePreferences = dockablePreferencesManager.
                getDockablePreferences(contextProviderPane2);
        dockablePreferences.setPosition(40);
        dockingPane.getDockables().add(new DockableEntry<>(contextProviderPane2, dockablePreferences));
        MenuItem contextProviderPane2MenuItem = createDockablePaneMenuItem(contextProviderPane2, dockingPane,
                dockablePreferencesManager);
        windowMenu.getItems().add(contextProviderPane2MenuItem);

        ContextConsumerPane contextConsumerPane = new ContextConsumerPane();
        contextConsumerPane.setTitle("Right");
        dockingPane.getDockables().add(new DockableEntry<>(contextConsumerPane, dockablePreferencesManager.
                getDockablePreferences(
                        contextConsumerPane)));
        MenuItem contextConsumerPaneMenuItem = createDockablePaneMenuItem(contextConsumerPane, dockingPane,
                dockablePreferencesManager);
        windowMenu.getItems().add(contextConsumerPaneMenuItem);

        // Set active context
        contextConsumerPane.setActiveContext(contextManager.getActiveContext());

        Scene scene = new Scene(borderPane, 1500, 1000);

        stage.setTitle("Context Sample Application");
        stage.setScene(scene);
        stage.show();
    }

    private MenuItem createDockablePaneMenuItem(DockablePane dockablePane, DockingPane dockingPane,
            DockablePreferencesManager<DockablePane> dockablePreferencesManager) {
        MenuItem openDockablePaneMenuItem = new MenuItem(dockablePane.getTitle());
        openDockablePaneMenuItem.setOnAction(new OpenDockablePaneActionHandler(dockingPane, dockablePane,
                dockablePreferencesManager));
        return openDockablePaneMenuItem;
    }

    private void registerDefaultDockablePreferences(DockablePreferencesManager<DockablePane> dockablePreferencesManager) {
        registerDefaultDockablePreferences(dockablePreferencesManager, ContextProviderPane.class, CENTER_AREA_ID, 20);
        registerDefaultDockablePreferences(dockablePreferencesManager, ContextConsumerPane.class, RIGHT_AREA_ID, 20);
    }

    private void registerDefaultDockablePreferences(DockablePreferencesManager<DockablePane> dockablePreferencesManager,
            Class<?> type, String areaId, int position) {
        DockablePreferences dockablePreferences = new DockablePreferences();
        dockablePreferences.setAreaId(areaId);
        dockablePreferences.setPosition(position);
        dockablePreferencesManager.registerDefaultDockablePreferences(type, dockablePreferences);
    }

    private void addDockingAreas(DockingPane dockingPane) {
        // TODO: hide DockingAreaPane from API
        DockingAreaDescriptor centerAreaDescriptor = new DockingAreaDescriptor();
        centerAreaDescriptor.setId(CENTER_AREA_ID);
        centerAreaDescriptor.setParentPath(Arrays.asList(20, 40, 50));
        centerAreaDescriptor.setPosition(20);
        centerAreaDescriptor.setPermanent(true);
        // TODO: set default
        centerAreaDescriptor.setLayoutConstraints(new LayoutConstraintsDescriptor());
        DockingAreaDescriptor rightAreaDescriptor = new DockingAreaDescriptor();
        rightAreaDescriptor.setId(RIGHT_AREA_ID);
        rightAreaDescriptor.setParentPath(Arrays.asList(20, 80));
        rightAreaDescriptor.setPosition(20);
        rightAreaDescriptor.setPermanent(false);
        LayoutConstraintsDescriptor rightLayoutConstraintsDescriptor = new LayoutConstraintsDescriptor();
        rightLayoutConstraintsDescriptor.setPrefWidth(200.0);
        rightAreaDescriptor.setLayoutConstraints(rightLayoutConstraintsDescriptor);

        dockingPane.getDockingAreaDescriptors().add(centerAreaDescriptor);
        dockingPane.getDockingAreaDescriptors().add(rightAreaDescriptor);
    }

    private static class OpenDockablePaneActionHandler implements EventHandler<ActionEvent> {

        private final DockingPane dockingPane;
        private final DockablePane dockablePane;
        private final DockablePreferencesManager<DockablePane> dockablePreferencesManager;

        public OpenDockablePaneActionHandler(DockingPane dockingPane, DockablePane dockablePane,
                DockablePreferencesManager<DockablePane> dockablePreferencesManager) {
            this.dockingPane = dockingPane;
            this.dockablePane = dockablePane;
            this.dockablePreferencesManager = dockablePreferencesManager;
        }

        @Override
        public void handle(ActionEvent t) {
            dockingPane.getDockables().add(new DockableEntry<>(dockablePane, dockablePreferencesManager.
                    getDockablePreferences(dockablePane)));
        }
    }

}
