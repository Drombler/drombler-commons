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
package org.drombler.commons.fx.scene.control.time;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import org.drombler.commons.fx.beans.property.LimitedComparableProperty;
import org.drombler.commons.fx.scene.control.time.impl.skin.Stylesheets;
import org.drombler.commons.fx.scene.renderer.DataRenderer;
import org.drombler.commons.fx.scene.renderer.FormatterDataRenderer;
import org.softsmithy.lib.text.Parser;
import org.softsmithy.lib.time.format.TemporalAccessorFormatter;

/**
 * A {@link LocalDate} text field which allows to pick the LocalDate from a
 * control.
 *
 * @author puce
 */
// TODO: good to have this in a separate class? Or should a property on LocalDatePicker be used to show "as field"
public class LocalDatePicker extends Control {

    /**
     * The {@link DataRenderer} used to format the {@link LocalDate} in the text
     * field. The default DataRenderer uses
     * {@link DateTimeFormatter#ofLocalizedDate(java.time.format.FormatStyle)}
     * and {@link FormatStyle#LONG}. <br><br> Here is a sample how you could
     * create a DataRenderer which uses
     * {@link DateTimeFormatter#ofLocalizedDate(java.time.format.FormatStyle)}
     * and {@link FormatStyle#MEDIUM}:<br><br>
     * {@code new FormatterDataRenderer<>(new TemporalAccessorFormatter(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)))}
     */
    private final ObjectProperty<DataRenderer<? super LocalDate>> dataRenderer = new SimpleObjectProperty<>(this,
            "dataRenderer",
            new FormatterDataRenderer<>(new TemporalAccessorFormatter(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))));
    /**
     * The {@link Parser} used to parse the text of text field to a
     * {@link LocalDate}.
     */
    private final ObjectProperty<Parser<? extends LocalDate>> parser = new SimpleObjectProperty<>(this, "parser");
    /**
     * The currently selected {@link LocalDate}.<br> <br>
     * Default: {@link LocalDate#now()}
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
     * The number of previous months relative to {@link #yearMonth} to show.
     */
    // TODO: should be configurable with CSS?
    private final IntegerProperty previousMonths = new SimpleIntegerProperty(this, "previousMonths", 0);
    /**
     * The number of next months relative to {@link #yearMonth} to show.
     */
    // TODO: should be configurable with CSS?
    private final IntegerProperty nextMonths = new SimpleIntegerProperty(this, "nextMonths", 0);
    /**
     * Flag if the month scroll button should be shown.
     */
    // TODO: should be configurable with CSS?
    private final BooleanProperty showingMonthScrollButton = new SimpleBooleanProperty(this, "showingMonthScrollButton", true);
    /**
     * Flag if the year scroll button should be shown.
     */
    // TODO: should be configurable with CSS?
    private final BooleanProperty showingYearScrollButton = new SimpleBooleanProperty(this, "showingYearScrollButton", true);

    /**
     * Creates a new instance of this class. The style class is set to
     * 'local-date-picker'.
     */
    public LocalDatePicker() {
        getStyleClass().setAll("local-date-picker");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserAgentStylesheet() {
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

    public final void setShowingWeekOfYear(boolean showingWeekOfYear) {
        this.showingWeekOfYear.set(showingWeekOfYear);
    }

    public BooleanProperty showingWeekOfYearProperty() {
        return showingWeekOfYear;
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

    public final boolean isShowingMonthScrollButton() {
        return showingMonthScrollButton.get();
    }

    public final void setShowingMonthScrollButton(boolean showingMonthScrollButton) {
        this.showingMonthScrollButton.set(showingMonthScrollButton);
    }

    public BooleanProperty showingMonthScrollButtonProperty() {
        return showingMonthScrollButton;
    }

    public final boolean isShowingYearScrollButton() {
        return showingYearScrollButton.get();
    }

    public final void setShowingYearScrollButton(boolean showingYearScrollButton) {
        this.showingYearScrollButton.set(showingYearScrollButton);
    }

    public BooleanProperty showingYearScrollButtonProperty() {
        return showingYearScrollButton;
    }
}
