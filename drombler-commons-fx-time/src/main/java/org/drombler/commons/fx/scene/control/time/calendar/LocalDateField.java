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

import java.util.Locale;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javax.time.calendar.LocalDate;
import javax.time.calendar.YearMonth;
import javax.time.calendar.format.DateTimeFormatters;
import org.drombler.commons.fx.beans.property.FiniteComparableProperty;
import org.drombler.commons.fx.scene.control.time.calendar.impl.skin.Stylesheets;
import org.drombler.commons.fx.scene.renderer.DataRenderer;
import org.drombler.commons.fx.scene.renderer.FormatterDataRenderer;
import org.drombler.commons.time.calendar.format.CalendricalFormatter;
import org.softsmithy.lib.text.Parser;

/**
 *
 * @author puce
 */
// TODO: good to have this in a separate class? Or should a property on LocalDatePicker be used to show "as field"
public class LocalDateField extends Control {

    /**
     * {@link DateTimeFormatters#longDate(java.util.Locale) }
     * {@code new FormatterDataRenderer<>(new CalendricalFormatter(DateTimeFormatters.mediumDate(Locale.getDefault())))}
     */
    private final ObjectProperty<DataRenderer<? super LocalDate>> dataRenderer = new SimpleObjectProperty<DataRenderer<? super LocalDate>>(
            this, "dataRenderer",
            new FormatterDataRenderer<>(new CalendricalFormatter(DateTimeFormatters.longDate(Locale.getDefault()))));
    private final ObjectProperty<Parser<? extends LocalDate>> parser = new SimpleObjectProperty<>(this, "parser");
    private final FiniteComparableProperty<LocalDate> selectedDate = new FiniteComparableProperty<>(this, "selectedDate",
            LocalDate.now());
    private final ObjectProperty<YearMonth> yearMonth = new SimpleObjectProperty<>(this, "yearMonth", YearMonth.now());
    // TODO: should be configurable with CSS?
    private final IntegerProperty previousWeeks = new SimpleIntegerProperty(this, "previousWeeks", 0);
    // TODO: should be configurable with CSS?
    private final IntegerProperty nextWeeks = new SimpleIntegerProperty(this, "nextWeeks", 0);
    // TODO: should be configurable with CSS?
    private final BooleanProperty showWeeks = new SimpleBooleanProperty(this, "showWeeks", true);
    // TODO: should be configurable with CSS?
    private final IntegerProperty previousMonths = new SimpleIntegerProperty(this, "previousMonths", 0);
    // TODO: should be configurable with CSS?
    private final IntegerProperty nextMonths = new SimpleIntegerProperty(this, "nextMonths", 0);
    // TODO: should be configurable with CSS?
    private final BooleanProperty showMonthScrollButton = new SimpleBooleanProperty(this, "showMonthScrollButton", true);
    // TODO: should be configurable with CSS?
    private final BooleanProperty showYearScrollButton = new SimpleBooleanProperty(this, "showYearScrollButton", true);

    /**
     * Creates a new instance of this class. The style class is set to 'year-month-picker'.
     */
    public LocalDateField() {
        getStyleClass().setAll("local-date-field");
    }

    @Override
    protected String getUserAgentStylesheet() {
        return Stylesheets.getDefaultStylesheet();
    }

    public final DataRenderer<? super LocalDate> getDataRenderer() {
        return dataRenderer.get();
    }

    public final void setDataRenderer(DataRenderer<? super LocalDate> dataRenderer) {
        this.dataRenderer.set(dataRenderer);
    }

    public ObjectProperty<DataRenderer<? super LocalDate>> dataRendererProperty() {
        return dataRenderer;
    }

    public final Parser<? extends LocalDate> getParser() {
        return parser.get();
    }

    public final void setParser(Parser<? extends LocalDate> parser) {
        this.parser.set(parser);
    }

    public ObjectProperty<Parser<? extends LocalDate>> parserProperty() {
        return parser;
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

    public final int getPreviousMonths() {
        return previousMonths.get();
    }

    public final void setPreviousMonths(int previousMonths) {
        this.previousMonths.set(previousMonths);
    }

    public IntegerProperty previousMonthsProperty() {
        return previousMonths;
    }

    public final int getNextMonths() {
        return nextMonths.get();
    }

    public final void setNextMonths(int nextMonths) {
        this.nextMonths.set(nextMonths);
    }

    public IntegerProperty nextMonthsProperty() {
        return nextMonths;
    }

    public final boolean getShowMonthScrollButton() {
        return showMonthScrollButton.get();
    }

    public final void setShowMonthScrollButton(boolean showMonthScrollButton) {
        this.showMonthScrollButton.set(showMonthScrollButton);
    }

    public BooleanProperty showMonthScrollButtonProperty() {
        return showMonthScrollButton;
    }

    public final boolean getShowYearScrollButton() {
        return showYearScrollButton.get();
    }

    public final void setShowYearScrollButton(boolean showYearScrollButton) {
        this.showYearScrollButton.set(showYearScrollButton);
    }

    public BooleanProperty showYearScrollButtonProperty() {
        return showYearScrollButton;
    }
}
