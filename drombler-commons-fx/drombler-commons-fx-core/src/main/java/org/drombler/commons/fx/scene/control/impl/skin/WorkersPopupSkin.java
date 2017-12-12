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
import javafx.scene.Node;
import javafx.scene.control.Skin;

/**
 *
 * @author puce
 */
public class WorkersPopupSkin implements Skin<WorkersPopup> {

    private final WorkersPopup workersPopup;
    private ProgressMonitorPopupContentPane contentPane = new ProgressMonitorPopupContentPane();


    public WorkersPopupSkin(WorkersPopup workersPopup) {
        this.workersPopup = workersPopup;
        Bindings.bindContent(contentPane.getWorkers(), workersPopup.getWorkers());
        contentPane.mainWorkerProperty().bind(workersPopup.mainWorkerProperty());
        contentPane.idProperty().bind(workersPopup.idProperty());
        contentPane.styleProperty().bind(workersPopup.styleProperty());
//        contentPane.getStyleClass().addAll(workersPopup.getStyleClass());
        Bindings.bindContent(contentPane.getStyleClass(), workersPopup.getStyleClass());

    }

    @Override
    public WorkersPopup getSkinnable() {
        return workersPopup;
    }

    @Override
    public Node getNode() {
        return contentPane;
    }

    @Override
    public void dispose() {
        contentPane = null;
    }

}
