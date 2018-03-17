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

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.drombler.commons.fx.beans.binding.CollectionBindings;
import org.drombler.commons.fx.fxml.FXMLLoaders;
import org.drombler.commons.fx.scene.control.DataHyperlink;
import org.drombler.commons.fx.scene.control.ListSingleSelectionModel;
import org.drombler.commons.fx.scene.renderer.TreeItemDataRenderer;
import org.drombler.commons.settings.fx.SettingsCategory;

/**
 *
 * @author puce
 */
public class DefaultSettingsCategoryPane extends BorderPane {

    private final ObjectProperty<TreeItem<SettingsCategory>> settingsCategoryTreeItem = new SimpleObjectProperty<>(this, "settingsCategoryTreeItem");
    private final ObjectProperty<SingleSelectionModel<TreeItem<SettingsCategory>>> selectionModel = new SimpleObjectProperty<>(this, "selectionModel");
    @FXML
    private VBox linkBox;

    public DefaultSettingsCategoryPane() {
        FXMLLoaders.loadRoot(this);
        settingsCategoryTreeItem.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                setSelectionModel(null);
                CollectionBindings.unbindContent(linkBox.getChildren(), oldValue.getChildren());
            }
            if (newValue != null) {
                setSelectionModel(new ListSingleSelectionModel<>(newValue.getChildren()));
                CollectionBindings.bindContent(linkBox.getChildren(), newValue.getChildren(), this::createLink);
            }
        });
    }

    private DataHyperlink<TreeItem<SettingsCategory>> createLink(TreeItem<SettingsCategory> settingsCategoryTreeItem) {
        DataHyperlink<TreeItem<SettingsCategory>> link = new DataHyperlink<>(new TreeItemDataRenderer<>(new SettingsCategoryRenderer()), settingsCategoryTreeItem);
        link.setOnAction(event -> getSelectionModel().select(settingsCategoryTreeItem));
        return link;
    }

    public final TreeItem<SettingsCategory> getSettingsCategoryTreeItem() {
        return settingsCategoryTreeItemProperty().get();
    }

    public final void setSettingsCategoryTreeItem(TreeItem<SettingsCategory> settingsCategoryTreeItem) {
        settingsCategoryTreeItemProperty().set(settingsCategoryTreeItem);
    }

    public ObjectProperty<TreeItem<SettingsCategory>> settingsCategoryTreeItemProperty() {
        return settingsCategoryTreeItem;
    }

    public final SingleSelectionModel<TreeItem<SettingsCategory>> getSelectionModel() {
        return selectionModelProperty().get();
    }

    public final void setSelectionModel(SingleSelectionModel<TreeItem<SettingsCategory>> selectionModel) {
        selectionModelProperty().set(selectionModel);
    }

    public ObjectProperty<SingleSelectionModel<TreeItem<SettingsCategory>>> selectionModelProperty() {
        return selectionModel;
    }
}
