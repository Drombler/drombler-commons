package org.drombler.commons.settings.fx.impl.skin;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TreeItem;
import org.drombler.commons.settings.fx.SettingsCategory;

/**
 *
 * @author puce
 */
public class SettingsCategoryTreeItemLink extends Hyperlink {

    private final ObjectProperty<TreeItem<SettingsCategory>> settingsCategoryTreeItem = new SimpleObjectProperty<>(this, "settingsCategoryTreeItem");
//    private final StringBinding displayNameBinding = Bindings.selectString(settingsCategoryTreeItemProperty(), "value", "displayName");

    public SettingsCategoryTreeItemLink() {
//        textProperty().bind(displayNameBinding);
        setText("Test");
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
}
