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
 * Copyright 2017 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.fx.scene.control.impl.skin;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.control.SkinBase;
import javafx.stage.PopupWindow;
import org.drombler.commons.fx.scene.Nodes;
import org.drombler.commons.fx.scene.control.ProgressMonitor;

/**
 *
 * @author puce
 */
public class ProgressMonitorSkin extends SkinBase<ProgressMonitor> {

    private final ProgressMonitorContentPane contentPane = new ProgressMonitorContentPane();
    private final WorkersPopup workersPopup = new WorkersPopup();
    private final BooleanBinding workersEmptyBinding = Bindings.isEmpty(workersPopup.getWorkers());

    /**
     *
     * @param progressMonitor
     */
    public ProgressMonitorSkin(ProgressMonitor progressMonitor) {
        super(progressMonitor);
        getChildren().add(contentPane);
        contentPane.workerProperty().bind(getSkinnable().mainWorkerProperty());
        contentPane.numberOfAdditionalWorkersProperty().bind(Bindings.size(getSkinnable().getWorkers()).subtract(1));
        contentPane.visibleProperty().bind(Bindings.isNotNull(getSkinnable().mainWorkerProperty()));
        contentPane.managedProperty().bind(contentPane.visibleProperty());
        contentPane.setOnMouseClicked(event -> displayAllTasks());

        initWorkersPopup();
    }

    private void displayAllTasks() {
        if (workersPopup.isShowing()) {
            workersPopup.hide();
        } else {
            workersPopup.setWidth(getSkinnable().getWidth());
            Point2D screenLocation = Nodes.getScreenLocation(getSkinnable());
            workersPopup.show(getSkinnable(), screenLocation.getX(), screenLocation.getY());
        }
    }

    private void initWorkersPopup() {
        Bindings.bindContent(workersPopup.getWorkers(), getSkinnable().getWorkers());
        workersPopup.mainWorkerProperty().bind(getSkinnable().mainWorkerProperty());

        getSkinnable().sceneProperty().addListener(o -> {
            if (((ObservableValue) o).getValue() == null) {
                if (workersPopup.isShowing()) {
                    workersPopup.hide();
                }
            }
        });
        workersEmptyBinding.addListener((observable, oldValue, newValue) -> {
            if (newValue && workersPopup.isShowing()) {
                workersPopup.hide();
            }
        });

        workersPopup.setAutoHide(true);
        workersPopup.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_BOTTOM_LEFT);
    }

}
