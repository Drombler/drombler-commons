/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.time.calendar;

import java.util.Comparator;
import javax.time.calendar.MonthOfYear;

/**
 *
 * @author puce
 */
public class MonthOfYearComparator implements Comparator<MonthOfYear>{

    @Override
    public int compare(MonthOfYear o1, MonthOfYear o2) {
        return o1.getValue() - o2.getValue();
    }
    
}
