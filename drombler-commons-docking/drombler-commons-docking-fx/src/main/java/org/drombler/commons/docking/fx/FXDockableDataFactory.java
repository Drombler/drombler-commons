/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (Sourceforge.net user: puce).
 * Copyright 2012 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.docking.fx;

import org.apache.commons.lang3.StringUtils;
import org.drombler.commons.docking.DockableDataFactory;
import org.drombler.commons.fx.scene.image.IconFactory;
import org.softsmithy.lib.util.ResourceLoader;

/**
 *
 * @author puce
 */
public class FXDockableDataFactory implements DockableDataFactory<FXDockableData> {

    private static final int ICON_SIZE = 16;

    @Override
    public FXDockableData createDockableData(String displayName, String icon, ResourceLoader resourceLoader) {
        FXDockableData dockableData = createCommonDockableData(icon, resourceLoader);
        dockableData.setTitle(displayName);
        return dockableData;
    }

    private FXDockableData createCommonDockableData(String icon, ResourceLoader resourceLoader) {
        FXDockableData dockableData = new FXDockableData();
        if (!StringUtils.isBlank(icon)) {
            // TODO: reuse IconFactory of FXActionUtils if possible
            IconFactory iconFactory = new IconFactory(icon, resourceLoader, false);
            dockableData.setGraphicFactory(iconFactory);
            dockableData.setGraphic(iconFactory.createGraphic(ICON_SIZE));
        }
        return dockableData;
    }

    @Override
    public FXDockableData createDockableData(String icon, ResourceLoader resourceLoader) {
        return createCommonDockableData(icon, resourceLoader);
    }

}
