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

import java.time.LocalDate;
import org.drombler.commons.fx.scene.renderer.AbstractDataRenderer;
import org.drombler.commons.fx.scene.renderer.DataRenderer;

/**
 * A {@link DataRenderer} for {@link LocalDate} based on {@link LocalDate#getDayOfMonth()}.
 *
 * @author puce
 */
public class LocalDateDayOfMonthRenderer extends AbstractDataRenderer<LocalDate> {

    /**
     * Creates a new instance of this class.
     */
    public LocalDateDayOfMonthRenderer() {
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public String getText(LocalDate localDate) {
        if (localDate != null) {
            return Integer.toString(localDate.getDayOfMonth());
        } else {
            return null;
        }
    }
}
