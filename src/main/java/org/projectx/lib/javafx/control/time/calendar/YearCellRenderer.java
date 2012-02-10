/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.control.time.calendar;

import javax.time.calendar.Year;
import org.projectx.lib.javafx.control.AbstractCellRenderer;

/**
 *
 * @author puce
 */
public class YearCellRenderer extends AbstractCellRenderer<Year> {

    @Override
    public String getText(Year year) {
        if (year != null) {
            return Integer.toString(year.getValue());
        } else {
            return null;
        }
    }
}
