/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.control.time.calendar.skin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.adapter.JavaBeanObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Skin;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javax.time.calendar.MonthOfYear;
import javax.time.calendar.Year;
import javax.time.calendar.YearMonth;
import org.projectx.lib.javafx.beans.property.FiniteComparableProperty;
import org.projectx.lib.javafx.control.DataButton;
import org.projectx.lib.javafx.control.ObjectCellRenderer;
import org.projectx.lib.javafx.control.time.calendar.MonthOfYearCellRenderer;
import org.projectx.lib.javafx.control.time.calendar.MonthOfYearComboBox;
import org.projectx.lib.javafx.control.time.calendar.YearCellRenderer;
import org.projectx.lib.javafx.control.time.calendar.YearField;
import org.projectx.lib.javafx.control.time.calendar.YearMonthPicker;
import org.projectx.lib.time.calendar.MonthOfYearComparator;
import org.softsmithy.lib.util.Comparables;

/**
 *
 * @author puce
 */
public class YearMonthPickerSkin implements Skin<YearMonthPicker> {

    /**
     * The {@code Control} that is referencing this Skin. There is a one-to-one relationship between a {@code Skin} and
     * a {@code Control}. When a {@code Skin} is set on a {@code Control}, this variable is automatically updated.
     */
    private YearMonthPicker control;
    /**
     * This control is used to represent the YearMonthPicker.
     */
    private HBox pane = new HBox();
    private DataButton<MonthOfYear> monthOfYearButton = new DataButton<>(new MonthOfYearCellRenderer());
    private DataButton<Year> yearButton = new DataButton<>(new YearCellRenderer());
    private Button previousMonthButton = new Button("<");
    private Button nextMonthButton = new Button(">");
    private Button previousYearButton = new Button("<<");
    private Button nextYearButton = new Button(">>");
    private MonthOfYearComboBox monthOfYearEditor;
    private YearField yearEditor;
    private int monthColumnIndex = 0;
    private int yearColumnIndex = 1;

    public YearMonthPickerSkin(final YearMonthPicker control) {
        this.control = control;
        control.yearMonthProperty().addListener(new ChangeListener<YearMonth>() {

            @Override
            public void changed(ObservableValue<? extends YearMonth> ov, YearMonth oldVal, YearMonth newVal) {
                applyData(newVal);
            }
        });
        layout();
        monthOfYearButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                pane.getChildren().set(monthColumnIndex, getMonthOfYearEditor());
            }
        });

        yearButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                pane.getChildren().set(yearColumnIndex, getYearEditor());
            }
        });

        previousYearButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                YearMonthPickerSkin.this.control.setYearMonth(YearMonthPickerSkin.this.control.getYearMonth().minusYears(
                        1));
            }
        });
        previousMonthButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                YearMonthPickerSkin.this.control.setYearMonth(YearMonthPickerSkin.this.control.getYearMonth().minusMonths(
                        1));
            }
        });
        nextMonthButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                YearMonthPickerSkin.this.control.setYearMonth(YearMonthPickerSkin.this.control.getYearMonth().plusMonths(
                        1));
            }
        });
        nextYearButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                YearMonthPickerSkin.this.control.setYearMonth(YearMonthPickerSkin.this.control.getYearMonth().plusYears(
                        1));
            }
        });
        applyData(control.getYearMonth());
    }

    private void layout() {
        monthColumnIndex = 0;
        yearColumnIndex = 1;
        if (control.getShowPreviousYearScrollButton()) {
            pane.getChildren().add(previousYearButton);
            monthColumnIndex++;
            yearColumnIndex++;
        }
        if (control.getShowPreviousMonthScrollButton()) {
            pane.getChildren().add(previousMonthButton);
            monthColumnIndex++;
            yearColumnIndex++;
        }

        pane.getChildren().addAll(monthOfYearButton, yearButton);

        if (control.getShowNextMonthScrollButton()) {
            pane.getChildren().add(nextMonthButton);
        }
        if (control.getShowNextYearScrollButton()) {
            pane.getChildren().add(nextYearButton);
        }
    }

    @Override
    public YearMonthPicker getSkinnable() {
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
        monthOfYearButton = null;
        yearButton = null;
        if (monthOfYearEditor != null) {
            monthOfYearEditor = null;
        }
        if (yearEditor != null) {
            yearEditor = null;
        }
    }

    private void applyData(YearMonth yearMonth) {
        monthOfYearButton.setData(yearMonth.getMonthOfYear());
        yearButton.setData(Year.of(yearMonth.getYear()));
        configureScrollButtonStates();
    }

    private void configureScrollButtonStates() {
        if (control.yearMonthProperty().getMin() != null) {
            if (control.getShowPreviousYearScrollButton()) {
                if (!previousYearButton.isDisabled() && control.getYearMonth().getYear() == control.yearMonthProperty().getMin().getYear()) {
                    previousYearButton.setDisable(true);
                } else if (previousYearButton.isDisabled() && control.getYearMonth().getYear() > control.yearMonthProperty().getMin().getYear()) {
                    previousYearButton.setDisable(false);
                }
            }
            if (control.getShowPreviousMonthScrollButton()) {
                if (!previousMonthButton.isDisabled() && control.getYearMonth().equals(
                        control.yearMonthProperty().getMin())) {
                    previousMonthButton.setDisable(true);
                } else if (previousMonthButton.isDisabled() && !control.getYearMonth().equals(
                        control.yearMonthProperty().getMin())) {
                    previousMonthButton.setDisable(false);
                }
            }
        }
        if (control.yearMonthProperty().getMax() != null) {
            if (control.getShowNextMonthScrollButton()) {
                if (!nextMonthButton.isDisabled() && control.getYearMonth().equals(control.yearMonthProperty().getMax())) {
                    nextMonthButton.setDisable(true);
                } else if (nextMonthButton.isDisabled() && !control.getYearMonth().equals(
                        control.yearMonthProperty().getMax())) {
                    nextMonthButton.setDisable(false);
                }
            }
            if (control.getShowNextYearScrollButton()) {
                if (!nextYearButton.isDisabled() && control.getYearMonth().getYear() == control.yearMonthProperty().getMax().getYear()) {
                    nextYearButton.setDisable(true);
                } else if (nextYearButton.isDisabled() && control.getYearMonth().getYear() < control.yearMonthProperty().getMax().getYear()) {
                    nextYearButton.setDisable(false);
                }
            }
        }
    }

    private MonthOfYearComboBox getMonthOfYearEditor() {
        if (monthOfYearEditor == null) {
            monthOfYearEditor = new MonthOfYearComboBox();
            monthOfYearEditor.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    System.out.println("Value selected");
                    control.setYearMonth(yearButton.getData().atMonth(monthOfYearEditor.getValue()));
                    showMonthOfYearView();
                }
            });
            monthOfYearEditor.focusedProperty().addListener(new ChangeListener<Boolean>() {

                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                    if (!newVal) {
                        System.out.println("Focus lost");
                        showMonthOfYearView();
                    }
                }
            });
        }
        monthOfYearEditor.setItems(FXCollections.observableList(getMonthOfYearList()));
        monthOfYearEditor.getSelectionModel().select(monthOfYearButton.getData());
        return monthOfYearEditor;
    }

    private YearField getYearEditor() {
        if (yearEditor == null) {
            yearEditor = new YearField();
            yearEditor.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    System.out.println("Value selected");
                    control.setYearMonth(yearEditor.getYear().atMonth(monthOfYearButton.getData()));
                    showYearView();
                }
            });
            yearEditor.focusedProperty().addListener(new ChangeListener<Boolean>() {

                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                    if (!newVal) {
                        System.out.println("Focus lost");
                        showYearView();
                    }
                }
            });
        }
        yearEditor.setYear(yearButton.getData());
        return yearEditor;
    }

    private void showMonthOfYearView() {
        if (!pane.getChildren().get(monthColumnIndex).equals(monthOfYearButton)) {
            pane.getChildren().set(monthColumnIndex, monthOfYearButton);
        }
    }

    private void showYearView() {
        if (!pane.getChildren().get(yearColumnIndex).equals(yearButton)) {
            pane.getChildren().set(yearColumnIndex, yearButton);
        }
    }

    private List<MonthOfYear> getMonthOfYearList() {
        Set<MonthOfYear> monthOfYearSet = EnumSet.allOf(MonthOfYear.class);
        FiniteComparableProperty<YearMonth> yearMonth = control.yearMonthProperty();
        MonthOfYearComparator moyComparator = new MonthOfYearComparator();

        if (yearMonth.getMax()
                != null && Comparables.isEqual(yearMonth.get().getYear(), yearMonth.getMax().getYear())) {
            for (MonthOfYear moy : MonthOfYear.values()) {
                if (Comparables.isGreater(moy, yearMonth.getMax().getMonthOfYear(), moyComparator)) {
                    monthOfYearSet.remove(moy);
                }
            }
        }

        if (yearMonth.getMin()
                != null && Comparables.isEqual(yearMonth.get().getYear(), yearMonth.getMin().getYear())) {
            for (MonthOfYear moy : MonthOfYear.values()) {
                if (Comparables.isLess(moy, yearMonth.getMax().getMonthOfYear(), moyComparator)) {
                    monthOfYearSet.remove(moy);
                }
            }
        }
        List<MonthOfYear> monthOfYearList = new ArrayList<>(monthOfYearSet);

        Collections.sort(monthOfYearList, moyComparator);
        return monthOfYearList;
    }
}
