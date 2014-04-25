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
package org.drombler.commons.fx.samples.docking;

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
public class DockingSampleApplication extends Application {

    public static final String RIGHT_AREA_ID = "right";
    public static final String LEFT_AREA_ID = "left";
    public static final String BOTTOM_AREA_ID = "bottom";
    public static final String TOP_AREA_ID = "top";
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
        Menu windowMenu = new Menu("Window");
        menuBar.getMenus().add(windowMenu);

        addDockingAreas(dockingPane);

        DockablePreferencesManager<DockablePane> dockablePreferencesManager = new SimpleDockablePreferencesManager<>();
        registerDefaultDockablePreferences(dockablePreferencesManager);

        LeftTestPane leftTestPane = new LeftTestPane();
        leftTestPane.setTitle("Left");
        dockingPane.getDockables().add(new DockableEntry<>(leftTestPane, dockablePreferencesManager.
                getDockablePreferences(
                        leftTestPane)));
        MenuItem leftTestPaneMenuItem = createDockablePaneMenuItem(leftTestPane, dockingPane, dockablePreferencesManager);
        windowMenu.getItems().add(leftTestPaneMenuItem);

        RightTestPane rightTestPane = new RightTestPane();
        rightTestPane.setTitle("Right");
        dockingPane.getDockables().add(new DockableEntry<>(rightTestPane, dockablePreferencesManager.
                getDockablePreferences(
                        rightTestPane)));
        MenuItem rightTestPaneMenuItem = createDockablePaneMenuItem(rightTestPane, dockingPane,
                dockablePreferencesManager);
        windowMenu.getItems().add(rightTestPaneMenuItem);

        TopTestPane topTestPane = new TopTestPane();
        topTestPane.setTitle("Top");
        dockingPane.getDockables().add(new DockableEntry<>(topTestPane, dockablePreferencesManager.
                getDockablePreferences(
                        topTestPane)));
        MenuItem topTestPanePaneMenuItem = createDockablePaneMenuItem(topTestPane, dockingPane,
                dockablePreferencesManager);
        windowMenu.getItems().add(topTestPanePaneMenuItem);

        BottomTestPane bottomTestPane = new BottomTestPane();
        bottomTestPane.setTitle("Bottom");
        dockingPane.getDockables().add(new DockableEntry<>(bottomTestPane, dockablePreferencesManager.
                getDockablePreferences(
                        bottomTestPane)));
        MenuItem bottomTestPanePaneMenuItem = createDockablePaneMenuItem(bottomTestPane, dockingPane,
                dockablePreferencesManager);
        windowMenu.getItems().add(bottomTestPanePaneMenuItem);

        Scene scene = new Scene(borderPane, 1500, 1000);

        stage.setTitle("Docking Sample Application");
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
        registerDefaultDockablePreferences(dockablePreferencesManager, LeftTestPane.class, LEFT_AREA_ID, 20);
        registerDefaultDockablePreferences(dockablePreferencesManager, RightTestPane.class, RIGHT_AREA_ID, 20);
        registerDefaultDockablePreferences(dockablePreferencesManager, TopTestPane.class, TOP_AREA_ID, 20);
        registerDefaultDockablePreferences(dockablePreferencesManager, BottomTestPane.class, BOTTOM_AREA_ID, 20);
    }

    private void registerDefaultDockablePreferences(DockablePreferencesManager<DockablePane> dockablePreferencesManager,
            Class<?> type, String areaId, int position) {
        DockablePreferences leftDockablePreferences = new DockablePreferences();
        leftDockablePreferences.setAreaId(areaId);
        leftDockablePreferences.setPosition(position);
        dockablePreferencesManager.registerDefaultDockablePreferences(type, leftDockablePreferences);
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

        DockingAreaDescriptor topAreaDescriptor = new DockingAreaDescriptor();
        topAreaDescriptor.setId(TOP_AREA_ID);
        topAreaDescriptor.setParentPath(Arrays.asList(20, 40, 20));
        topAreaDescriptor.setPosition(20);
        topAreaDescriptor.setPermanent(false);
        LayoutConstraintsDescriptor topLayoutConstraintsDescriptor = new LayoutConstraintsDescriptor();
        topLayoutConstraintsDescriptor.setPrefHeight(100.0);
        topAreaDescriptor.setLayoutConstraints(topLayoutConstraintsDescriptor);

        DockingAreaDescriptor bottomAreaDescriptor = new DockingAreaDescriptor();
        bottomAreaDescriptor.setId(BOTTOM_AREA_ID);
        bottomAreaDescriptor.setParentPath(Arrays.asList(20, 40, 80));
        bottomAreaDescriptor.setPosition(20);
        bottomAreaDescriptor.setPermanent(false);
        LayoutConstraintsDescriptor bottomLayoutConstraintsDescriptor = new LayoutConstraintsDescriptor();
        bottomLayoutConstraintsDescriptor.setPrefHeight(100.0);
        bottomAreaDescriptor.setLayoutConstraints(bottomLayoutConstraintsDescriptor);

        DockingAreaDescriptor leftAreaDescriptor = new DockingAreaDescriptor();
        leftAreaDescriptor.setId(LEFT_AREA_ID);
        leftAreaDescriptor.setParentPath(Arrays.asList(20, 20));
        leftAreaDescriptor.setPosition(20);
        leftAreaDescriptor.setPermanent(false);
        LayoutConstraintsDescriptor leftLayoutConstraintsDescriptor = new LayoutConstraintsDescriptor();
        leftLayoutConstraintsDescriptor.setPrefWidth(200.0);
        leftAreaDescriptor.setLayoutConstraints(leftLayoutConstraintsDescriptor);

        DockingAreaDescriptor rightAreaDescriptor = new DockingAreaDescriptor();
        rightAreaDescriptor.setId(RIGHT_AREA_ID);
        rightAreaDescriptor.setParentPath(Arrays.asList(20, 80));
        rightAreaDescriptor.setPosition(20);
        rightAreaDescriptor.setPermanent(false);
        LayoutConstraintsDescriptor rightLayoutConstraintsDescriptor = new LayoutConstraintsDescriptor();
        rightLayoutConstraintsDescriptor.setPrefWidth(200.0);
        rightAreaDescriptor.setLayoutConstraints(rightLayoutConstraintsDescriptor);

        dockingPane.getDockingAreaDescriptors().add(centerAreaDescriptor);
        dockingPane.getDockingAreaDescriptors().add(topAreaDescriptor);
        dockingPane.getDockingAreaDescriptors().add(bottomAreaDescriptor);
        dockingPane.getDockingAreaDescriptors().add(leftAreaDescriptor);
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
