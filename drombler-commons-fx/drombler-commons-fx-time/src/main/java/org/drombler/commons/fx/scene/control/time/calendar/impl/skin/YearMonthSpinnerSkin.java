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
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Skin;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javax.time.calendar.MonthOfYear;
import javax.time.calendar.Year;
import javax.time.calendar.YearMonth;
import org.drombler.commons.fx.beans.property.LimitedComparableProperty;
import org.drombler.commons.fx.scene.control.DataButton;
import org.drombler.commons.fx.scene.control.time.calendar.MonthOfYearComboBox;
import org.drombler.commons.fx.scene.control.time.calendar.YearField;
import org.drombler.commons.fx.scene.control.time.calendar.YearMonthSpinner;
import org.drombler.commons.fx.scene.renderer.time.calendar.MonthOfYearRenderer;
import org.drombler.commons.fx.scene.renderer.time.calendar.YearRenderer;
import org.drombler.commons.time.calendar.YearUtils;
import org.softsmithy.lib.util.Comparables;

/**
 *
 * @author puce
 */
public class YearMonthSpinnerSkin implements Skin<YearMonthSpinner> {

    /**
     * The {@code Control} that is referencing this Skin. There is a one-to-one
     * relationship between a {@code Skin} and a {@code Control}. When a
     * {@code Skin} is set on a {@code Control}, this variable is automatically
     * updated.
     */
    private YearMonthSpinner control;
    /**
     * This control is used to represent the YearMonthSpinner.
     */
    private GridPane pane = new GridPane();
    private DataButton<MonthOfYear> monthOfYearButton = new DataButton<>(new MonthOfYearRenderer());
    private DataButton<Year> yearButton = new DataButton<>(new YearRenderer());
    private Button previousMonthButton = new Button("<");
    private Button nextMonthButton = new Button(">");
    private Button previousYearButton = new Button("<<");
    private Button nextYearButton = new Button(">>");
    private MonthOfYearComboBox monthOfYearEditor;
    private YearField yearEditor;
    private int monthColumnIndex = 0;
    private int yearColumnIndex = 1;

    public YearMonthSpinnerSkin(final YearMonthSpinner control) {
        this.control = control;
        //pane.setStyle("-fx-background-color: blue, red; -fx-background-insets: 2, 5;");
        //pane.setGridLinesVisible(true);
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
                YearMonthSpinnerSkin.this.control.setYearMonth(YearMonthSpinnerSkin.this.control.getYearMonth().minusYears(
                        1));
            }
        });
        previousYearButton.prefWidthProperty().bind(previousYearButton.minWidthProperty());
        previousMonthButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                YearMonthSpinnerSkin.this.control.setYearMonth(YearMonthSpinnerSkin.this.control.getYearMonth().minusMonths(
                        1));
            }
        });
        nextMonthButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                YearMonthSpinnerSkin.this.control.setYearMonth(YearMonthSpinnerSkin.this.control.getYearMonth().plusMonths(
                        1));
            }
        });
        nextYearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                YearMonthSpinnerSkin.this.control.setYearMonth(YearMonthSpinnerSkin.this.control.getYearMonth().plusYears(
                        1));
            }
        });
        applyData(control.getYearMonth());
    }

    private void layout() {
        pane.setHgap(5d);
        int columnIndex = 0;
        if (control.isAlwaysReservingYearScrollButtonSpace() || control.isShowingPreviousYearScrollButton() || control.isShowingNextYearScrollButton()) {
            ColumnConstraints cc = new ColumnConstraints();
            pane.getColumnConstraints().add(cc);
            pane.add(previousYearButton, columnIndex++, 0);
            previousYearButton.setVisible(control.isShowingPreviousYearScrollButton());
        }
        if (control.isAlwaysReservingMonthScrollButtonSpace() || control.isShowingPreviousMonthScrollButton() || control.isShowingNextMonthScrollButton()) {
            ColumnConstraints cc = new ColumnConstraints();
            pane.getColumnConstraints().add(cc);
            pane.add(previousMonthButton, columnIndex++, 0);
            previousMonthButton.setVisible(control.isShowingPreviousMonthScrollButton());
        }

//        HBox.setHgrow(monthOfYearButton, Priority.ALWAYS);
//        HBox.setHgrow(yearButton, Priority.ALWAYS);
//        pane.setAlignment(Pos.TOP_CENTER);
//        monthOfYearButton.setMaxWidth(Double.MAX_VALUE);
//        yearButton.setMaxWidth(Double.MAX_VALUE);

//        pane.getChildren().addAll(monthOfYearButton, yearButton);
        ColumnConstraints ccMonthOfYear = new ColumnConstraints();
        ccMonthOfYear.setHgrow(Priority.ALWAYS);
        ccMonthOfYear.setHalignment(HPos.RIGHT);
        pane.getColumnConstraints().add(ccMonthOfYear);
        monthColumnIndex = columnIndex;
        pane.add(monthOfYearButton, columnIndex++, 0);

        ColumnConstraints ccYear = new ColumnConstraints();
        ccYear.setHgrow(Priority.ALWAYS);
        pane.getColumnConstraints().add(ccYear);
        yearColumnIndex = columnIndex;
        pane.add(yearButton, columnIndex++, 0);

        if (control.isAlwaysReservingMonthScrollButtonSpace() || control.isShowingPreviousMonthScrollButton() || control.isShowingNextMonthScrollButton()) {
            ColumnConstraints cc = new ColumnConstraints();
            pane.getColumnConstraints().add(cc);
            pane.add(nextMonthButton, columnIndex++, 0);
            nextMonthButton.setVisible(control.isShowingNextMonthScrollButton());
        }
        if (control.isAlwaysReservingYearScrollButtonSpace() || control.isShowingPreviousYearScrollButton() || control.isShowingNextYearScrollButton()) {
            ColumnConstraints cc = new ColumnConstraints();
            pane.getColumnConstraints().add(cc);
            pane.add(nextYearButton, columnIndex++, 0);
            nextYearButton.setVisible(control.isShowingNextYearScrollButton());
        }
        //pane.setGridLinesVisible(true);
    }

    @Override
    public YearMonthSpinner getSkinnable() {
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
            if (control.isShowingPreviousYearScrollButton()) {
                if (!previousYearButton.isDisabled() && control.getYearMonth().getYear() == control.yearMonthProperty().getMin().getYear()) {
                    previousYearButton.setDisable(true);
                } else if (previousYearButton.isDisabled() && control.getYearMonth().getYear() > control.yearMonthProperty().getMin().getYear()) {
                    previousYearButton.setDisable(false);
                }
            }
            if (control.isShowingPreviousMonthScrollButton()) {
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
            if (control.isShowingNextMonthScrollButton()) {
                if (!nextMonthButton.isDisabled() && control.getYearMonth().equals(control.yearMonthProperty().getMax())) {
                    nextMonthButton.setDisable(true);
                } else if (nextMonthButton.isDisabled() && !control.getYearMonth().equals(
                        control.yearMonthProperty().getMax())) {
                    nextMonthButton.setDisable(false);
                }
            }
            if (control.isShowingNextYearScrollButton()) {
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
            GridPane.setConstraints(monthOfYearEditor, monthColumnIndex, 0);
            monthOfYearEditor.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
//                    System.out.println("Value selected");
                    control.setYearMonth(yearButton.getData().atMonth(monthOfYearEditor.getValue()));
                    showMonthOfYearView();
                }
            });
            monthOfYearEditor.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                    if (!newVal) {
//                        System.out.println("Focus lost");
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
            GridPane.setConstraints(yearEditor, yearColumnIndex, 0);
            yearEditor.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
//                    System.out.println("Value selected");
                    control.setYearMonth(yearEditor.getYear().atMonth(monthOfYearButton.getData()));
                    showYearView();
                }
            });
            yearEditor.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                    if (!newVal) {
//                        System.out.println("Focus lost");
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
        LimitedComparableProperty<YearMonth> yearMonth = control.yearMonthProperty();
        return YearUtils.getMonthOfYearList(Year.of(yearMonth.get().getYear()), yearMonth.getMin(), yearMonth.getMax());
    }
}
