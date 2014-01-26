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
package org.drombler.commons.fx.samples.docking;

import java.util.Arrays;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.drombler.commons.client.docking.DockablePreferences;
import org.drombler.commons.client.docking.DockablePreferencesManager;
import org.drombler.commons.client.docking.LayoutConstraintsDescriptor;
import org.drombler.commons.client.docking.SimpleDockablePreferencesManager;
import org.drombler.commons.fx.docking.DockablePane;
import org.drombler.commons.fx.docking.DockingAreaPane;
import org.drombler.commons.fx.docking.DockingPane;

/**
 *
 * @author puce
 */


public class DockingSampleApplication extends Application {

     public static void main(String... args) {
        launch(args);
    }
     
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = new BorderPane();
        DockingPane dockingPane = new DockingPane();

        addDockingAreas(dockingPane);

        DockablePreferencesManager<DockablePane> dockablePreferencesManager = new SimpleDockablePreferencesManager<>();
        registerDefaultDockablePreferences(dockablePreferencesManager);

        LeftTestPane leftTestPane = new LeftTestPane();
        leftTestPane.setTitle("Left");
        dockingPane.addDockable(leftTestPane, dockablePreferencesManager.getDockablePreferences(leftTestPane));

        borderPane.setCenter(dockingPane);
        Scene scene = new Scene(borderPane, 1500, 1000);

        stage.setTitle("Docking Sample Apllication");
        stage.setScene(scene);
        stage.show();
    }

    private void registerDefaultDockablePreferences(DockablePreferencesManager<DockablePane> dockablePreferencesManager) {
        DockablePreferences leftDockablePreferences = new DockablePreferences();
        leftDockablePreferences.setAreaId(LEFT_AREA_ID);
        leftDockablePreferences.setPosition(20);
        dockablePreferencesManager.registerDefaultDockablePreferences(LeftTestPane.class, leftDockablePreferences);
    }

    private void addDockingAreas(DockingPane dockingPane) {
        // TODO: hide DockingAreaPane from API
        DockingAreaPane centerDockingAreaPane = new DockingAreaPane(CENTER_AREA_ID, 20, true);
        // TODO: set default
        centerDockingAreaPane.setLayoutConstraints(new LayoutConstraintsDescriptor());

        DockingAreaPane topDockingAreaPane = new DockingAreaPane(TOP_AREA_ID, 20, false);
        LayoutConstraintsDescriptor topLayoutConstraintsDescriptor = new LayoutConstraintsDescriptor();
        topLayoutConstraintsDescriptor.setPrefHeight(100.0);
        topDockingAreaPane.setLayoutConstraints(topLayoutConstraintsDescriptor);

        DockingAreaPane bottomDockingAreaPane = new DockingAreaPane(BOTTOM_AREA_ID, 20, false);
        LayoutConstraintsDescriptor bottomLayoutConstraintsDescriptor = new LayoutConstraintsDescriptor();
        bottomLayoutConstraintsDescriptor.setPrefHeight(100.0);
        bottomDockingAreaPane.setLayoutConstraints(bottomLayoutConstraintsDescriptor);

        DockingAreaPane leftDockingAreaPane = new DockingAreaPane(LEFT_AREA_ID, 20, false);
        LayoutConstraintsDescriptor leftLayoutConstraintsDescriptor = new LayoutConstraintsDescriptor();
        leftLayoutConstraintsDescriptor.setPrefWidth(200.0);
        leftDockingAreaPane.setLayoutConstraints(leftLayoutConstraintsDescriptor);

        DockingAreaPane rightDockingAreaPane = new DockingAreaPane(RIGHT_AREA_ID, 20, false);
        LayoutConstraintsDescriptor rightLayoutConstraintsDescriptor = new LayoutConstraintsDescriptor();
        rightLayoutConstraintsDescriptor.setPrefWidth(200.0);
        rightDockingAreaPane.setLayoutConstraints(rightLayoutConstraintsDescriptor);

        dockingPane.addDockingArea(Arrays.asList(20, 40, 50), centerDockingAreaPane);
        dockingPane.addDockingArea(Arrays.asList(20, 40, 20), topDockingAreaPane);
        dockingPane.addDockingArea(Arrays.asList(20, 40, 80), bottomDockingAreaPane);
        dockingPane.addDockingArea(Arrays.asList(20, 20), leftDockingAreaPane);
        dockingPane.addDockingArea(Arrays.asList(20, 80), rightDockingAreaPane);
    }
    public static final String RIGHT_AREA_ID = "right";
    public static final String LEFT_AREA_ID = "left";
    public static final String BOTTOM_AREA_ID = "bottom";
    public static final String TOP_AREA_ID = "top";
    public static final String CENTER_AREA_ID = "center";
    
}
