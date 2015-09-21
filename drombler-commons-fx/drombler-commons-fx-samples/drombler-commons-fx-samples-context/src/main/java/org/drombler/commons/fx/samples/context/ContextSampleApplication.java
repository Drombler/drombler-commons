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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.drombler.commons.client.docking.DockablePreferences;
import org.drombler.commons.client.docking.DockablePreferencesManager;
import org.drombler.commons.client.docking.DockingAreaDescriptor;
import org.drombler.commons.client.docking.LayoutConstraintsDescriptor;
import org.drombler.commons.client.docking.SimpleDockablePreferencesManager;
import org.drombler.commons.context.ContextManager;
import org.drombler.commons.docking.fx.context.DockableDataModifiedManager;
import org.drombler.commons.docking.fx.context.DockingManager;
import org.drombler.commons.fx.docking.DockingPane;
import org.drombler.commons.fx.docking.FXDockableData;
import org.drombler.commons.fx.docking.FXDockableEntry;

/**
 *
 * @author puce
 */
public class ContextSampleApplication extends Application {

    public static final String RIGHT_AREA_ID = "right";
    public static final String CENTER_AREA_ID = "center";

    private DockingManager dockingManager;
    private DockableDataModifiedManager dockableDataModifiedManager;

    @Override
    public void start(Stage stage) {
        BorderPane borderPane = new BorderPane();
        DockingPane dockingPane = new DockingPane();
        ContextManager contextManager = new ContextManager();
        dockingManager = new DockingManager(dockingPane, contextManager);
        dockableDataModifiedManager = new DockableDataModifiedManager(dockingPane);
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

        DockablePreferencesManager<Node> dockablePreferencesManager = new SimpleDockablePreferencesManager<>();
        registerDefaultDockablePreferences(dockablePreferencesManager);

        FXDockableData contextProviderPane1DockableData = new FXDockableData();
        ContextProviderPane contextProviderPane1 = new ContextProviderPane(new Sample("Sample 1"));
        contextProviderPane1.setDockableData(contextProviderPane1DockableData);
        final FXDockableEntry contextProviderPane1FXDockableEntry = new FXDockableEntry(contextProviderPane1,
                contextProviderPane1DockableData,
                dockablePreferencesManager.getDockablePreferences(contextProviderPane1));
        dockingPane.getDockables().add(contextProviderPane1FXDockableEntry);
        MenuItem contextProviderPane1MenuItem = createDockablePaneMenuItem(contextProviderPane1FXDockableEntry,
                dockingPane);
        windowMenu.getItems().add(contextProviderPane1MenuItem);

        FXDockableData contextProviderPane2DockableData = new FXDockableData();
        ContextProviderPane contextProviderPane2 = new ContextProviderPane(new Sample("Sample 2"));
        contextProviderPane2.setDockableData(contextProviderPane2DockableData);
        final DockablePreferences dockablePreferences = dockablePreferencesManager.
                getDockablePreferences(contextProviderPane2);
        dockablePreferences.setPosition(40);
        final FXDockableEntry contextProviderPane2FXDockableEntry = new FXDockableEntry(contextProviderPane2,
                contextProviderPane2DockableData,
                dockablePreferences);
        dockingPane.getDockables().add(contextProviderPane2FXDockableEntry);
        MenuItem contextProviderPane2MenuItem = createDockablePaneMenuItem(contextProviderPane2FXDockableEntry,
                dockingPane);
        windowMenu.getItems().add(contextProviderPane2MenuItem);

        ContextConsumerPane contextConsumerPane = new ContextConsumerPane();
        FXDockableData contextConsumerPaneDockableData = new FXDockableData();
        contextConsumerPaneDockableData.setTitle("Right");
        final FXDockableEntry contextConsumerPaneDockableDataFXDockableEntry = new FXDockableEntry(contextConsumerPane,
                contextConsumerPaneDockableData,
                dockablePreferencesManager.getDockablePreferences(contextConsumerPane));
        dockingPane.getDockables().add(contextConsumerPaneDockableDataFXDockableEntry);
        MenuItem contextConsumerPaneMenuItem = createDockablePaneMenuItem(contextConsumerPaneDockableDataFXDockableEntry,
                dockingPane);
        windowMenu.getItems().add(contextConsumerPaneMenuItem);

        // Set active context
        contextConsumerPane.setActiveContext(contextManager.getActiveContext());

        Scene scene = new Scene(borderPane, 1500, 1000);

        stage.setTitle("Context Sample Application");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        dockingManager.close();
        dockableDataModifiedManager.close();
        super.stop();
    }

    private MenuItem createDockablePaneMenuItem(FXDockableEntry dockableEntry, DockingPane dockingPane) {
        MenuItem openDockablePaneMenuItem = new MenuItem(dockableEntry.getDockableData().getTitle());
        openDockablePaneMenuItem.setOnAction(new OpenDockablePaneActionHandler(dockingPane, dockableEntry));
        return openDockablePaneMenuItem;
    }

    private void registerDefaultDockablePreferences(DockablePreferencesManager<Node> dockablePreferencesManager) {
        registerDefaultDockablePreferences(dockablePreferencesManager, ContextProviderPane.class, CENTER_AREA_ID, 20);
        registerDefaultDockablePreferences(dockablePreferencesManager, ContextConsumerPane.class, RIGHT_AREA_ID, 20);
    }

    private void registerDefaultDockablePreferences(DockablePreferencesManager<Node> dockablePreferencesManager,
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
        centerAreaDescriptor.setLayoutConstraints(LayoutConstraintsDescriptor.flexible());
        DockingAreaDescriptor rightAreaDescriptor = new DockingAreaDescriptor();
        rightAreaDescriptor.setId(RIGHT_AREA_ID);
        rightAreaDescriptor.setParentPath(Arrays.asList(20, 80));
        rightAreaDescriptor.setPosition(20);
        rightAreaDescriptor.setPermanent(false);
        LayoutConstraintsDescriptor rightLayoutConstraintsDescriptor = LayoutConstraintsDescriptor.prefWidth(200.0);
        rightAreaDescriptor.setLayoutConstraints(rightLayoutConstraintsDescriptor);

        dockingPane.getDockingAreaDescriptors().add(centerAreaDescriptor);
        dockingPane.getDockingAreaDescriptors().add(rightAreaDescriptor);
    }

    private static class OpenDockablePaneActionHandler implements EventHandler<ActionEvent> {

        private final DockingPane dockingPane;
        private final FXDockableEntry dockableEntry;

        public OpenDockablePaneActionHandler(DockingPane dockingPane, FXDockableEntry dockableEntry) {
            this.dockingPane = dockingPane;
            this.dockableEntry = dockableEntry;
        }

        @Override
        public void handle(ActionEvent t) {
            dockingPane.getDockables().add(dockableEntry);
        }
    }

}
