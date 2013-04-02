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
package org.drombler.commons.fx.scene.control.time.calendar.impl.skin;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javax.time.calendar.LocalDate;
import javax.time.calendar.YearMonth;
import org.drombler.commons.fx.scene.control.time.calendar.LocalDateChooser;
import org.drombler.commons.fx.scene.control.time.calendar.LocalDateFixedYearMonthChooser;
import org.drombler.commons.fx.scene.control.time.calendar.YearMonthSpinner;
import org.drombler.commons.time.calendar.LocalDateUtils;

/**
 *
 * @author puce
 */
public class LocalDateChooserSkin implements Skin<LocalDateChooser> {

    /**
     * The {@code Control} that is referencing this Skin. There is a one-to-one
     * relationship between a {@code Skin} and a {@code Control}. When a
     * {@code Skin} is set on a {@code Control}, this variable is automatically
     * updated.
     */
    private LocalDateChooser control;
    /**
     * This control is used to represent the YearMonthSpinner.
     */
    private HBox pane = new HBox();
    private List<YearMonthSpinner> yearMonthSpinners;
    private List<LocalDateFixedYearMonthChooser> localDateFixedYearMonthChoosers;
    private boolean adjusting = false;

    public LocalDateChooserSkin(LocalDateChooser control) {
        this.control = control;
        control.selectedDateProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> ov, LocalDate oldVal, LocalDate newVal) {
                setSelectedDate(newVal);
            }
        });
        control.selectedDateProperty().maxProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> ov, LocalDate oldVal, LocalDate newVal) {
                setMaxYearMonth(newVal);
            }
        });
        control.selectedDateProperty().minProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> ov, LocalDate oldVal, LocalDate newVal) {
                setMinYearMonth(newVal);
            }
        });
        control.yearMonthProperty().addListener(new ChangeListener<YearMonth>() {
            @Override
            public void changed(ObservableValue<? extends YearMonth> ov, YearMonth oldVal, YearMonth newVal) {
                adjusting = true;
                showMonths(newVal);
                adjusting = false;
            }
        });

        init();

    }

    private void init() {
        int months = getMonthsToShow();
        yearMonthSpinners = new ArrayList<>(months);
        localDateFixedYearMonthChoosers = new ArrayList<>(months);
        for (int monthIndex = 0; monthIndex < months; monthIndex++) {
            YearMonthSpinner yearMonthPicker = new YearMonthSpinner();
            yearMonthPicker.setAlwaysReservingYearScrollButtonSpace(months > 1 && control.isShowingYearScrollButton());
            yearMonthPicker.setAlwaysReservingMonthScrollButtonSpace(months > 1 && control.isShowingMonthScrollButton());
            yearMonthPicker.setShowingPreviousYearScrollButton(monthIndex == 0 && control.isShowingYearScrollButton());
            yearMonthPicker.setShowingPreviousMonthScrollButton(monthIndex == 0 && control.isShowingMonthScrollButton());
            yearMonthPicker.setShowingNextMonthScrollButton(monthIndex == months - 1 && control.isShowingMonthScrollButton());
            yearMonthPicker.setShowingNextYearScrollButton(monthIndex == months - 1 && control.isShowingYearScrollButton());

            final int currentMonthIndex = monthIndex;
            yearMonthPicker.yearMonthProperty().addListener(new ChangeListener<YearMonth>() {
                @Override
                public void changed(ObservableValue<? extends YearMonth> ov, YearMonth oldVal, YearMonth newVal) {
                    if (!adjusting) {
                        if (currentMonthIndex < control.getPreviousMonths()) {
                            control.setYearMonth(newVal.plusMonths(control.getPreviousMonths() - currentMonthIndex));
                        } else if (currentMonthIndex == control.getPreviousMonths()) {
                            control.setYearMonth(newVal);
                        } else {
                            control.setYearMonth(newVal.minusMonths(currentMonthIndex - control.getPreviousMonths()));
                        }
                    }
                }
            });
            yearMonthSpinners.add(yearMonthPicker);
            localDateFixedYearMonthChoosers.add(createLocalDateFixedYearMonthChooser());
        }
        layout();
        setMaxYearMonth(control.selectedDateProperty().getMax());
        setMinYearMonth(control.selectedDateProperty().getMin());
        showMonths(control.getYearMonth());
    }

    private LocalDateFixedYearMonthChooser createLocalDateFixedYearMonthChooser() {
        LocalDateFixedYearMonthChooser localDateFixedYearMonthChooser = new LocalDateFixedYearMonthChooser();
        localDateFixedYearMonthChooser.selectedDateProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> ov, LocalDate oldVal, LocalDate newVal) {
                control.selectedDateProperty().set(newVal);
            }
        });
        localDateFixedYearMonthChooser.previousWeeksProperty().bind(control.previousWeeksProperty());
        localDateFixedYearMonthChooser.nextWeeksProperty().bind(control.nextWeeksProperty());
        localDateFixedYearMonthChooser.showingWeekOfYearProperty().bind(control.showingWeekOfYearProperty());
        return localDateFixedYearMonthChooser;
    }

    private void layout() {
        pane.setSpacing(5d);
        int months = getMonthsToShow();
        for (int monthIndex = 0; monthIndex < months; monthIndex++) {
            BorderPane monthPane = new BorderPane();
            monthPane.setTop(yearMonthSpinners.get(monthIndex));
            monthPane.setCenter(localDateFixedYearMonthChoosers.get(monthIndex));
//            monthPane.setStyle("-fx-background-color: " + (monthIndex == 1 ? "red" : "blue") + ";");

            pane.getChildren().add(monthPane);
        }
    }

    @Override
    public LocalDateChooser getSkinnable() {
        return control;
    }

    @Override
    public Node getNode() {
        return pane;
    }

    @Override
    public void dispose() {
        control = null;
        pane = null;
        yearMonthSpinners.clear();
        yearMonthSpinners.clear();
    }

    private void showMonths(YearMonth yearMonth) {
        YearMonth startYearMonth = yearMonth.minusMonths(control.getPreviousMonths());

        int months = getMonthsToShow();
        YearMonth ym = startYearMonth;
        for (int monthIndex = 0; monthIndex < months; monthIndex++) {
            yearMonthSpinners.get(monthIndex).setYearMonth(ym);
            localDateFixedYearMonthChoosers.get(monthIndex).setYearMonth(ym);
            ym = ym.plusMonths(1);
        }
    }

    private int getMonthsToShow() {
        return control.getPreviousMonths() + 1 + control.getNextMonths();
    }

    private void setMaxYearMonth(LocalDate maxDate) {
        YearMonth max = LocalDateUtils.getYearMonth(maxDate);
        int months = getMonthsToShow();
        for (int monthIndex = 0; monthIndex < months; monthIndex++) {
            if (monthIndex < control.getPreviousMonths()) {
                yearMonthSpinners.get(monthIndex).yearMonthProperty().setMax(max != null ? max.minusMonths(
                        control.getPreviousMonths() - monthIndex) : null);

            } else if (monthIndex == control.getPreviousMonths()) {
                yearMonthSpinners.get(monthIndex).yearMonthProperty().setMax(max);
            } else {
                yearMonthSpinners.get(monthIndex).yearMonthProperty().setMax(max != null ? max.plusMonths(
                        monthIndex - control.getPreviousMonths()) : null);
            }

            localDateFixedYearMonthChoosers.get(monthIndex).selectedDateProperty().setMax(maxDate);
        }
    }

    private void setMinYearMonth(LocalDate minDate) {
        YearMonth min = LocalDateUtils.getYearMonth(minDate);
        int months = getMonthsToShow();
        for (int monthIndex = 0; monthIndex < months; monthIndex++) {
            if (monthIndex < control.getPreviousMonths()) {
                yearMonthSpinners.get(monthIndex).yearMonthProperty().setMin(min != null ? min.minusMonths(
                        control.getPreviousMonths() - monthIndex) : null);

            } else if (monthIndex == control.getPreviousMonths()) {
                yearMonthSpinners.get(monthIndex).yearMonthProperty().setMin(min);
            } else {
                yearMonthSpinners.get(monthIndex).yearMonthProperty().setMin(min != null ? min.plusMonths(
                        monthIndex - control.getPreviousMonths()) : null);
            }

            localDateFixedYearMonthChoosers.get(monthIndex).selectedDateProperty().setMin(minDate);
        }
    }

    private void setSelectedDate(LocalDate selectedDate) {
        int months = getMonthsToShow();
        for (int monthIndex = 0; monthIndex < months; monthIndex++) {
            localDateFixedYearMonthChoosers.get(monthIndex).setSelectedDate(selectedDate);
        }
    }
}
