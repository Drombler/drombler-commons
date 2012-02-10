/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.control.time.calendar;

import java.util.Locale;
import javax.time.calendar.MonthOfYear;
import org.projectx.lib.javafx.control.AbstractCellRenderer;

/**
 *
 * @author puce
 */
public class MonthOfYearCellRenderer extends AbstractCellRenderer<MonthOfYear> {

    private final boolean shortText;

    public MonthOfYearCellRenderer() {
        this(false);
    }

    public MonthOfYearCellRenderer(boolean shortText) {
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
