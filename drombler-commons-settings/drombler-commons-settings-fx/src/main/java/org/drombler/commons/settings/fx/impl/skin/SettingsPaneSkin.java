package org.drombler.commons.settings.fx.impl.skin;

import javafx.scene.control.SkinBase;
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
    }
}
