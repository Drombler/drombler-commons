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

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.drombler.commons.fx.fxml.FXMLLoaders;

/**
 *
 * @author puce
 */
public class StatusBarContentPane extends GridPane {

    @FXML
    private HBox leftPane;

    @FXML
    private HBox centerPane;

    @FXML
    private HBox rightPane;

    public StatusBarContentPane() {
        FXMLLoaders.loadRoot(this);
    }

    /**
     * @return the leftPane
     */
    public Pane getLeftPane() {
        return leftPane;
    }

    /**
     * @return the centerPane
     */
    public Pane getCenterPane() {
        return centerPane;
    }

    /**
     * @return the rightPane
     */
    public Pane getRightPane() {
        return rightPane;
    }

}
