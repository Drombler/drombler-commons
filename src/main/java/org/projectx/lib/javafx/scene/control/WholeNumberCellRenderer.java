/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.scene.control;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import javafx.scene.text.TextAlignment;
import org.softsmithy.lib.math.BigIntegers;
import org.softsmithy.lib.util.Comparables;

/**
 *
 * @author puce
 */
public class WholeNumberCellRenderer<T extends Number & Comparable<T>> extends AbstractDataRenderer<T> {

    private final NumberFormat numberFormat;
    private final T zero;

    public WholeNumberCellRenderer(T zero) {
        this(NumberFormat.getIntegerInstance(), zero);
    }

    public WholeNumberCellRenderer(NumberFormat numberFormat, T zero) {
        this.numberFormat = numberFormat;
        this.zero = zero;
    }

    @Override
    public String getText(Number item) {
        if (item != null) {
            return numberFormat.format(item);
        } else {
            return null;
        }
    }

    @Override
    public List<String> getStyleClass() {
        List<String> styleClass = super.getStyleClass();
        styleClass.addAll(Arrays.asList("number", "negative-number"));
        return styleClass;
    }

    @Override
    public List<String> getStyleClass(T item) {
        List<String> styleClass = super.getStyleClass();
        styleClass.add("number");
        
        if (item != null && isNegative(item)){
            styleClass.add("negative-number");
        }
        return styleClass;
    }
    
    private boolean isNegative(T number){
        return Comparables.isLess(number, zero);
    }
}
