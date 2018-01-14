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
 * Copyright 2016 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.docking.fx.context;

import java.util.ResourceBundle;
import java.util.SortedSet;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Window;
import org.drombler.commons.client.util.ResourceBundleUtils;
import org.drombler.commons.docking.context.DockingAreaContainer;
import org.drombler.commons.docking.fx.FXDockableData;
import org.drombler.commons.docking.fx.FXDockableEntry;
import org.drombler.commons.docking.fx.context.impl.SaveModifiedDockablesPane;
import org.drombler.commons.fx.stage.OnExitRequestHandler;

/**
 * A {@link OnExitRequestHandler} which handles modified Dockables on exit request.
 *
 * @see Window#setOnCloseRequest(javafx.event.EventHandler)
 * @see OnExitRequestHandler#createOnCloseRequestEventHandler(org.drombler.commons.fx.stage.OnExitRequestHandler) 
 * @author puce
 */
public class DockingOnExitRequestHandler implements OnExitRequestHandler {

    private final ResourceBundle resourceBundle = ResourceBundleUtils.getClassResourceBundle(DockingOnExitRequestHandler.class);
    private final DockingAreaContainer<Node, FXDockableData, FXDockableEntry> dockingAreaContainer;

    public DockingOnExitRequestHandler(DockingAreaContainer<Node, FXDockableData, FXDockableEntry> dockingAreaContainer) {
        this.dockingAreaContainer = dockingAreaContainer;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean handleExitRequest() {
        return handleModifiedDockables();
    }

    public boolean handleModifiedDockables() {
        SortedSet<FXDockableEntry> modifiedDockables = dockingAreaContainer.getSortedModifiedDockables();

        if (!modifiedDockables.isEmpty()) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle(resourceBundle.getString("dialog.title"));
            dialog.setResizable(true);
            SaveModifiedDockablesPane saveModifiedDockablesPane = new SaveModifiedDockablesPane(modifiedDockables);
            saveModifiedDockablesPane.emptyProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    dialog.setResult(ButtonType.OK);
                }
            });
            dialog.getDialogPane().setContent(saveModifiedDockablesPane);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            ((Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL)).setDefaultButton(true);
            return !dialog.showAndWait()
                    .filter(result -> result == ButtonType.CANCEL)
                    .isPresent();
        } else {
            return true;
        }
    }
}
