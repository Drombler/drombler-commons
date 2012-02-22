/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.scene.control.time.calendar;

import javax.time.calendar.LocalDate;
import org.projectx.lib.javafx.scene.control.AbstractDataRenderer;

/**
 *
 * @author puce
 */
public class DayOfMonthRenderer extends AbstractDataRenderer<LocalDate> {

    @Override
    public String getText(LocalDate localDate) {
        if (localDate != null) {
            return Integer.toString(localDate.getDayOfMonth());
        } else {
            return null;
        }
    }
}
