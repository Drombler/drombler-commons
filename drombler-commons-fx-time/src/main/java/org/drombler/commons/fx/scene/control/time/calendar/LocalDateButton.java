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
package org.drombler.commons.fx.scene.control.time.calendar;

import javax.time.calendar.LocalDate;
import org.drombler.commons.fx.scene.control.DataToggleButton;
import org.drombler.commons.fx.scene.renderer.time.calendar.DayOfMonthRenderer;

/**
 *
 * @author puce
 */
public class LocalDateButton extends DataToggleButton<LocalDate> {

    public LocalDateButton() {
        super(new DayOfMonthRenderer());
    }

    public LocalDateButton(LocalDate localDate) {
        super(new DayOfMonthRenderer(), localDate);
    }

    @Override
    public void fire() {
        if (getToggleGroup() == null || !isSelected()) {
            super.fire();
        }
    }
}
