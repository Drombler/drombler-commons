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
     * The {@link YearMonth} value of this spinner.
     */
    private final LimitedComparableProperty<YearMonth> yearMonth = new LimitedComparableProperty<>(this, "yearMonth",
            YearMonth.now());
    // TODO: should be configurable with CSS?
    /**
     * Flag if the previous month scroll button should be shown.
     */
    private final BooleanProperty showingPreviousMonthScrollButton = new SimpleBooleanProperty(this,
            "showingPreviousMonthScrollButton", true);
    // TODO: should be configurable with CSS?
    /**
     * Flag if the next month scroll button should be shown.
     */
    private final BooleanProperty showingNextMonthScrollButton = new SimpleBooleanProperty(this,
            "showingNextMonthScrollButton", true);
    // TODO: should be configurable with CSS?
    /**
     * Flag if the previous year scroll button should be shown.
     */
    private final BooleanProperty showingPreviousYearScrollButton = new SimpleBooleanProperty(this,
            "showingPreviousYearScrollButton", true);
    // TODO: should be configurable with CSS?
    /**
     * Flag if the next year scroll button should be shown.
     */
    private final BooleanProperty showingNextYearScrollButton = new SimpleBooleanProperty(this, "showingNextYearScrollButton",
            true);
    // TODO: should be configurable with CSS?
    /**
     * Flag if the space for the year scroll buttons should always be reserved,
     * even if the buttons are not shown.
     */
    private final BooleanProperty alwaysReservingYearScrollButtonSpace = new SimpleBooleanProperty(this,
            "alwaysReservingYearScrollButtonSpace",
            false);
    // TODO: should be configurable with CSS?
    /**
     * Flag if the space for the month scroll buttons should always be reserved,
     * even if the buttons are not shown.
     */
    private final BooleanProperty alwaysReservingMonthScrollButtonSpace = new SimpleBooleanProperty(this,
            "alwaysReservingMonthScrollButtonSpace",
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

    public final boolean isShowingPreviousMonthScrollButton() {
        return showingPreviousMonthScrollButton.get();
    }

    public final void setShowingPreviousMonthScrollButton(boolean showingPreviousMonthScrollButton) {
        this.showingPreviousMonthScrollButton.set(showingPreviousMonthScrollButton);
    }

    public BooleanProperty showingPreviousMonthScrollButtonProperty() {
        return showingPreviousMonthScrollButton;
    }

    public final boolean isShowingPreviousYearScrollButton() {
        return showingPreviousYearScrollButton.get();
    }

    public final void setShowingPreviousYearScrollButton(boolean showingPreviousYearScrollButton) {
        this.showingPreviousYearScrollButton.set(showingPreviousYearScrollButton);
    }

    public BooleanProperty showingPreviousYearScrollButtonProperty() {
        return showingPreviousYearScrollButton;
    }

    public final boolean isShowingNextMonthScrollButton() {
        return showingNextMonthScrollButton.get();
    }

    public final void setShowingNextMonthScrollButton(boolean showingNextMonthScrollButton) {
        this.showingNextMonthScrollButton.set(showingNextMonthScrollButton);
    }

    public BooleanProperty showingNextMonthScrollButtonProperty() {
        return showingNextMonthScrollButton;
    }

    public final boolean isShowingNextYearScrollButton() {
        return showingNextYearScrollButton.get();
    }

    public final void setShowingNextYearScrollButton(boolean showingNextYearScrollButton) {
        this.showingNextYearScrollButton.set(showingNextYearScrollButton);
    }

    public BooleanProperty showingNextYearScrollButtonProperty() {
        return showingNextYearScrollButton;
    }

    public final boolean isAlwaysReservingYearScrollButtonSpace() {
        return alwaysReservingYearScrollButtonSpace.get();
    }

    public final void setAlwaysReservingYearScrollButtonSpace(boolean alwaysReservingYearScrollButtonSpace) {
        this.alwaysReservingYearScrollButtonSpace.set(alwaysReservingYearScrollButtonSpace);
    }

    public BooleanProperty alwaysReservingYearScrollButtonSpaceProperty() {
        return alwaysReservingYearScrollButtonSpace;
    }

    public final boolean isAlwaysReservingMonthScrollButtonSpace() {
        return alwaysReservingMonthScrollButtonSpace.get();
    }

    public final void setAlwaysReservingMonthScrollButtonSpace(boolean alwaysReservingMonthScrollButtonSpace) {
        this.alwaysReservingMonthScrollButtonSpace.set(alwaysReservingMonthScrollButtonSpace);
    }

    public BooleanProperty alwaysReservingMonthScrollButtonSpaceProperty() {
        return alwaysReservingMonthScrollButtonSpace;
    }
}
