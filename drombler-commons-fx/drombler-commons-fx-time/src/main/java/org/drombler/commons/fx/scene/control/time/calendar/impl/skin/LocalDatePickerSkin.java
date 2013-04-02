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

import java.text.ParseException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Skin;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.WindowEvent;
import javax.time.calendar.LocalDate;
import org.drombler.commons.fx.scene.Nodes;
import org.drombler.commons.fx.scene.control.FormattedTextField;
import org.drombler.commons.fx.scene.control.time.calendar.LocalDatePicker;
import org.drombler.commons.fx.scene.control.time.calendar.LocalDateChooser;
import org.drombler.commons.fx.scene.renderer.FormatterDataRenderer;
import org.drombler.commons.time.calendar.format.CalendricalFormatter;
import org.softsmithy.lib.text.Parser;

/**
 *
 * @author puce
 */
public class LocalDatePickerSkin implements Skin<LocalDatePicker> {

    /**
     * The {@code Control} that is referencing this Skin. There is a one-to-one
     * relationship between a {@code Skin} and a {@code Control}. When a
     * {@code Skin} is set on a {@code Control}, this variable is automatically
     * updated.
     */
    private LocalDatePicker control;
    /**
     * This control is used to represent the YearMonthPicker.
     */
    private HBox pane = new HBox();
    private FormattedTextField<LocalDate> dateField = new FormattedTextField<>(new FormatterDataRenderer<>(
            new CalendricalFormatter()),
            new Parser<LocalDate>() {
                @Override
                public LocalDate parse(CharSequence text) throws ParseException {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            });
    private LocalDateChooser dateChooser = new LocalDateChooser();
    private Button dateButton = new Button("...");
//    private PopupControl popupControl = new PopupControl() {
//        {
//            setSkin(new Skin<PopupControl>() {
//                private PopupControl skinnable = popupControl;
//                private Node node = datePicker;
//                @Override
//                public PopupControl getSkinnable() {
//                    return skinnable;//LocalDateFieldSkin.this.getSkinnable();
//                }
//
//                @Override
//                public Node getNode() {
//                    return node;
//                }
//
//                @Override
//                public void dispose() {
//                    skinnable = null;
//                    node = null;
//                }
//            });
//
//            getStyleClass().add("local-date-field-popup");
//        }
//    };
    private Popup popupControl = new Popup();
    private boolean showing = false;

    public LocalDatePickerSkin(LocalDatePicker control) {
        this.control = control;
//        this.control.cellRendererProperty().addListener(new ChangeListener<CellRenderer<? super LocalDate>>() {
//
//            @Override
//            public void changed(ObservableValue<? extends CellRenderer<? super LocalDate>> ov,
//                    CellRenderer<? super LocalDate> oldValue, CellRenderer<? super LocalDate> newValue) {
//                dateField.setCellRenderer(newValue);
//            }
//        });
        pane.getChildren().addAll(dateField, dateButton);


        //popupControl.getContent().add(datePicker);
        popupControl.setAutoHide(true);
        popupControl.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                showing = false;
            }
        });
        popupControl.setAutoFix(true);
        popupControl.getContent().add(dateChooser);
//        datePicker.getStyleClass().add("date-picker-popup");
        
        // TODO: provide this fill via CSS
        popupControl.getScene().setFill(Color.WHITE);// setOpacity(1.0);
//        datePicker.setOpacity(1.0);
//        datePicker.setStyle("-fx-background-color: slateblue;");

//        popupControl.getStyleClass().add("local-date-field-popup");



        dateChooser.nextMonthsProperty().bind(this.control.nextMonthsProperty());
        dateChooser.nextWeeksProperty().bind(this.control.nextWeeksProperty());
        dateChooser.previousMonthsProperty().bind(this.control.previousMonthsProperty());
        dateChooser.previousWeeksProperty().bind(this.control.previousWeeksProperty());
        dateChooser.showingMonthScrollButtonProperty().bind(this.control.showingMonthScrollButtonProperty());
        dateChooser.showingWeekOfYearProperty().bind(this.control.showingWeekOfYearProperty());
        dateChooser.showingYearScrollButtonProperty().bind(this.control.showingYearScrollButtonProperty());
        dateChooser.selectedDateProperty().maxProperty().bind(this.control.selectedDateProperty().maxProperty());
        dateChooser.selectedDateProperty().minProperty().bind(this.control.selectedDateProperty().minProperty());
        dateChooser.selectedDateProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> ov, LocalDate oldVal, LocalDate newVal) {
                dateField.setValue(newVal);
                LocalDatePickerSkin.this.control.setSelectedDate(newVal);

                popupControl.hide();
            }
        });

        dateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (!showing) {
                    showing = true;
                    dateChooser.setYearMonth(LocalDatePickerSkin.this.control.getYearMonth());
                    Point2D screenLocation = Nodes.getScreenLocation(dateField);
                    popupControl.show(LocalDatePickerSkin.this.control.getScene().getWindow(),
                            screenLocation.getX() + dateField.getLayoutX(),
                            screenLocation.getY() + dateField.getHeight());
                }
            }
        });

        dateField.dataRendererProperty().bind(this.control.dataRendererProperty());
        dateField.parserProperty().bind(this.control.parserProperty());
        dateField.setEditable(false);
        dateField.setValue(control.getSelectedDate());
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
        dateButton = null;
        dateField = null;
        dateChooser = null;
        popupControl = null;
    }
}
