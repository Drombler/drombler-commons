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

}
