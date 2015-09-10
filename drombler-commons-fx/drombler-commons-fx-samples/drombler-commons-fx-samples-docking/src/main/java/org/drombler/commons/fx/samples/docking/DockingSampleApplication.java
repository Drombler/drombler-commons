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
import org.drombler.commons.client.util.MnemonicUtils;
import org.drombler.commons.client.util.ResourceBundleUtils;
import org.drombler.commons.context.ContextManager;
import org.drombler.commons.docking.fx.context.DockingManager;
import org.drombler.commons.fx.docking.DockingPane;
import org.drombler.commons.fx.docking.FXDockableData;
import org.drombler.commons.fx.docking.FXDockableEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author puce
 */
public class DockingSampleApplication extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(DockingSampleApplication.class);

    public static final String RIGHT_AREA_ID = "right";
    public static final String LEFT_AREA_ID = "left";
    public static final String BOTTOM_AREA_ID = "bottom";
    public static final String TOP_AREA_ID = "top";
    public static final String CENTER_AREA_ID = "center";

    private int sampleCounter = 0;
    private DockingManager dockingManager;

    @Override
    public void start(Stage stage)  {
        BorderPane borderPane = new BorderPane();
        final DockingPane dockingPane = new DockingPane();
        ContextManager contextManager = new ContextManager();
        dockingManager = new DockingManager(dockingPane, contextManager);
        borderPane.setCenter(dockingPane);

        MenuBar menuBar = new MenuBar();
        borderPane.setTop(menuBar);
        Menu windowMenu = new Menu(ResourceBundleUtils.getPackageResourceStringPrefixed(DockingSampleApplication.class,
                "%Menu.Window.displayName"));
        menuBar.getMenus().add(windowMenu);

        addDockingAreas(dockingPane);

        final DockablePreferencesManager<Node> dockablePreferencesManager = new SimpleDockablePreferencesManager<>();
        registerDefaultDockablePreferences(dockablePreferencesManager);

        FXDockableData leftDockableData = new FXDockableData();
        leftDockableData.setTitle(MnemonicUtils.removeMnemonicChar(getDisplayName(LeftTestPane.class)));
        LeftTestPane leftTestPane = new LeftTestPane();
        leftTestPane.setOnNewSampleAction((ActionEvent event) -> {
            sampleCounter++;
            Sample sample = new Sample("Sample " + sampleCounter);
            FXDockableData sampleDockableData = new FXDockableData();
            SampleEditorPane sampleEditorPane = new SampleEditorPane(sample);
            sampleEditorPane.setDockableData(sampleDockableData);
            dockingPane.getDockables().add(new FXDockableEntry(sampleEditorPane, sampleDockableData,
                    dockablePreferencesManager.getDockablePreferences(sampleEditorPane)));
        });
        final FXDockableEntry leftDockableEntry = new FXDockableEntry(leftTestPane, leftDockableData,
                dockablePreferencesManager.getDockablePreferences(leftTestPane));
        dockingPane.getDockables().add(leftDockableEntry);
        MenuItem leftTestPaneMenuItem = createDockablePaneMenuItem(leftDockableEntry, dockingPane);
        windowMenu.getItems().add(leftTestPaneMenuItem);

        FXDockableData rightDockableData = new FXDockableData();
        rightDockableData.setTitle(MnemonicUtils.removeMnemonicChar(getDisplayName(RightTestPane.class)));
        RightTestPane rightTestPane = new RightTestPane();
        final FXDockableEntry rightDockableEntry = new FXDockableEntry(rightTestPane, rightDockableData,
                dockablePreferencesManager.getDockablePreferences(rightTestPane));
        dockingPane.getDockables().add(rightDockableEntry);
        MenuItem rightTestPaneMenuItem = createDockablePaneMenuItem(rightDockableEntry, dockingPane);
        windowMenu.getItems().add(rightTestPaneMenuItem);

        FXDockableData topDockableData = new FXDockableData();
        topDockableData.setTitle(MnemonicUtils.removeMnemonicChar(getDisplayName(TopTestPane.class)));
        TopTestPane topTestPane = new TopTestPane();
        final FXDockableEntry topDockableEntry = new FXDockableEntry(topTestPane, topDockableData,
                dockablePreferencesManager.getDockablePreferences(topTestPane));
        dockingPane.getDockables().add(topDockableEntry);
        MenuItem topTestPanePaneMenuItem = createDockablePaneMenuItem(topDockableEntry, dockingPane);
        windowMenu.getItems().add(topTestPanePaneMenuItem);

        FXDockableData bottomDockableData = new FXDockableData();
        bottomDockableData.setTitle(MnemonicUtils.removeMnemonicChar(getDisplayName(BottomTestPane.class)));
        BottomTestPane bottomTestPane = new BottomTestPane();
        final FXDockableEntry bottomDockableEntry = new FXDockableEntry(bottomTestPane, bottomDockableData,
                dockablePreferencesManager.getDockablePreferences(bottomTestPane));
        dockingPane.getDockables().add(bottomDockableEntry);
        MenuItem bottomTestPanePaneMenuItem = createDockablePaneMenuItem(bottomDockableEntry, dockingPane);
        windowMenu.getItems().add(bottomTestPanePaneMenuItem);

        Scene scene = new Scene(borderPane, 1500, 1000);

        stage.setTitle("Docking Sample Application");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        dockingManager.close();
        super.stop();
    }

    private String getDisplayName(Class<?> type) {
        return ResourceBundleUtils.getClassResourceStringPrefixed(type, "%displayName");
    }

    private MenuItem createDockablePaneMenuItem(FXDockableEntry dockableEntry, DockingPane dockingPane) {
        MenuItem openDockablePaneMenuItem = new MenuItem(dockableEntry.getDockableData().getTitle());
        openDockablePaneMenuItem.setOnAction(new OpenDockablePaneActionHandler(dockingPane, dockableEntry));
        return openDockablePaneMenuItem;
    }

    private void registerDefaultDockablePreferences(DockablePreferencesManager<Node> dockablePreferencesManager) {
        registerDefaultDockablePreferences(dockablePreferencesManager, LeftTestPane.class, LEFT_AREA_ID, 20);
        registerDefaultDockablePreferences(dockablePreferencesManager, RightTestPane.class, RIGHT_AREA_ID, 20);
        registerDefaultDockablePreferences(dockablePreferencesManager, TopTestPane.class, TOP_AREA_ID, 20);
        registerDefaultDockablePreferences(dockablePreferencesManager, BottomTestPane.class, BOTTOM_AREA_ID, 20);
        registerDefaultDockablePreferences(dockablePreferencesManager, SampleEditorPane.class, CENTER_AREA_ID, 20);
    }

    private void registerDefaultDockablePreferences(DockablePreferencesManager<Node> dockablePreferencesManager,
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
        centerAreaDescriptor.setLayoutConstraints(LayoutConstraintsDescriptor.flexible());

        DockingAreaDescriptor topAreaDescriptor = new DockingAreaDescriptor();
        topAreaDescriptor.setId(TOP_AREA_ID);
        topAreaDescriptor.setParentPath(Arrays.asList(20, 40, 20));
        topAreaDescriptor.setPosition(20);
        topAreaDescriptor.setPermanent(false);
        LayoutConstraintsDescriptor topLayoutConstraintsDescriptor = LayoutConstraintsDescriptor.prefHeight(100.0);
        topAreaDescriptor.setLayoutConstraints(topLayoutConstraintsDescriptor);

        DockingAreaDescriptor bottomAreaDescriptor = new DockingAreaDescriptor();
        bottomAreaDescriptor.setId(BOTTOM_AREA_ID);
        bottomAreaDescriptor.setParentPath(Arrays.asList(20, 40, 80));
        bottomAreaDescriptor.setPosition(20);
        bottomAreaDescriptor.setPermanent(false);
        LayoutConstraintsDescriptor bottomLayoutConstraintsDescriptor = LayoutConstraintsDescriptor.prefHeight(100.0);
        bottomAreaDescriptor.setLayoutConstraints(bottomLayoutConstraintsDescriptor);

        DockingAreaDescriptor leftAreaDescriptor = new DockingAreaDescriptor();
        leftAreaDescriptor.setId(LEFT_AREA_ID);
        leftAreaDescriptor.setParentPath(Arrays.asList(20, 20));
        leftAreaDescriptor.setPosition(20);
        leftAreaDescriptor.setPermanent(false);
        LayoutConstraintsDescriptor leftLayoutConstraintsDescriptor = LayoutConstraintsDescriptor.prefWidth(200.0);
        leftAreaDescriptor.setLayoutConstraints(leftLayoutConstraintsDescriptor);

        DockingAreaDescriptor rightAreaDescriptor = new DockingAreaDescriptor();
        rightAreaDescriptor.setId(RIGHT_AREA_ID);
        rightAreaDescriptor.setParentPath(Arrays.asList(20, 80));
        rightAreaDescriptor.setPosition(20);
        rightAreaDescriptor.setPermanent(false);
        LayoutConstraintsDescriptor rightLayoutConstraintsDescriptor = LayoutConstraintsDescriptor.prefWidth(200.0);
        rightAreaDescriptor.setLayoutConstraints(rightLayoutConstraintsDescriptor);

        dockingPane.getDockingAreaDescriptors().add(centerAreaDescriptor);
        dockingPane.getDockingAreaDescriptors().add(topAreaDescriptor);
        dockingPane.getDockingAreaDescriptors().add(bottomAreaDescriptor);
        dockingPane.getDockingAreaDescriptors().add(leftAreaDescriptor);
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
