package org.drombler.commons.settings;

/**
 *
 * @author puce
 */
public class SettingsStorageManager<T extends SettingsStorage> {

    private SettingsLevel lowestSupportedLevel;

    public T getStorage(SettingsLevel level) {
        return null;
    }
}
