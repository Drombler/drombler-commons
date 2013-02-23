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
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Control;
import javax.time.calendar.YearMonth;
import org.drombler.commons.fx.beans.property.LimitedComparableProperty;
import org.drombler.commons.fx.scene.control.time.calendar.impl.skin.Stylesheets;

/**
 * A {@link YearMonth} spinner.
 *
 * @author puce
 */
public class YearMonthSpinner extends Control {

    /**
     * The {@link YearMonth} value of ths spinner.
     */
    private final LimitedComparableProperty<YearMonth> yearMonth = new LimitedComparableProperty<>(this, "yearMonth",
            YearMonth.now());
    // TODO: should be configurable with CSS?
    /**
     * Flag if the previous month scroll button should be shown.
     */
    private final BooleanProperty showPreviousMonthScrollButton = new SimpleBooleanProperty(this,
            "showPreviousMonthScrollButton", true);
    // TODO: should be configurable with CSS?
    /**
     * Flag if the next month scroll button should be shown.
     */
    private final BooleanProperty showNextMonthScrollButton = new SimpleBooleanProperty(this,
            "showNextMonthScrollButton", true);
    // TODO: should be configurable with CSS?
    /**
     * Flag if the previous year scroll button should be shown.
     */
    private final BooleanProperty showPreviousYearScrollButton = new SimpleBooleanProperty(this,
            "showPreviousYearScrollButton", true);
    // TODO: should be configurable with CSS?
    /**
     * Flag if the next year scroll button should be shown.
     */
    private final BooleanProperty showNextYearScrollButton = new SimpleBooleanProperty(this, "showNextYearScrollButton",
            true);
    // TODO: should be configurable with CSS?
    /**
     * Flag if the space for the year scroll buttons should always be reserved,
     * even if the buttons are not shown.
     */
    private final BooleanProperty alwaysYearScrollButtonSpaceReserved = new SimpleBooleanProperty(this,
            "alwaysYearScrollButtonSpaceReserved",
            false);
    // TODO: should be configurable with CSS?
    /**
     * Flag if the space for the month scroll buttons should always be reserved,
     * even if the buttons are not shown.
     */
    private final BooleanProperty alwaysMonthScrollButtonSpaceReserved = new SimpleBooleanProperty(this,
            "alwaysMonthScrollButtonSpaceReserved",
            false);

    /**
     * Creates a new instance of this class. The style class is set to
     * 'year-month-spinner'.
     */
    public YearMonthSpinner() {
        getStyleClass().setAll("year-month-spinner");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getUserAgentStylesheet() {
        return Stylesheets.getDefaultStylesheet();
    }

    public final YearMonth getYearMonth() {
        return yearMonth.get();
    }

    public final void setYearMonth(YearMonth yearMonth) {
        this.yearMonth.set(yearMonth);
    }

    public LimitedComparableProperty<YearMonth> yearMonthProperty() {
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

    public final boolean isAlwaysYearScrollButtonSpaceReserved() {
        return alwaysYearScrollButtonSpaceReserved.get();
    }

    public final void setAlwaysYearScrollButtonSpaceReserved(boolean alwaysYearScrollButtonSpaceReserved) {
        this.alwaysYearScrollButtonSpaceReserved.set(alwaysYearScrollButtonSpaceReserved);
    }

    public BooleanProperty alwaysYearScrollButtonSpaceReservedProperty() {
        return alwaysYearScrollButtonSpaceReserved;
    }

    public final boolean isAlwaysMonthScrollButtonSpaceReserved() {
        return alwaysMonthScrollButtonSpaceReserved.get();
    }

    public final void setAlwaysMonthScrollButtonSpaceReserved(boolean alwaysMonthScrollButtonSpaceReserved) {
        this.alwaysMonthScrollButtonSpaceReserved.set(alwaysMonthScrollButtonSpaceReserved);
    }

    public BooleanProperty alwaysMonthScrollButtonSpaceReservedProperty() {
        return alwaysMonthScrollButtonSpaceReserved;
    }
}
