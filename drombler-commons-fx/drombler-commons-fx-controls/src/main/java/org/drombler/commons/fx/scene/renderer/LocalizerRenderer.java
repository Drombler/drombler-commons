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
import org.softsmithy.lib.text.Localizer;

/**
 * A {@link DataRenderer} implementation which uses a {@link Localizer} to get a
 * text representation of the items to render. This renderer provides no
 * graphical representations of items and no style classes by default.
 *
 * @param <T> the type of the data to render
 * @author puce
 */
public class LocalizerRenderer<T> extends AbstractDataRenderer<T> {

    private final Localizer<? super T> localizer;

    /**
     * Creates a new instance of this class.
     *
     * @param localizer a {@link Localizer} to get a text representation of the
     * items to render
     */
    public LocalizerRenderer(Localizer<? super T> localizer) {
        this.localizer = localizer;
    }

    /**
     * Gets a text representation of the item.
     *
     * @see Localizer#getDisplayString(java.lang.Object, java.util.Locale)
     * @param item the item to render
     * @return a text representation of the item
     */
    @Override
    public String getText(T item) {
        String localizedString = null;
        if (item != null) {
            localizedString = localizer.getDisplayString(item, Locale.getDefault()); // TODO: Locale.getDefault ok? Should the locale parameter be removed from getDisplayString()?
        }
        return localizedString;
    }
}
