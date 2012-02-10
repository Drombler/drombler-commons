/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.control.time.calendar;

import javax.time.calendar.LocalDate;
import org.projectx.lib.javafx.control.DataToggleButton;

/**
 *
 * @author puce
 */
public class LocalDateButton extends DataToggleButton<LocalDate> {

    public LocalDateButton() {
        super(new DayOfMonthCellRenderer());
    }

    public LocalDateButton(LocalDate data) {
        super(new DayOfMonthCellRenderer(), data);
    }

    @Override
    public void fire() {
        if (getToggleGroup() == null || !isSelected()) {
            super.fire();
        }
    }
}
