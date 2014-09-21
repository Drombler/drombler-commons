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
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import org.drombler.commons.client.util.MnemonicUtils;

/**
 * Utility methods to configure buttons with actions.
 *
 * @author puce
 */
public final class ButtonUtils {

    private ButtonUtils() {
    }

    /**
     * Configures a toolbar button with the specified action.
     *
     * @param button the toolbar button to configure
     * @param action the action
     * @param iconSize the icon size
     */
    public static void configureToolbarButton(ButtonBase button, FXAction action, int iconSize) {

        //        button.getStyleClass().add("tool-bar-overflow-button");
//        button.setStyle(
//                "-fx-padding: 0.416667em 0.416667em 0.416667em 0.416667em; -fx-content-display: GRAPHIC_ONLY; -fx-background-color: transparent"); /*
//         * 5 5 5 5
//         */
        button.setFocusTraversable(false); // TODO: good?

//        button.setMnemonicParsing(true);
//        button.acceleratorProperty().bind(action.acceleratorProperty());
        button.setOnAction(action);
        button.disableProperty().bind(action.enabledProperty().not());
        final String displayName = MnemonicUtils.removeMnemonicChar(action.getDisplayName());
        if (action.getGraphicFactory() != null) {
            Node graphic = action.getGraphicFactory().createGraphic(iconSize);
            if (graphic != null) {
                button.setGraphic(graphic);
            } else {
                button.setText(displayName); // TODO: ok? -fx-content-display: GRAPHIC_ONLY ? 
            }
        } else {
            button.setText(displayName); // TODO: ok? -fx-content-display: GRAPHIC_ONLY ? 
        }
        button.setTooltip(new Tooltip(displayName));// + " (" + action.getAccelerator() + ")"));
    }

    /**
     * Configures a toolbar toggle button with the specified toggle action.
     *
     * @param toggleButton the toolbar toggle button to configure
     * @param action the toggle action
     * @param iconSize the icon size
     */
    public static void configureToolbarToggleButton(ToggleButton toggleButton, FXToggleAction action, int iconSize) {
        configureToolbarButton(toggleButton, action, iconSize);
        toggleButton.selectedProperty().bindBidirectional(action.selectedProperty());
    }
}
