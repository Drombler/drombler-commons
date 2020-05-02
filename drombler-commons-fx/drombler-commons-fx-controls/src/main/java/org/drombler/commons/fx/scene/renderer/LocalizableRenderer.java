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
package org.drombler.commons.fx.scene.renderer;

import java.util.Locale;
import org.softsmithy.lib.text.Localizable;

/**
 *
 * @author puce
 */
class LocalizableRenderer<T extends Localizable> extends AbstractDataRenderer<T> {


    @Override
    public String getText(T item) {
        String localizedString = null;
        if (item != null) {
            localizedString = item.getDisplayString(Locale.getDefault()); // TODO: Locale.getDefault ok? Should the locale parameter be removed from getDisplayString()?
        }
        return localizedString;
    }
}
