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
package org.drombler.commons.fx.scene.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import org.drombler.commons.fx.scene.control.impl.skin.StatusBarSkin;
import org.drombler.commons.fx.scene.control.impl.skin.Stylesheets;

/**
 *
 * @author puce
 */
public class StatusBar extends Control {
    private static final String DEFAULT_STYLE_CLASS = "status-bar";

    private final ObservableList<Node> leftEntries = FXCollections.observableArrayList();
    private final ObservableList<Node> centerEntries = FXCollections.observableArrayList();
    private final ObservableList<Node> rightEntries = FXCollections.observableArrayList();

    public StatusBar() {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserAgentStylesheet() {
        return Stylesheets.getDefaultStylesheet();
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new StatusBarSkin(this);
    }

    /**
     * @return the leftEntries
     */
    public ObservableList<Node> getLeftEntries() {
        return leftEntries;
    }

    /**
     * @return the centerEntries
     */
    public ObservableList<Node> getCenterEntries() {
        return centerEntries;
    }

    /**
     * @return the rightEntries
     */
    public ObservableList<Node> getRightEntries() {
        return rightEntries;
    }

}
