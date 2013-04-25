/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (Sourceforge.net user: puce).
 * Copyright 2012 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.fx.scene.control.time.calendar;

import java.text.NumberFormat;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.time.Year;
import org.drombler.commons.fx.beans.property.LimitedComparableProperty;
import org.drombler.commons.fx.scene.control.IntegerField;

/**
 * A text field for {@link Year}.
 * @author puce
 */
public class YearField extends IntegerField {

    /**
     * The {@link Year} value of the field.
     */
    private final LimitedComparableProperty<Year> year = new LimitedComparableProperty<>(this, "year");

    /**
     * Creates a new instance of this class.
     */
    public YearField() {
        super(createNumberFormat());
        setPrefColumnCount(4);
        year.addListener(new ChangeListener<Year>() {

            @Override
            public void changed(ObservableValue<? extends Year> ov, Year oldVal, Year newVal) {
//                System.out.println("year object changed: " + oldVal + "; " + newVal);
                setValue(newVal.getValue());
            }
        });
        valueProperty().addListener(new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer oldVal, Integer newVal) {
//                System.out.println("year int changed: " + oldVal + "; " + newVal);
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

    public LimitedComparableProperty<Year> yearProperty() {
        return year;
    }
}
