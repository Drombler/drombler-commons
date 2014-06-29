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
 * Utility methods to configure menu items with actions.
 *
 * @author puce
 */
public class MenuItemUtils {

    private MenuItemUtils() {
    }

    /**
     * Configures a {@link MenuItem} with the specified action.
     *
     * @param menuItem the menu item to configre
     * @param action the action
     * @param iconSize the icon size
     */
    public static void configureMenuItem(MenuItem menuItem, FXAction action, int iconSize) {
        menuItem.setMnemonicParsing(true);
        menuItem.textProperty().bind(action.displayNameProperty());
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

    /**
     * Configures a {@link RadioMenuItem} with the specified toggle action.
     *
     * @param menuItem the menu item to configre
     * @param action the action
     * @param iconSize the icon size
     */
    public static void configureRadioMenuItem(RadioMenuItem menuItem, FXToggleAction action, int iconSize) {
        configureMenuItem(menuItem, action, iconSize);
        menuItem.selectedProperty().bindBidirectional(action.selectedProperty());
    }

    /**
     * Configures a {@link CheckMenuItem} with the specified toggle action.
     *
     * @param menuItem the menu item to configre
     * @param action the action
     * @param iconSize the icon size
     */
    public static void configureCheckMenuItem(CheckMenuItem menuItem, FXToggleAction action, int iconSize) {
        configureMenuItem(menuItem, action, iconSize);
        menuItem.selectedProperty().bindBidirectional(action.selectedProperty());
    }
}
