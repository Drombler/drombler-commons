/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.scene.control.time.calendar;

import java.text.NumberFormat;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javax.time.calendar.Year;
import org.projectx.lib.javafx.beans.property.FiniteComparableProperty;
import org.projectx.lib.javafx.scene.control.IntegerField;

/**
 *
 * @author puce
 */
public class YearField extends IntegerField {

    private final FiniteComparableProperty<Year> year = new FiniteComparableProperty<>(this, "year");

    public YearField() {
        super(createNumberFormat());
        setPrefColumnCount(4);
        year.addListener(new ChangeListener<Year>() {

            @Override
            public void changed(ObservableValue<? extends Year> ov, Year oldVal, Year newVal) {
                System.out.println("year object changed: " + oldVal + "; " + newVal);
                setValue(newVal.getValue());
            }
        });
        valueProperty().addListener(new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer oldVal, Integer newVal) {
                System.out.println("year int changed: " + oldVal + "; " + newVal);
                setYear(Year.of(newVal));
            }
        });
    }

    private static NumberFormat createNumberFormat() {
        NumberFormat numberFormat = NumberFormat.getIntegerInstance();
        numberFormat.setGroupingUsed(false);
        return numberFormat;
    }

    public final Year getYear() {
        return year.get();
    }

    public final void setYear(Year year) {
        this.year.set(year);
    }

    public FiniteComparableProperty<Year> yearProperty() {
        return year;
    }
}
