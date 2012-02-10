/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.scene.control.time.calendar;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javax.time.calendar.LocalDate;
import javax.time.calendar.YearMonth;
import org.projectx.lib.javafx.beans.property.FiniteComparableProperty;

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
    private final BooleanProperty showWeeks = new SimpleBooleanProperty(this, "showWeeks", true);

    /**
     * Creates a new instance of this class. The style class is set to 'year-month-picker'.
     */
    public LocalDateFoo() {
        getStyleClass().setAll("local-date-foo");
    }

    @Override
    protected String getUserAgentStylesheet() {
        return LocalDateFoo.class.getResource("LocalDateFoo.css").toExternalForm();
    }

    public final LocalDate getSelectedDate() {
        return selectedDate.get();
    }

    public final void setSelectedDate(LocalDate selectedDate) {
        this.selectedDate.set(selectedDate);
    }

    public FiniteComparableProperty<LocalDate> selectedDateProperty() {
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

    public final boolean getShowWeeks() {
        return showWeeks.get();
    }

    public final void setShowWeeks(boolean showWeeks) {
        this.showWeeks.set(showWeeks);
    }

    public BooleanProperty showWeeksProperty() {
        return showWeeks;
    }
}
