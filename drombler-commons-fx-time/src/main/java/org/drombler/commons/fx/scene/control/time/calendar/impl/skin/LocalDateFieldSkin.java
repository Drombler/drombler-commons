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
import javafx.scene.control.PopupControl;
import javafx.scene.control.Skin;
import javafx.scene.control.Skinnable;
import javafx.scene.layout.HBox;
import javafx.stage.WindowEvent;
import javax.time.calendar.LocalDate;
import org.drombler.commons.fx.scene.Nodes;
import org.drombler.commons.fx.scene.control.FormattedTextField;
import org.drombler.commons.fx.scene.control.time.calendar.LocalDateField;
import org.drombler.commons.fx.scene.control.time.calendar.LocalDatePicker;
import org.drombler.commons.fx.scene.renderer.FormatterDataRenderer;
import org.drombler.commons.time.calendar.format.CalendricalFormatter;
import org.softsmithy.lib.text.Parser;

/**
 *
 * @author puce
 */
public class LocalDateFieldSkin implements Skin<LocalDateField> {

    /**
     * The {@code Control} that is referencing this Skin. There is a one-to-one relationship between a {@code Skin} and
     * a {@code Control}. When a {@code Skin} is set on a {@code Control}, this variable is automatically updated.
     */
    private LocalDateField control;
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
    private LocalDatePicker datePicker = new LocalDatePicker();
    private Button dateButton = new Button("...");
    private PopupControl popupControl = new PopupControl() {

        {
            setSkin(new Skin() {

                @Override
                public Skinnable getSkinnable() {
                    return LocalDateFieldSkin.this.getSkinnable();
                }

                @Override
                public Node getNode() {
                    return datePicker;
                }

                @Override
                public void dispose() {
                }
            });
        }
    };
    private boolean showing = false;

    public LocalDateFieldSkin(LocalDateField control) {
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



        datePicker.nextMonthsProperty().bind(this.control.nextMonthsProperty());
        datePicker.nextWeeksProperty().bind(this.control.nextWeeksProperty());
        datePicker.previousMonthsProperty().bind(this.control.previousMonthsProperty());
        datePicker.previousWeeksProperty().bind(this.control.previousWeeksProperty());
        datePicker.showMonthScrollButtonProperty().bind(this.control.showMonthScrollButtonProperty());
        datePicker.showWeeksProperty().bind(this.control.showWeeksProperty());
        datePicker.showYearScrollButtonProperty().bind(this.control.showYearScrollButtonProperty());
        datePicker.selectedDateProperty().maxProperty().bind(this.control.selectedDateProperty().maxProperty());
        datePicker.selectedDateProperty().minProperty().bind(this.control.selectedDateProperty().minProperty());
        datePicker.selectedDateProperty().addListener(new ChangeListener<LocalDate>() {

            @Override
            public void changed(ObservableValue<? extends LocalDate> ov, LocalDate oldVal, LocalDate newVal) {
                dateField.setValue(newVal);
                LocalDateFieldSkin.this.control.setSelectedDate(newVal);

                popupControl.hide();
            }
        });

        dateButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                if (!showing) {
                    showing = true;
                    datePicker.setYearMonth(LocalDateFieldSkin.this.control.getYearMonth());
                    Point2D screenLocation = Nodes.getScreenLocation(dateButton);
                    popupControl.show(LocalDateFieldSkin.this.control.getScene().getWindow(),
                            screenLocation.getX() + dateButton.getWidth() - popupControl.getWidth(),
                            screenLocation.getY() + dateButton.getHeight());
                }
            }
        });

        dateField.dataRendererProperty().bind(this.control.dataRendererProperty());
        dateField.parserProperty().bind(this.control.parserProperty());
        dateField.setEditable(false);
        dateField.setValue(control.getSelectedDate());
    }

    @Override
    public LocalDateField getSkinnable() {
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
        datePicker = null;
        popupControl = null;
    }
}
