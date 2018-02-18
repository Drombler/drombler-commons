package org.drombler.commons.settings.fx;

import javafx.beans.DefaultProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import org.drombler.commons.settings.SettingsStorageManager;
import org.drombler.commons.settings.fx.impl.skin.SettingsPaneSkin;
import org.drombler.commons.settings.fx.impl.skin.Stylesheets;

/**
 *
 * @author puce
 */
@DefaultProperty("settings")
public class SettingsPane extends Control {

    private static final String DEFAULT_STYLE_CLASS = "settings-pane";
    private ObjectProperty<SettingsCategory> rootSettingsCategory = new SimpleObjectProperty<>(this, "rootSettingsCategory");

    private final ObjectProperty<SettingsStorageManager<?>> settingsStorageManager = new SimpleObjectProperty<>(this, "settingsStorageManager");

    public SettingsPane() {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserAgentStylesheet() {
        return Stylesheets.getDefaultStylesheet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new SettingsPaneSkin(this);
    }

    public final SettingsCategory getRootSettingsCategory() {
        return rootSettingsCategoryProperty().get();
    }

    public final void setRootSettingsCategory(SettingsCategory rootSettingsCategory) {
        rootSettingsCategoryProperty().set(rootSettingsCategory);
    }

    public ObjectProperty<SettingsCategory> rootSettingsCategoryProperty() {
        return rootSettingsCategory;
    }

    public final SettingsStorageManager<?> getSettingsStorageManager() {
        return settingsStorageManagerProperty().get();
    }

    public final void setSettingsStorageManager(SettingsStorageManager<?> settingsStorageManager) {
        settingsStorageManagerProperty().set(settingsStorageManager);
    }

    public ObjectProperty<SettingsStorageManager<?>> settingsStorageManagerProperty() {
        return settingsStorageManager;
    }

}
