/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.scene.control.time.calendar;

import javax.time.calendar.LocalDate;
import org.projectx.lib.javafx.scene.control.DataToggleButton;
import org.projectx.lib.javafx.scene.renderer.time.calendar.DayOfMonthRenderer;

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
