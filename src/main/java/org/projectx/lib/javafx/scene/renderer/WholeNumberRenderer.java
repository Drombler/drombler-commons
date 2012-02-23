/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.scene.renderer;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import org.softsmithy.lib.text.FormatFormatter;
import org.softsmithy.lib.util.Comparables;

/**
 *
 * @author puce
 */
public class WholeNumberRenderer<T extends Number & Comparable<T>> extends FormatterDataRenderer<T> {

    private final T zero;

    public WholeNumberRenderer(T zero) {
        this(NumberFormat.getIntegerInstance(), zero);
    }

    public WholeNumberRenderer(NumberFormat numberFormat, T zero) {
        super(new FormatFormatter<>(numberFormat));
        this.zero = zero;
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

        if (item != null && isNegative(item)) {
            styleClass.add("negative-number");
        }
        return styleClass;
    }

    private boolean isNegative(T number) {
        return Comparables.isLess(number, zero);
    }
}
