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
package org.drombler.commons.fx.scene.renderer.time;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;
import org.drombler.commons.fx.scene.renderer.AbstractDataRenderer;
import org.drombler.commons.fx.scene.renderer.DataRenderer;

/**
 * A {@link DataRenderer} for {@link DayOfWeek}.
 *
 * @author puce
 */
public class DayOfWeekRenderer extends AbstractDataRenderer<DayOfWeek> {

    private final TextStyle textStyle;

    /**
     * Creates a new instance of this class. Uses {@link DayOfWeek#getDisplayName(java.time.format.TextStyle, java.util.Locale)} and {@link TextStyle#FULL} to get a text representation.
     */
    public DayOfWeekRenderer() {
        this(TextStyle.FULL);
    }

    /**
     * Creates a new instance of this class. Uses {@link DayOfWeek#getDisplayName(java.time.format.TextStyle, java.util.Locale)} to get a text representation.
     *
     * @param textStyle the {@link TextStyle}
     */
    public DayOfWeekRenderer(TextStyle textStyle) {
        this.textStyle = textStyle;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getText(DayOfWeek dayOfWeek) {
        if (dayOfWeek != null) {
            return dayOfWeek.getDisplayName(textStyle, Locale.getDefault());
        } else {
            return null;
        }
    }

}
