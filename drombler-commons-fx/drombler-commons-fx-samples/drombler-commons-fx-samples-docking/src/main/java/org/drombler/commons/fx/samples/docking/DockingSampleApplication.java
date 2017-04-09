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
import org.drombler.commons.client.util.MnemonicUtils;
import org.drombler.commons.client.util.ResourceBundleUtils;
import org.drombler.commons.docking.DockablePreferences;
import org.drombler.commons.docking.DockingAreaDescriptor;
import org.drombler.commons.docking.DockingAreaKind;
import org.drombler.commons.docking.LayoutConstraintsDescriptor;
import org.drombler.commons.docking.fx.DockingPane;
import org.drombler.commons.docking.fx.FXDockableEntry;
import org.drombler.commons.docking.fx.context.DockingPaneDockingAreaContainerAdapter;
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
    private DockingPaneDockingAreaContainerAdapter dockingAreaContainerAdapter;

    @Override
    public void start(Stage stage) {
        BorderPane borderPane = new BorderPane();
        final DockingPane dockingPane = new DockingPane();
        dockingAreaContainerAdapter = new DockingPaneDockingAreaContainerAdapter(dockingPane);
        borderPane.setCenter(dockingPane);

        MenuBar menuBar = new MenuBar();
        borderPane.setTop(menuBar);
        Menu windowMenu = new Menu(ResourceBundleUtils.getPackageResourceStringPrefixed(DockingSampleApplication.class,
                "%Menu.Window.displayName"));
        menuBar.getMenus().add(windowMenu);

        addDockingAreas();

        registerDefaultDockablePreferences(dockingAreaContainerAdapter.getDefaultEditorAreaId());

        FXDockableEntry leftDockableEntry = dockingAreaContainerAdapter.openAndRegisterNewView(LeftTestPane.class, false,
                MnemonicUtils.removeMnemonicChar(getDisplayName(LeftTestPane.class)), null, null);
        ((LeftTestPane) leftDockableEntry.getDockable()).setOnNewSampleAction((ActionEvent event) -> {
            sampleCounter++;
            Sample sample = new Sample("Sample " + sampleCounter);
            dockingAreaContainerAdapter.openEditorForContent(sample, SampleEditorPane.class, null, null);
        });
        MenuItem leftTestPaneMenuItem = createDockablePaneMenuItem(leftDockableEntry, dockingPane);
        windowMenu.getItems().add(leftTestPaneMenuItem);

        FXDockableEntry rightDockableEntry = dockingAreaContainerAdapter.openAndRegisterNewView(RightTestPane.class, false,
                MnemonicUtils.removeMnemonicChar(getDisplayName(RightTestPane.class)), null, null);
        MenuItem rightTestPaneMenuItem = createDockablePaneMenuItem(rightDockableEntry, dockingPane);
        windowMenu.getItems().add(rightTestPaneMenuItem);

        FXDockableEntry topDockableEntry = dockingAreaContainerAdapter.openAndRegisterNewView(TopTestPane.class, false,
                MnemonicUtils.removeMnemonicChar(getDisplayName(TopTestPane.class)), null, null);
        MenuItem topTestPanePaneMenuItem = createDockablePaneMenuItem(topDockableEntry, dockingPane);
        windowMenu.getItems().add(topTestPanePaneMenuItem);

        FXDockableEntry bottomDockableEntry = dockingAreaContainerAdapter.openAndRegisterNewView(BottomTestPane.class, false,
                MnemonicUtils.removeMnemonicChar(getDisplayName(BottomTestPane.class)), null, null);
        MenuItem bottomTestPanePaneMenuItem = createDockablePaneMenuItem(bottomDockableEntry, dockingPane);
        windowMenu.getItems().add(bottomTestPanePaneMenuItem);

        Scene scene = new Scene(borderPane, 1500, 1000);

        stage.setTitle("Docking Sample Application");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        dockingAreaContainerAdapter.close();
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

    private void registerDefaultDockablePreferences(String defaultEditorAreaId) {
        registerDefaultDockablePreferences(LeftTestPane.class, LEFT_AREA_ID, 20);
        registerDefaultDockablePreferences(RightTestPane.class, RIGHT_AREA_ID, 20);
        registerDefaultDockablePreferences(TopTestPane.class, TOP_AREA_ID, 20);
        registerDefaultDockablePreferences(BottomTestPane.class, BOTTOM_AREA_ID, 20);
        registerDefaultDockablePreferences(SampleEditorPane.class, defaultEditorAreaId, 20);
    }

    private void registerDefaultDockablePreferences(Class<?> type, String areaId, int position) {
        DockablePreferences leftDockablePreferences = new DockablePreferences(areaId, position);
        dockingAreaContainerAdapter.registerDefaultDockablePreferences(type, leftDockablePreferences);
    }

    private void addDockingAreas() {
        // TODO: hide DockingAreaPane from API
        DockingAreaDescriptor centerAreaDescriptor = new DockingAreaDescriptor();
        centerAreaDescriptor.setId(CENTER_AREA_ID);
        centerAreaDescriptor.setKind(DockingAreaKind.EDITOR);
        centerAreaDescriptor.setParentPath(Arrays.asList(20, 40, 50));
        centerAreaDescriptor.setPosition(20);
        centerAreaDescriptor.setPermanent(true);
        centerAreaDescriptor.setAdHoc(false);
        // TODO: set default
        centerAreaDescriptor.setLayoutConstraints(LayoutConstraintsDescriptor.flexible());

        DockingAreaDescriptor topAreaDescriptor = new DockingAreaDescriptor();
        topAreaDescriptor.setId(TOP_AREA_ID);
        topAreaDescriptor.setKind(DockingAreaKind.VIEW);
        topAreaDescriptor.setParentPath(Arrays.asList(20, 40, 20));
        topAreaDescriptor.setPosition(20);
        topAreaDescriptor.setPermanent(false);
        topAreaDescriptor.setAdHoc(false);
        LayoutConstraintsDescriptor topLayoutConstraintsDescriptor = LayoutConstraintsDescriptor.prefHeight(100.0);
        topAreaDescriptor.setLayoutConstraints(topLayoutConstraintsDescriptor);

        DockingAreaDescriptor bottomAreaDescriptor = new DockingAreaDescriptor();
        bottomAreaDescriptor.setId(BOTTOM_AREA_ID);
        bottomAreaDescriptor.setKind(DockingAreaKind.VIEW);
        bottomAreaDescriptor.setParentPath(Arrays.asList(20, 40, 80));
        bottomAreaDescriptor.setPosition(20);
        bottomAreaDescriptor.setPermanent(false);
        bottomAreaDescriptor.setAdHoc(false);
        LayoutConstraintsDescriptor bottomLayoutConstraintsDescriptor = LayoutConstraintsDescriptor.prefHeight(100.0);
        bottomAreaDescriptor.setLayoutConstraints(bottomLayoutConstraintsDescriptor);

        DockingAreaDescriptor leftAreaDescriptor = new DockingAreaDescriptor();
        leftAreaDescriptor.setId(LEFT_AREA_ID);
        leftAreaDescriptor.setKind(DockingAreaKind.VIEW);
        leftAreaDescriptor.setParentPath(Arrays.asList(20, 20));
        leftAreaDescriptor.setPosition(20);
        leftAreaDescriptor.setPermanent(false);
        leftAreaDescriptor.setAdHoc(false);
        LayoutConstraintsDescriptor leftLayoutConstraintsDescriptor = LayoutConstraintsDescriptor.prefWidth(200.0);
        leftAreaDescriptor.setLayoutConstraints(leftLayoutConstraintsDescriptor);

        DockingAreaDescriptor rightAreaDescriptor = new DockingAreaDescriptor();
        rightAreaDescriptor.setId(RIGHT_AREA_ID);
        rightAreaDescriptor.setKind(DockingAreaKind.VIEW);
        rightAreaDescriptor.setParentPath(Arrays.asList(20, 80));
        rightAreaDescriptor.setPosition(20);
        rightAreaDescriptor.setPermanent(false);
        rightAreaDescriptor.setAdHoc(false);
        LayoutConstraintsDescriptor rightLayoutConstraintsDescriptor = LayoutConstraintsDescriptor.prefWidth(200.0);
        rightAreaDescriptor.setLayoutConstraints(rightLayoutConstraintsDescriptor);

        dockingAreaContainerAdapter.addDockingArea(centerAreaDescriptor);
        dockingAreaContainerAdapter.addDockingArea(topAreaDescriptor);
        dockingAreaContainerAdapter.addDockingArea(bottomAreaDescriptor);
        dockingAreaContainerAdapter.addDockingArea(leftAreaDescriptor);
        dockingAreaContainerAdapter.addDockingArea(rightAreaDescriptor);
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
