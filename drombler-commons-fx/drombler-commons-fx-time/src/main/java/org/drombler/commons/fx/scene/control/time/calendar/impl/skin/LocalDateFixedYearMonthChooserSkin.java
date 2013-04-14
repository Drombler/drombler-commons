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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javax.time.calendar.DateTimeFieldRule;
import javax.time.calendar.DayOfWeek;
import javax.time.calendar.ISOChronology;
import javax.time.calendar.LocalDate;
import javax.time.calendar.YearMonth;
import org.drombler.commons.fx.scene.control.time.calendar.LocalDateFixedYearMonthChooser;
import org.drombler.commons.time.calendar.DayOfWeekUtils;
import static org.drombler.commons.time.calendar.DayOfWeekUtils.DAYS_IN_WEEK;
import org.drombler.commons.time.calendar.LocalDateUtils;
import org.drombler.commons.time.calendar.YearMonthUtils;
import org.softsmithy.lib.util.Comparables;

/**
 *
 * @author puce
 */
public class LocalDateFixedYearMonthChooserSkin implements Skin<LocalDateFixedYearMonthChooser> {

    public static final int MAX_WEEKS_IN_MONTH = 6;
    private static final String DAY_OUT_OF_YEAR_MONTH_STYLE_CLASS = "day-out-of-year-month";
    private static final String DAY_IN_YEAR_MONTH_STYLE_CLASS = "day-in-year-month";
    /**
     * The {@code Control} that is referencing this Skin. There is a one-to-one
     * relationship between a {@code Skin} and a {@code Control}. When a
     * {@code Skin} is set on a {@code Control}, this variable is automatically
     * updated.
     */
    private LocalDateFixedYearMonthChooser control;
    /**
     * This control is used to represent the YearMonthPicker.
     */
    private BorderPane node = new BorderPane();
    private final List<DayOfWeek> orderedDaysOfWeek;
    private final List<Label> orderedDayOfWeekLabels = new ArrayList<>(DAYS_IN_WEEK);
    private final List<Label> weekLabels = new ArrayList<>();
    private final List<List<LocalDateToggleButton>> dayButtons = new ArrayList<>();
    private final VBox weekLabelParent = new VBox();
    private ToggleGroup dayButtonGroup;
    private boolean adjusting = false;

    public LocalDateFixedYearMonthChooserSkin(LocalDateFixedYearMonthChooser control) {
        this.control = control;
        control.selectedDateProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> ov, LocalDate oldVal, LocalDate newVal) {
                showYearMonth();
            }
        });
        control.yearMonthProperty().addListener(new ChangeListener<YearMonth>() {
            @Override
            public void changed(ObservableValue<? extends YearMonth> ov, YearMonth t, YearMonth t1) {
                showYearMonth();
            }
        });
        control.previousWeeksProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                numberOfWeeksChanged();
            }
        });
        control.nextWeeksProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                numberOfWeeksChanged();
            }
        });
        control.showingWeekOfYearProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                numberOfWeeksChanged();
            }
        });
        orderedDaysOfWeek = DayOfWeekUtils.getOrderedDaysOfWeek(Locale.getDefault());
        initDayOfMonthView();
    }

    private void numberOfWeeksChanged() {
        initWeekLabels();
        layout();
        showYearMonth();
    }

    @Override
    public LocalDateFixedYearMonthChooser getSkinnable() {
        return control;
    }

    @Override
    public Node getNode() {
        return node;
    }

    @Override
    public void dispose() {
        control = null;
        node = null;
    }

    private ColumnConstraints createColumnConstraints() {
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHalignment(HPos.CENTER);
        columnConstraints.setHgrow(Priority.ALWAYS);
        columnConstraints.setFillWidth(true);
        return columnConstraints;
    }

    private RowConstraints createRowConstraints() {
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.ALWAYS);
        rowConstraints.setFillHeight(true);
        return rowConstraints;
    }

    private void initDayOfMonthView() {
//        LocalDateToggleButton dateButton = createDayButton();
//        dateButton.setText("22");
//        double prefWidth = dateButton.getPrefWidth();
//        double prefHeight = dateButton.getPrefHeight();
        initDayOfWeekLabels();
        initWeekLabels();
        initLocalDateButtons();
        layout();
        showYearMonth();
    }

    private void initDayOfWeekLabels() {
        for (DayOfWeek dayOfWeek : orderedDaysOfWeek) {
            Label dayOfWeekLabel = createDayOfWeekLabel(dayOfWeek);
            orderedDayOfWeekLabels.add(dayOfWeekLabel);
        }
    }

    private void initWeekLabels() {
        weekLabels.clear();
        weekLabelParent.getStyleClass().add("week-label");
        int weeks = getWeeks();
        for (int week = 0; week < weeks; week++) {
            Label weekLabel = createWeekLabel();
            weekLabels.add(weekLabel);
            weekLabelParent.getChildren().add(weekLabel);
        }
    }

    private int getWeeks() {
        return control.getPreviousWeeks() + MAX_WEEKS_IN_MONTH + control.getNextWeeks();
    }

    private void initLocalDateButtons() {
        dayButtons.clear();
        int weeks = getWeeks();
        dayButtonGroup = new ToggleGroup();
        for (int week = 0; week < weeks; week++) {
            List<LocalDateToggleButton> weekButtons = new ArrayList<>(DAYS_IN_WEEK);
            for (int i = 0; i < DAYS_IN_WEEK; i++) {
                LocalDateToggleButton dayButton = createDayButton();
                dayButton.setToggleGroup(dayButtonGroup);
                weekButtons.add(dayButton);
            }
            dayButtons.add(weekButtons);
        }
        dayButtonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle oldValue, Toggle newValue) {
                if (!adjusting) {
                    control.setSelectedDate(((LocalDateToggleButton) newValue).getData());
                }
            }
        });
    }

    private void layout() {
        node.setTop(createDayOfWeekPane());
        if (control.isShowingWeekOfYear()) {
            node.setLeft(weekLabelParent);
        }
        node.setCenter(createDayOfMonthPane());
    }

    private void showYearMonth() {
        adjusting = true;
        LocalDate firstDayOfMonth = YearMonthUtils.atFirstDay(control.getYearMonth());
        int firstDayOfWeekIndex = orderedDaysOfWeek.indexOf(firstDayOfMonth.getDayOfWeek());
        LocalDate startDay = firstDayOfMonth.minusDays(firstDayOfWeekIndex).minusWeeks(control.getPreviousWeeks());

        DateTimeFieldRule<Integer> weekOfWeekBasedYearRule = ISOChronology.weekOfWeekBasedYearRule();
        int weeks = getWeeks();
        LocalDate day = startDay;
        NumberFormat weekFormat = NumberFormat.getIntegerInstance();
        weekFormat.setMinimumIntegerDigits(2);
        for (int weekIndex = 0; weekIndex < weeks; weekIndex++) {
            int week = weekOfWeekBasedYearRule.getInt(day);
            if (control.isShowingWeekOfYear()) {
                Label weekLabel = weekLabels.get(weekIndex);
                weekLabel.setText(weekFormat.format(week));
            }
            List<LocalDateToggleButton> weekButtons = dayButtons.get(weekIndex);
            for (int dayOfWeekIndex = 0; dayOfWeekIndex < DAYS_IN_WEEK; dayOfWeekIndex++) {
                LocalDateToggleButton dayButton = weekButtons.get(dayOfWeekIndex);
                dayButton.setData(day);
                dayButton.setSelected(day.equals(control.getSelectedDate()));
                dayButton.setDisable(!Comparables.isInRange(day, control.selectedDateProperty().getMin(),
                        control.selectedDateProperty().getMax()));
                if (LocalDateUtils.getYearMonth(day).equals(control.getYearMonth())) {
                    dayButton.getStyleClass().remove(DAY_OUT_OF_YEAR_MONTH_STYLE_CLASS);
                    dayButton.getStyleClass().add(DAY_IN_YEAR_MONTH_STYLE_CLASS);
                } else {
                    dayButton.getStyleClass().remove(DAY_IN_YEAR_MONTH_STYLE_CLASS);
                    dayButton.getStyleClass().add(DAY_OUT_OF_YEAR_MONTH_STYLE_CLASS);
                }
                day = day.plusDays(1);
            }

        }
        adjusting = false;
    }

    private Pane createDayOfMonthPane() {
        GridPane dayOfMonthPane = new GridPane();
        addColumnConstraints(dayOfMonthPane);
        addRowConstraints(dayOfMonthPane);
//        dayOfMonthView.setGridLinesVisible(true);
//        dayOfMonthView.setStyle("-fx-background-color: rgba(0, 255, 255, 125)");
        int weeks = getWeeks();
        for (int weekIndex = 0; weekIndex < weeks; weekIndex++) {
            List<LocalDateToggleButton> weekButtons = dayButtons.get(weekIndex);
            for (int dayOfWeekIndex = 0; dayOfWeekIndex < DAYS_IN_WEEK; dayOfWeekIndex++) {
                LocalDateToggleButton dayButton = weekButtons.get(dayOfWeekIndex);
                dayOfMonthPane.add(dayButton, dayOfWeekIndex, weekIndex);
            }

        }
        return dayOfMonthPane;
    }

    private Pane createDayOfWeekPane() {
        HBox dayOfWeekPane = new HBox();
        if (control.isShowingWeekOfYear()) {
            Label placeHolderLabel = new Label();
//            placeHolderLabel.setMaxWidth(Double.MAX_VALUE);
//            HBox.setHgrow(placeHolderLabel, Priority.ALWAYS);
            placeHolderLabel.prefWidthProperty().bind(weekLabels.get(0).widthProperty());
            dayOfWeekPane.getChildren().add(placeHolderLabel);
        }
        int dayInWeekIndex = 0;
        for (Label dayOfWeekLabel : orderedDayOfWeekLabels) {
            dayOfWeekLabel.prefWidthProperty().bind(dayButtons.get(0).get(dayInWeekIndex).widthProperty());
            dayOfWeekPane.getChildren().add(dayOfWeekLabel);
            dayInWeekIndex++;
        }
        return dayOfWeekPane;
    }

    private LocalDateToggleButton createDayButton() {
        LocalDateToggleButton dayButton = new LocalDateToggleButton();
        dayButton.setMaxWidth(Double.MAX_VALUE);
        dayButton.setMaxHeight(Double.MAX_VALUE);
        return dayButton;
    }

    private Label createDayOfWeekLabel(DayOfWeek dayOfWeek) {
        Label dayOfWeekLabel = new Label(dayOfWeek.getShortText(Locale.getDefault()));
        dayOfWeekLabel.getStyleClass().add("day-of-week");
        dayOfWeekLabel.setAlignment(Pos.BASELINE_CENTER);
        dayOfWeekLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(dayOfWeekLabel, Priority.ALWAYS);
        //  dayOfWeekLabel.setPrefSize(prefWidth, prefWidth);
        return dayOfWeekLabel;
    }

    private Label createWeekLabel() {
        final Label weekLabel = new Label();
        //            weekLabel.setPrefSize(prefWidth, prefWidth);
        weekLabel.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(weekLabel, Priority.ALWAYS);
        return weekLabel;
    }

    private void addColumnConstraints(GridPane dayOfMonthPane) {
        for (int dayInWeekIndex = 0; dayInWeekIndex < DAYS_IN_WEEK; dayInWeekIndex++) {
            dayOfMonthPane.getColumnConstraints().add(createColumnConstraints());
        }
    }

    private void addRowConstraints(GridPane dayOfMonthPane) {
        int weeks = getWeeks();
        for (int weekIndex = 0; weekIndex < weeks; weekIndex++) {
            dayOfMonthPane.getRowConstraints().add(createRowConstraints());
        }

    }
}
