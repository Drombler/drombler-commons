package org.drombler.commons.settings;

import java.util.Locale;

/**
 *
 * @author puce
 */
public enum StandardSettingsLevel implements SettingsLevel {
    /**
     * The default settings level is usually hard coded.
     */
    DEFAULT(null),
    /**
     * The system wide / user independent settings.
     */
    SYSTEM(DEFAULT),
    /**
     * The per user settings.
     */
    USER(SYSTEM);
    private final SettingsLevel parentLevel;

    private StandardSettingsLevel(SettingsLevel parentLevel) {
        this.parentLevel = parentLevel;
    }

    @Override
    public SettingsLevel getParentLevel() {
        return parentLevel;
    }

    @Override
    public String getDisplayString(Locale inLocale) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
