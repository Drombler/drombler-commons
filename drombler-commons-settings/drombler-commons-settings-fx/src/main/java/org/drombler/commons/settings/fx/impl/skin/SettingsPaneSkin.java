package org.drombler.commons.settings.fx.impl.skin;

import javafx.scene.control.SkinBase;
import javafx.scene.control.TreeItem;
import org.drombler.commons.fx.beans.binding.CollectionBindings;
import org.drombler.commons.settings.fx.SettingsCategory;
import org.drombler.commons.settings.fx.SettingsPane;

/**
 *
 * @author puce
 */
public class SettingsPaneSkin extends SkinBase<SettingsPane> {

    private final SettingsContentPane contentPane = new SettingsContentPane();

    public SettingsPaneSkin(SettingsPane settingsPane) {
        super(settingsPane);
        getChildren().add(contentPane);
        CollectionBindings.bindTreeContent(contentPane.getRootSettingsCategoryItem().getChildren(), TreeItem::getChildren,
                settingsPane.getTopCategories(), SettingsCategory::getSubCategories,
                settingsCategory -> new TreeItem<>(settingsCategory));
    }
}
