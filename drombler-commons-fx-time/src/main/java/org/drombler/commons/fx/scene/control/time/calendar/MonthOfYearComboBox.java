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

import java.util.Arrays;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javax.time.calendar.MonthOfYear;
import org.drombler.commons.fx.scene.control.RenderedListCellFactory;
import org.drombler.commons.fx.scene.renderer.time.calendar.MonthOfYearRenderer;

/**
 * A {@link ComboBox} for {@link MonthOfYear}.
 *
 * @author puce
 */
public class MonthOfYearComboBox extends ComboBox<MonthOfYear> {

    /**
     * Creates a new instance of this class.
     */
    public MonthOfYearComboBox() {
        super(getMonthOfYearList());
//        System.out.println(Locale.getDefault());
        
//        setConverter(new StringConverter<MonthOfYear>() {
//
//            @Override
//            public String toString(MonthOfYear moy) {
//                return moy.getText(Locale.getDefault());
//            }
//
//            @Override
//            public MonthOfYear fromString(String string) {
//                return MonthOfYear.valueOf(string);
//            }
//        });
        setCellFactory(new RenderedListCellFactory<>(new MonthOfYearRenderer()));
    }

    private static ObservableList<MonthOfYear> getMonthOfYearList() {
//        List<MonthOfYear> monthOfYearList = new ArrayList<>(EnumSet.allOf(MonthOfYear.class));
//        Collections.sort(monthOfYearList, new MonthOfYearComparator());
//        return FXCollections.observableList(monthOfYearList);

        return FXCollections.observableList(Arrays.asList(MonthOfYear.values()));
    }
}
