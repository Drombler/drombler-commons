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
package org.drombler.commons.action.fx;

import javafx.scene.Node;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;

/**
 *
 * @author puce
 */
public class MenuItemUtils {

    private MenuItemUtils() {
    }

    public static void configureMenuItem(MenuItem menuItem, FXAction action, int iconSize) {
        menuItem.textProperty().bind(action.displayNameProperty());
        menuItem.setMnemonicParsing(true);
        menuItem.acceleratorProperty().bind(action.acceleratorProperty());
        menuItem.setOnAction(action);
        menuItem.disableProperty().bind(action.disabledProperty());
        if (action.getGraphicFactory() != null) {
            Node graphic = action.getGraphicFactory().createGraphic(iconSize);
            if (graphic != null) {
                menuItem.setGraphic(graphic);
            }
        }
    }

    public static void configureRadioMenuItem(RadioMenuItem menuItem, FXToggleAction action, int iconSize) {
        configureMenuItem(menuItem, action, iconSize);
        menuItem.selectedProperty().bindBidirectional(action.selectedProperty());
    }

    public static void configureCheckMenuItem(CheckMenuItem menuItem, FXToggleAction action, int iconSize) {
        configureMenuItem(menuItem, action, iconSize);
        menuItem.selectedProperty().bindBidirectional(action.selectedProperty());
    }
}
