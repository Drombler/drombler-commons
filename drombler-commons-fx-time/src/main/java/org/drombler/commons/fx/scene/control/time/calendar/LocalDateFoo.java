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

import java.util.Calendar;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javax.time.calendar.LocalDate;
import javax.time.calendar.YearMonth;
import org.drombler.commons.fx.beans.property.FiniteComparableProperty;
import org.drombler.commons.fx.scene.control.time.calendar.impl.skin.Stylesheets;

/**
 *
 * @author puce
 */
public class LocalDateFoo extends Control {

    private final FiniteComparableProperty<LocalDate> selectedDate = new FiniteComparableProperty<>(this, "selectedDate",
            LocalDate.now());
    private final ObjectProperty<YearMonth> yearMonth = new SimpleObjectProperty<>(this, "yearMonth", YearMonth.now());
    // TODO: should be configurable with CSS?
    private final IntegerProperty previousWeeks = new SimpleIntegerProperty(this, "previousWeeks", 0);
    // TODO: should be configurable with CSS?
    private final IntegerProperty nextWeeks = new SimpleIntegerProperty(this, "nextWeeks", 0);
    // TODO: should be configurable with CSS?
    private final BooleanProperty showWeekOfYear = new SimpleBooleanProperty(this, "showWeekOfYear", true);

    /**
     * Creates a new instance of this class. The style class is set to
     * 'year-month-picker'.
     */
    public LocalDateFoo() {
        getStyleClass().setAll("local-date-foo");
    }

    @Override
    protected String getUserAgentStylesheet() {
        return Stylesheets.getDefaultStylesheet();
    }

    public final LocalDate getSelectedDate() {
        return selectedDate.get();
    }

    public final void setSelectedDate(LocalDate selectedDate) {
        this.selectedDate.set(selectedDate);
    }

    /**
     * The currently selected {@link LocalDate}.<br/> <br/> Default:
     * {@link LocalDate#now()}
     */
    public FiniteComparableProperty<LocalDate> selectedDateProperty() {
        return selectedDate;
    }

    public final YearMonth getYearMonth() {
        return yearMonth.get();
    }

    public final void setYearMonth(YearMonth yearMonth) {
        this.yearMonth.set(yearMonth);
    }

    /**
     * The currently displayed {@link YearMonth}.
     */
    public ObjectProperty<YearMonth> yearMonthProperty() {
        return yearMonth;
    }

    public final int getPreviousWeeks() {
        return previousWeeks.get();
    }

    public final void setPreviousWeeks(int previousWeeks) {
        this.previousWeeks.set(previousWeeks);
    }

    /**
     * The number of previous weeks to show.
     */
    public IntegerProperty previousWeeksProperty() {
        return previousWeeks;
    }

    public final int getNextWeeks() {
        return nextWeeks.get();
    }

    public final void setNextWeeks(int nextWeeks) {
        this.nextWeeks.set(nextWeeks);
    }

    /**
     * The number of next weeks to show.
     */
    public IntegerProperty nextWeeksProperty() {
        return nextWeeks;
    }

    public final boolean isShowWeekOfYear() {
        return showWeekOfYear.get();
    }

    public final void setShowWeekOfYear(boolean showWeekOfYear) {
        this.showWeekOfYear.set(showWeekOfYear);
    }

    /**
     * Flag if the week of year should be shown.
     */
    public BooleanProperty showWeekOfYearProperty() {
        return showWeekOfYear;
    }
}
