package org.drombler.commons.settings.fx.impl.skin;

import javafx.scene.control.Tooltip;
import org.apache.commons.lang3.StringUtils;
import org.drombler.commons.fx.scene.renderer.AbstractDataRenderer;
import org.drombler.commons.settings.fx.SettingsCategory;

/**
 *
 * @author puce
 */
public class SettingsCategoryRenderer extends AbstractDataRenderer<SettingsCategory> {

    @Override
    public String getText(SettingsCategory item) {
        return item.getDisplayName();
    }

    @Override
    public Tooltip getTooltip(SettingsCategory item) {
        if (StringUtils.isNotBlank(item.getDisplayDescription())) {
            return new Tooltip(item.getDisplayDescription());
        } else {
            return null;
        }
    }

}
