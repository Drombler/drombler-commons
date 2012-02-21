/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.scene.control.time.calendar.skin;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javax.time.calendar.DateTimeFieldRule;
import javax.time.calendar.DayOfWeek;
import javax.time.calendar.ISOChronology;
import javax.time.calendar.LocalDate;
import javax.time.calendar.YearMonth;
import org.projectx.lib.javafx.scene.control.time.calendar.LocalDateButton;
import org.projectx.lib.javafx.scene.control.time.calendar.LocalDateFoo;
import org.projectx.lib.time.calendar.DayOfWeekUtils;
import org.projectx.lib.time.calendar.YearMonthUtils;
import org.softsmithy.lib.util.Comparables;

/**
 *
 * @author puce
 */
public class LocalDateFooSkin implements Skin<LocalDateFoo> {

    public static final int DAYS_IN_WEEK = 7;
    public static final int MAX_WEEKS_IN_MONTH = 6;
    /**
     * The {@code Control} that is referencing this Skin. There is a one-to-one relationship between a {@code Skin} and
     * a {@code Control}. When a {@code Skin} is set on a {@code Control}, this variable is automatically updated.
     */
    private LocalDateFoo control;
    /**
     * This control is used to represent the YearMonthPicker.
     */
    private GridPane dayOfMonthView;
    private final List<DayOfWeek> orderedDaysOfWeek;
    private final List<Label> orderedDayOfWeekLabels = new ArrayList<>(DAYS_IN_WEEK);
    private final List<Label> weekLabels = new ArrayList<>();
    private final List<List<LocalDateButton>> dayButtons = new ArrayList<>();
    private ToggleGroup dayButtonGroup;
    private boolean adjusting = false;

    public LocalDateFooSkin(LocalDateFoo control) {
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
        orderedDaysOfWeek = DayOfWeekUtils.getOrderedDaysOfWeek(Locale.getDefault());
        initDayOfMonthView();
    }

    @Override
    public LocalDateFoo getSkinnable() {
        return control;
    }

    @Override
    public Node getNode() {
        return dayOfMonthView;
    }

    @Override
    public void dispose() {
        control = null;
        dayOfMonthView = null;
    }

    private ColumnConstraints createColumnConstraints() {
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHalignment(HPos.CENTER);
        return columnConstraints;
    }

    private void initDayOfMonthView() {
        initDayOfWeekLabels();
        initWeekLabels();
        initLocalDateButtons();
        layout();
        showYearMonth();
    }

    private void initDayOfWeekLabels() {
        for (DayOfWeek dayOfWeek : orderedDaysOfWeek) {
            Label dayOfWeekLabel = new Label(dayOfWeek.getShortText(Locale.getDefault()));
            dayOfWeekLabel.getStyleClass().add("day-of-week");
            orderedDayOfWeekLabels.add(dayOfWeekLabel);
        }
    }

    private void initWeekLabels() {
        weekLabels.clear();
        int weeks = getWeeks();
        for (int week = 0; week < weeks; week++) {
            weekLabels.add(new Label());
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
            List<LocalDateButton> weekButtons = new ArrayList<>(DAYS_IN_WEEK);
            for (int i = 0; i < DAYS_IN_WEEK; i++) {
                LocalDateButton dayButton = new LocalDateButton();
                dayButton.setMaxWidth(Double.MAX_VALUE);
                dayButton.setToggleGroup(dayButtonGroup);
                weekButtons.add(dayButton);
            }
            dayButtons.add(weekButtons);
        }
        dayButtonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle oldValue, Toggle newValue) {
                if (!adjusting) {
                    control.setSelectedDate(((LocalDateButton) newValue).getData());
                }
            }
        });
    }

    private void layout() {
        dayOfMonthView = new GridPane();
        //dayOfMonthView.setAlignment(Pos.CENTER);
        int columnIndex = control.getShowWeeks() ? 1 : 0;
        int rowIndex = 0;
        if (control.getShowWeeks()) {
            ColumnConstraints columnConstraints = createColumnConstraints();
            dayOfMonthView.getColumnConstraints().add(columnConstraints);
        }
        for (Label dayOfWeekLabel : orderedDayOfWeekLabels) {
            ColumnConstraints columnConstraints = createColumnConstraints();
            dayOfMonthView.getColumnConstraints().add(columnConstraints);
            dayOfMonthView.add(dayOfWeekLabel, columnIndex++, rowIndex);
        }
        int weeks = getWeeks();
        for (int weekIndex = 0; weekIndex < weeks; weekIndex++) {
            columnIndex = 0;
            rowIndex++;
            if (control.getShowWeeks()) {
                dayOfMonthView.add(weekLabels.get(weekIndex), columnIndex++, rowIndex);
            }
            List<LocalDateButton> weekButtons = dayButtons.get(weekIndex);
            for (int dayOfWeekIndex = 0; dayOfWeekIndex < DAYS_IN_WEEK; dayOfWeekIndex++) {
                LocalDateButton dayButton = weekButtons.get(dayOfWeekIndex);
                dayOfMonthView.add(dayButton, columnIndex++, rowIndex);
            }

        }
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
            if (control.getShowWeeks()) {
                Label weekLabel = weekLabels.get(weekIndex);
                weekLabel.setText(weekFormat.format(week));
            }
            List<LocalDateButton> weekButtons = dayButtons.get(weekIndex);
            for (int dayOfWeekIndex = 0; dayOfWeekIndex < DAYS_IN_WEEK; dayOfWeekIndex++) {
                LocalDateButton dayButton = weekButtons.get(dayOfWeekIndex);
                dayButton.setData(day);
                dayButton.setSelected(day.equals(control.getSelectedDate()));
                dayButton.setDisable(!Comparables.isInRange(day, control.selectedDateProperty().getMin(),
                        control.selectedDateProperty().getMax()));
                day = day.plusDays(1);
            }

        }
        adjusting = false;
    }
}
