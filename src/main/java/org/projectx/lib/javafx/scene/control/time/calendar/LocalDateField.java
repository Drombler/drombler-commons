/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.scene.control.time.calendar;

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
import javax.time.calendar.format.DateTimeFormatter;
import javax.time.calendar.format.DateTimeFormatters;
import org.projectx.lib.javafx.beans.property.FiniteComparableProperty;
import org.projectx.lib.javafx.scene.control.CellRenderer;
import org.projectx.lib.javafx.scene.control.skin.Stylesheets;
import org.softsmithy.lib.text.Parser;

/**
 *
 * @author puce
 */
// TODO: good to have this in a separate class? Or should a property on LocalDatePicker be used to show "as field"
public class LocalDateField extends Control {

    private final ObjectProperty<CellRenderer<? super LocalDate>> cellRenderer = new SimpleObjectProperty<CellRenderer<? super LocalDate>>(
            this, "cellRenderer", new LocalDateCellRenderer(DateTimeFormatters.longDate(Locale.getDefault())));
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

    public final CellRenderer<? super LocalDate> getCellRenderer() {
        return cellRenderer.get();
    }

    public final void setCellRenderer(CellRenderer<? super LocalDate> cellRenderer) {
        this.cellRenderer.set(cellRenderer);
    }

    public ObjectProperty<CellRenderer<? super LocalDate>> cellRendererProperty() {
        return cellRenderer;
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
