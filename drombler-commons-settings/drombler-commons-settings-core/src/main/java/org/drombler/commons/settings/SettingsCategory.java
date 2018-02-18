package org.drombler.commons.settings;

import java.util.Locale;
import org.softsmithy.lib.text.Localizable;

/**
 *
 * @author puce
 */
public class SettingsCategory  implements Localizable{
    public static final SettingsCategory ROOT = new SettingsCategory(null, "");
    private SettingsCategory parentCategory;
//    private String id;
    private final String displayName;

    public SettingsCategory(SettingsCategory parentCategory, String displayName) {
        this.parentCategory = parentCategory;
        this.displayName = displayName;
    }

    @Override
    public String getDisplayString(Locale inLocale) {
        return displayName;
    }
    
}
