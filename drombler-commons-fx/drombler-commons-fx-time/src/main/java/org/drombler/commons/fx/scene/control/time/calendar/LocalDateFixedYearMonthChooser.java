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

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javax.time.calendar.LocalDate;
import javax.time.calendar.YearMonth;
import org.drombler.commons.fx.beans.property.LimitedComparableProperty;
import org.drombler.commons.fx.scene.control.time.calendar.impl.skin.Stylesheets;

/**
 * A control which allows to select a {@link LocalDate} for a fixed
 * {@link YearMonth}.
 *
 * TODO: Useful as a public API? (e.g. for skinning?) Or should it be hidden as
 * an implementation detail?
 *
 * @author puce
 */
public class LocalDateFixedYearMonthChooser extends Control {

    /**
     * The currently selected {@link LocalDate}.<br/> <br/> Default:
     * {@link LocalDate#now()}
     *
     * TODO: support multi-selection?
     */
    private final LimitedComparableProperty<LocalDate> selectedDate = new LimitedComparableProperty<>(this, "selectedDate",
            LocalDate.now());
    /**
     * The currently displayed {@link YearMonth}.
     */
    private final ObjectProperty<YearMonth> yearMonth = new SimpleObjectProperty<>(this, "yearMonth", YearMonth.now());
    /**
     * The number of previous weeks to show.
     */
    // TODO: should be configurable with CSS?
    private final IntegerProperty previousWeeks = new SimpleIntegerProperty(this, "previousWeeks", 0);
    /**
     * The number of next weeks to show.
     */
    // TODO: should be configurable with CSS?
    private final IntegerProperty nextWeeks = new SimpleIntegerProperty(this, "nextWeeks", 0);
    /**
     * Flag if the week of year should be shown.
     */
    // TODO: should be configurable with CSS?
    private final BooleanProperty showingWeekOfYear = new SimpleBooleanProperty(this, "showingWeekOfYear", true);

    /**
     * Creates a new instance of this class. The style class is set to
     * 'local-date-fixed-year-month-chooser'.
     */
    public LocalDateFixedYearMonthChooser() {
        getStyleClass().setAll("local-date-fixed-year-month-chooser");
    }

    /**
     * {@inheritDoc}
     */
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

    public LimitedComparableProperty<LocalDate> selectedDateProperty() {
        return selectedDate;
    }

    public final YearMonth getYearMonth() {
        return yearMonth.get();
    }

    public final void setYearMonth(YearMonth yearMonth) {
        this.yearMonth.set(yearMonth);
    }

    public ObjectProperty<YearMonth> yearMonthProperty() {
        return yearMonth;
    }

    public final int getPreviousWeeks() {
        return previousWeeks.get();
    }

    public final void setPreviousWeeks(int previousWeeks) {
        this.previousWeeks.set(previousWeeks);
    }

    public IntegerProperty previousWeeksProperty() {
        return previousWeeks;
    }

    public final int getNextWeeks() {
        return nextWeeks.get();
    }

    public final void setNextWeeks(int nextWeeks) {
        this.nextWeeks.set(nextWeeks);
    }

    public IntegerProperty nextWeeksProperty() {
        return nextWeeks;
    }

    public final boolean isShowingWeekOfYear() {
        return showingWeekOfYear.get();
    }

    public final void setShowingWeekOfYear(boolean showWeekOfYear) {
        this.showingWeekOfYear.set(showWeekOfYear);
    }

    public BooleanProperty showingWeekOfYearProperty() {
        return showingWeekOfYear;
    }
}
