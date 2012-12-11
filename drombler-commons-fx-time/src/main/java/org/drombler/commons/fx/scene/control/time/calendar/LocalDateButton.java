/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
