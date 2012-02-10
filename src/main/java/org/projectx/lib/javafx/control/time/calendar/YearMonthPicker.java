/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.control.time.calendar;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Control;
import javax.time.calendar.YearMonth;
import org.projectx.lib.javafx.beans.property.FiniteComparableProperty;

/**
 *
 * @author puce
 */
public class YearMonthPicker extends Control {

    private final FiniteComparableProperty<YearMonth> yearMonth = new FiniteComparableProperty<>(this, "yearMonth",
            YearMonth.now());
    // TODO: should be configurable with CSS?
    private final BooleanProperty showPreviousMonthScrollButton = new SimpleBooleanProperty(this,
            "showPreviousMonthScrollButton", true);
    // TODO: should be configurable with CSS?
    private final BooleanProperty showNextMonthScrollButton = new SimpleBooleanProperty(this,
            "showNextMonthScrollButton", true);
    // TODO: should be configurable with CSS?
    private final BooleanProperty showPreviousYearScrollButton = new SimpleBooleanProperty(this,
            "showPreviousYearScrollButton", true);
    // TODO: should be configurable with CSS?
    private final BooleanProperty showNextYearScrollButton = new SimpleBooleanProperty(this, "showNextYearScrollButton",
            true);

    /**
     * Creates a new instance of this class. The style class is set to 'year-month-picker'.
     */
    public YearMonthPicker() {
        getStyleClass().setAll("year-month-picker");
    }

    @Override
    protected String getUserAgentStylesheet() {
        return YearMonthPicker.class.getResource("YearMonthPicker.css").toExternalForm();
    }

    public final YearMonth getYearMonth() {
        return yearMonth.get();
    }

    public final void setYearMonth(YearMonth yearMonth) {
        this.yearMonth.set(yearMonth);
    }

    public FiniteComparableProperty<YearMonth> yearMonthProperty() {
        return yearMonth;
    }

    public final boolean getShowPreviousMonthScrollButton() {
        return showPreviousMonthScrollButton.get();
    }

    public final void setShowPreviousMonthScrollButton(boolean showPreviousMonthScrollButton) {
        this.showPreviousMonthScrollButton.set(showPreviousMonthScrollButton);
    }

    public BooleanProperty showPreviousMonthScrollButtonProperty() {
        return showPreviousMonthScrollButton;
    }

    public final boolean getShowPreviousYearScrollButton() {
        return showPreviousYearScrollButton.get();
    }

    public final void setShowPreviousYearScrollButton(boolean showPreviousYearScrollButton) {
        this.showPreviousYearScrollButton.set(showPreviousYearScrollButton);
    }

    public BooleanProperty showPreviousYearScrollButtonProperty() {
        return showPreviousYearScrollButton;
    }

    public final boolean getShowNextMonthScrollButton() {
        return showNextMonthScrollButton.get();
    }

    public final void setShowNextMonthScrollButton(boolean showNextMonthScrollButton) {
        this.showNextMonthScrollButton.set(showNextMonthScrollButton);
    }

    public BooleanProperty showNextMonthScrollButtonProperty() {
        return showNextMonthScrollButton;
    }

    public final boolean getShowNextYearScrollButton() {
        return showNextYearScrollButton.get();
    }

    public final void setShowNextYearScrollButton(boolean showNextYearScrollButton) {
        this.showNextYearScrollButton.set(showNextYearScrollButton);
    }

    public BooleanProperty showNextYearScrollButtonProperty() {
        return showNextYearScrollButton;
    }
}
