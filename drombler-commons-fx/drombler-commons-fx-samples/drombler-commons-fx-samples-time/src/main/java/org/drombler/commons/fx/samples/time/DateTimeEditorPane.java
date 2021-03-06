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
package org.drombler.commons.fx.samples.time;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import org.drombler.commons.fx.fxml.FXMLLoaders;
import org.drombler.commons.fx.scene.control.time.LocalDateChooser;
import org.drombler.commons.fx.scene.control.time.MonthComboBox;
import org.drombler.commons.fx.scene.control.time.YearField;

public class DateTimeEditorPane extends BorderPane {

    @FXML
    private LocalDateChooser localDateChooser1;
    @FXML
    private MonthComboBox monthComboBox;
    @FXML
    private YearField yearField;

    public DateTimeEditorPane() {
        loadFXML();
        final LocalDate now = LocalDate.of(2013, Month.APRIL, 3);
        localDateChooser1.selectedDateProperty().setMax(now.plusWeeks(3));
        localDateChooser1.selectedDateProperty().setMin(now.minusMonths(19));
        localDateChooser1.setPreviousMonths(1);
        localDateChooser1.setNextMonths(1);

        monthComboBox.getSelectionModel().select(Month.APRIL);

        yearField.setYear(Year.of(2013));
    }

    private void loadFXML() {
        FXMLLoaders.loadRoot(this);
    }
}
