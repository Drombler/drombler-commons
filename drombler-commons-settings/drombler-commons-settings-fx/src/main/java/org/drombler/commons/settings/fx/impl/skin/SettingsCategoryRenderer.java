/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2018 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
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
