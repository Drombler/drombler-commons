/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.control.time.calendar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javax.time.calendar.MonthOfYear;
import org.projectx.lib.javafx.control.RenderedListCellFactory;
import org.projectx.lib.time.calendar.MonthOfYearComparator;

/**
 *
 * @author puce
 */
public class MonthOfYearComboBox extends ComboBox<MonthOfYear> {

    public MonthOfYearComboBox() {
        super(getMonthOfYearList());
        System.out.println(Locale.getDefault());
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
        setCellFactory(new RenderedListCellFactory<>(new MonthOfYearCellRenderer()));
    }
    
    private static ObservableList<MonthOfYear> getMonthOfYearList(){
        List<MonthOfYear> monthOfYearList = new ArrayList<>(EnumSet.allOf(MonthOfYear.class));
        Collections.sort(monthOfYearList, new MonthOfYearComparator());
        return FXCollections.observableList(monthOfYearList);
    }
}
