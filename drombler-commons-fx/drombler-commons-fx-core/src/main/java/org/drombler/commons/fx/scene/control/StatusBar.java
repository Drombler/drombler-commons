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
 * A StatusBar typically is placed at the bottom of the application to show the user some information.
 *
 * This control provides an API to place status bar element to the right-side, left-side or center of this status bar.
 */
public class StatusBar extends Control {
    private static final String DEFAULT_STYLE_CLASS = "status-bar";

    private final ObservableList<Node> leftEntries = FXCollections.observableArrayList();
    private final ObservableList<Node> centerEntries = FXCollections.observableArrayList();
    private final ObservableList<Node> rightEntries = FXCollections.observableArrayList();

    /**
     * Creates a new instance of this class.
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new StatusBarSkin(this);
    }

    /**
     * Gets the left status bar elements.
     *
     * @return the left status bar elements
     */
    public ObservableList<Node> getLeftElements() {
        return leftEntries;
    }

    /**
     * Gets the center status bar elements.
     *
     * @return the center status bar elements
     */
    public ObservableList<Node> getCenterElements() {
        return centerEntries;
    }

    /**
     * Gets the rights status bar elements.
     *
     * @return the right status bar elements
     */
    public ObservableList<Node> getRightElements() {
        return rightEntries;
    }

}
