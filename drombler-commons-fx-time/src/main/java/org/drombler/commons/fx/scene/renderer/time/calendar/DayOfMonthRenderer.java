/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.commons.fx.scene.renderer.time.calendar;

import javax.time.calendar.LocalDate;
import org.drombler.commons.fx.scene.renderer.AbstractDataRenderer;

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
