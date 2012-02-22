/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.scene.control.time.calendar;

import java.util.Locale;
import javax.time.calendar.LocalDate;
import javax.time.calendar.format.DateTimeFormatter;
import javax.time.calendar.format.DateTimeFormatters;
import org.projectx.lib.javafx.scene.control.AbstractDataRenderer;

/**
 *
 * @author puce
 */
public class LocalDateRenderer extends AbstractDataRenderer<LocalDate> {
    private final DateTimeFormatter dateTimeFormatter;

    public LocalDateRenderer() {
        this(DateTimeFormatters.fullDate(Locale.getDefault()));
    }

    public LocalDateRenderer(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }
    

    @Override
    public String getText(LocalDate item) {
        if (item != null){
            return dateTimeFormatter.print(item);
        } else {
            return null;
        }
    }
    
}
