package org.drombler.commons.settings.fx;

import javafx.scene.Node;

/**
 *
 * @author puce
 */

@Deprecated
public class Settings<T> {

    private final SettingsCategory category;
    private final Class<T> settingsType;
    private final Class<? extends Node> paneType;

    /**
     *
     * @param category
     * @param settingsType
     * @param paneType
     */
    public Settings(SettingsCategory category, Class<T> settingsType, Class<? extends Node> paneType) {
        this.category = category;
        this.settingsType = settingsType;
        this.paneType = paneType;
    }
    
}
