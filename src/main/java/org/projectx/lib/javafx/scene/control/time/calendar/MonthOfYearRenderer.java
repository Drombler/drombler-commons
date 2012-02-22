/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.scene.control.time.calendar;

import java.util.Locale;
import javax.time.calendar.MonthOfYear;
import org.projectx.lib.javafx.scene.control.AbstractDataRenderer;

/**
 *
 * @author puce
 */
public class MonthOfYearRenderer extends AbstractDataRenderer<MonthOfYear> {

    private final boolean shortText;

    public MonthOfYearRenderer() {
        this(false);
    }

    public MonthOfYearRenderer(boolean shortText) {
        this.shortText = shortText;
    }

    @Override
    public String getText(MonthOfYear moy) {
        if (moy != null) {
            if (shortText) {
                return moy.getShortText(Locale.getDefault());
            } else {
                return moy.getText(Locale.getDefault());
            }
        } else {
            return null;
        }
    }
}
