/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.scene.control.time.calendar.skin;

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
import org.projectx.lib.javafx.scene.control.time.calendar.LocalDateFoo;
import org.projectx.lib.javafx.scene.control.time.calendar.LocalDatePicker;
import org.projectx.lib.javafx.scene.control.time.calendar.YearMonthPicker;
import org.projectx.lib.time.calendar.LocalDateUtils;

/**
 *
 * @author puce
 */
public class LocalDatePickerSkin implements Skin<LocalDatePicker> {

    /**
     * The {@code Control} that is referencing this Skin. There is a one-to-one relationship between a {@code Skin} and
     * a {@code Control}. When a {@code Skin} is set on a {@code Control}, this variable is automatically updated.
     */
    private LocalDatePicker control;
    /**
     * This control is used to represent the YearMonthPicker.
     */
    private HBox pane = new HBox();
    private List<YearMonthPicker> yearMonthPickers;
    private List<LocalDateFoo> localDateFoos;
    private boolean adjusting = false;

    public LocalDatePickerSkin(LocalDatePicker control) {
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
        int months = getMonths();
        yearMonthPickers = new ArrayList<>(months);
        localDateFoos = new ArrayList<>(months);
        for (int monthIndex = 0; monthIndex < months; monthIndex++) {
            YearMonthPicker yearMonthPicker = new YearMonthPicker();
            yearMonthPicker.setAlwaysYearScrollButtonSpaceReserved(months > 1 && control.getShowYearScrollButton());
            yearMonthPicker.setAlwaysMonthScrollButtonSpaceReserved(months > 1 && control.getShowMonthScrollButton());
            yearMonthPicker.setShowPreviousYearScrollButton(monthIndex == 0 && control.getShowYearScrollButton());
            yearMonthPicker.setShowPreviousMonthScrollButton(monthIndex == 0 && control.getShowMonthScrollButton());
            yearMonthPicker.setShowNextMonthScrollButton(monthIndex == months - 1 && control.getShowMonthScrollButton());
            yearMonthPicker.setShowNextYearScrollButton(monthIndex == months - 1 && control.getShowYearScrollButton());
            
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
            yearMonthPickers.add(yearMonthPicker);

            LocalDateFoo localDateFoo = new LocalDateFoo();
            localDateFoo.selectedDateProperty().addListener(new ChangeListener<LocalDate>() {

                @Override
                public void changed(ObservableValue<? extends LocalDate> ov, LocalDate oldVal, LocalDate newVal) {
                    control.selectedDateProperty().set(newVal);
                }
            });
            localDateFoos.add(localDateFoo);
        }
        layout();
        setMaxYearMonth(control.selectedDateProperty().getMax());
        setMinYearMonth(control.selectedDateProperty().getMin());
        showMonths(control.getYearMonth());
    }

    private void layout() {
        int months = getMonths();
        for (int monthIndex = 0; monthIndex < months; monthIndex++) {
            BorderPane monthPane = new BorderPane();
            monthPane.setTop(yearMonthPickers.get(monthIndex));
            monthPane.setCenter(localDateFoos.get(monthIndex));
//            monthPane.setStyle("-fx-background-color: " + (monthIndex == 1 ? "red" : "blue") + ";");

            pane.getChildren().add(monthPane);
        }
    }

    @Override
    public LocalDatePicker getSkinnable() {
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
        yearMonthPickers.clear();
        yearMonthPickers.clear();
    }

    private void showMonths(YearMonth yearMonth) {
        YearMonth startYearMonth = yearMonth.minusMonths(control.getPreviousMonths());

        int months = getMonths();
        YearMonth ym = startYearMonth;
        for (int monthIndex = 0; monthIndex < months; monthIndex++) {
            yearMonthPickers.get(monthIndex).setYearMonth(ym);
            localDateFoos.get(monthIndex).setYearMonth(ym);
            ym = ym.plusMonths(1);
        }
    }

    private int getMonths() {
        return control.getPreviousMonths() + 1 + control.getNextMonths();
    }

    private void setMaxYearMonth(LocalDate maxDate) {
        YearMonth max = LocalDateUtils.getYearMonth(maxDate);
        int months = getMonths();
        for (int monthIndex = 0; monthIndex < months; monthIndex++) {
            if (monthIndex < control.getPreviousMonths()) {
                yearMonthPickers.get(monthIndex).yearMonthProperty().setMax(max != null ? max.minusMonths(
                        control.getPreviousMonths() - monthIndex) : null);

            } else if (monthIndex == control.getPreviousMonths()) {
                yearMonthPickers.get(monthIndex).yearMonthProperty().setMax(max);
            } else {
                yearMonthPickers.get(monthIndex).yearMonthProperty().setMax(max != null ? max.plusMonths(
                        monthIndex - control.getPreviousMonths()) : null);
            }

            localDateFoos.get(monthIndex).selectedDateProperty().setMax(maxDate);
        }
    }

    private void setMinYearMonth(LocalDate minDate) {
        YearMonth min = LocalDateUtils.getYearMonth(minDate);
        int months = getMonths();
        for (int monthIndex = 0; monthIndex < months; monthIndex++) {
            if (monthIndex < control.getPreviousMonths()) {
                yearMonthPickers.get(monthIndex).yearMonthProperty().setMin(min != null ? min.minusMonths(
                        control.getPreviousMonths() - monthIndex) : null);

            } else if (monthIndex == control.getPreviousMonths()) {
                yearMonthPickers.get(monthIndex).yearMonthProperty().setMin(min);
            } else {
                yearMonthPickers.get(monthIndex).yearMonthProperty().setMin(min != null ? min.plusMonths(
                        monthIndex - control.getPreviousMonths()) : null);
            }

            localDateFoos.get(monthIndex).selectedDateProperty().setMin(minDate);
        }
    }

    private void setSelectedDate(LocalDate selectedDate) {
        int months = getMonths();
        for (int monthIndex = 0; monthIndex < months; monthIndex++) {
            localDateFoos.get(monthIndex).setSelectedDate(selectedDate);
        }
    }
}
