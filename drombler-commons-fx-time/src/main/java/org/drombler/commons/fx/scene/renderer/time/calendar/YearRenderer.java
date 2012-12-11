/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.commons.fx.scene.renderer.time.calendar;

import javax.time.calendar.Year;
import org.drombler.commons.fx.scene.renderer.AbstractDataRenderer;

/**
 *
 * @author puce
 */
public class YearRenderer extends AbstractDataRenderer<Year> {

    @Override
    public String getText(Year year) {
        if (year != null) {
            return Integer.toString(year.getValue());
        } else {
            return null;
        }
    }
}
