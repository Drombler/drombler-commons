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
package org.drombler.commons.fx.scene.control;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.control.ToolBar;
import org.drombler.commons.fx.scene.control.impl.skin.Stylesheets;

/**
 *
 * @author puce
 */


class ToolbarContainerPane extends Control {

    private static final String DEFAULT_STYLE_CLASS = "toolbar-container-pane";

    private ObservableList<ToolBar> toolbars = FXCollections.observableArrayList();

    private ObjectProperty<Orientation> orientation;

    public ToolbarContainerPane() {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);
        toolbars.addListener((ListChangeListener.Change<? extends ToolBar> c) -> {

        });
    }

    @Override
    protected String getUserAgentStylesheet() {
        return Stylesheets.getDefaultStylesheet();
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return super.createDefaultSkin(); //To change body of generated methods, choose Tools | Templates.
    }

}
