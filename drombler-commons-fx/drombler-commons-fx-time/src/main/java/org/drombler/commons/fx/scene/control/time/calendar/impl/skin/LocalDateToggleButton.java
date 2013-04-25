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
package org.drombler.commons.fx.scene.control.time.calendar.impl.skin;

import java.time.LocalDate;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import org.drombler.commons.fx.scene.control.DataToggleButton;
import org.drombler.commons.fx.scene.renderer.time.calendar.LocalDateDayOfMonthRenderer;

/**
 * This is a {@link ToggleButton} for a {@link LocalDate}. The default renderer
 * is a {@link LocalDateDayOfMonthRenderer}. If it is a member of a {@link ToggleGroup},
 * then it can only be deselected by selecting another member of the
 * ToggleGroup.
 *
 * @author puce
 */
public class LocalDateToggleButton extends DataToggleButton<LocalDate> {

    /**
     * Creates a new instance of this class.
     */
    public LocalDateToggleButton() {
        super(new LocalDateDayOfMonthRenderer());
    }

    /**
     * Creates a new instance of this class.
     *
     * @param localDate the {@link LocalDate}
     */
    public LocalDateToggleButton(LocalDate localDate) {
        super(new LocalDateDayOfMonthRenderer(), localDate);
    }


}
