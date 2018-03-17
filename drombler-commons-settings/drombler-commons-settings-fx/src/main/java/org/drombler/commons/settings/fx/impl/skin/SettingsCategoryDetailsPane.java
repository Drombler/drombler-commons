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
 * Copyright 2018 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.settings.fx.impl.skin;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.drombler.commons.fx.fxml.FXMLLoaders;

/**
 *
 * @author puce
 */
public class SettingsCategoryDetailsPane extends GridPane {

    @FXML
    private Label titleLabel; // TODO: replace with Breadcrumb

    @FXML
    private Label descriptionLabel;

    @FXML
    private BorderPane contentPaneContainer;

    public SettingsCategoryDetailsPane() {
        FXMLLoaders.loadRoot(this);
        descriptionLabel.visibleProperty().bind(Bindings.isEmpty(descriptionLabel.textProperty()));
    }

    public final String getTitle() {
        return titleProperty().get();
    }

    public final void setTitle(String title) {
        titleProperty().set(title);
    }

    public StringProperty titleProperty() {
        return titleLabel.textProperty();
    }

    public final String getDescription() {
        return descriptionProperty().get();
    }

    public final void setDescription(String description) {
        descriptionProperty().set(description);
    }

    public StringProperty descriptionProperty() {
        return descriptionLabel.textProperty();
    }

    public final Node getContentPane() {
        return contentPaneProperty().get();
    }

    public final void setContentPane(Node contentPane) {
        contentPaneProperty().set(contentPane);
    }

    public ObjectProperty<Node> contentPaneProperty() {
        return contentPaneContainer.centerProperty();
    }

}
