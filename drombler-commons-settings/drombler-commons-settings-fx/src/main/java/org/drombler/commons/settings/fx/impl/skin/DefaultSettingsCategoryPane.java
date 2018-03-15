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
import org.drombler.commons.fx.scene.control.ListSingleSelectionModel;
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

    private SettingsCategoryTreeItemLink createLink(TreeItem<SettingsCategory> settingsCategoryTreeItem) {
        SettingsCategoryTreeItemLink link = new SettingsCategoryTreeItemLink();
        link.setSettingsCategoryTreeItem(settingsCategoryTreeItem);
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
