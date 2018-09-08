package org.drombler.commons.fx.scene.renderer;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import org.softsmithy.lib.text.FormatFormatter;
import org.softsmithy.lib.util.Comparables;


public class AbstractNumberRenderer<T extends Number & Comparable<T>> extends FormatterDataRenderer<T> {

    protected final T zero;

    public AbstractNumberRenderer(NumberFormat numberFormat, T zero) {
        super(new FormatFormatter<>(numberFormat));
        this.zero = zero;
    }


    /**
     * Gets all possible style classes for any item.
     *
     * @see #getStyleClass()
     *
     * @return all possible style classes for any item
     */
    @Override
    public List<String> getStyleClass() {
        List<String> styleClass = super.getStyleClass();
        styleClass.addAll(Arrays.asList("number", "negative-number"));
        return styleClass;
    }

    /**
     * Gets a ["number"] for numbers with value zero or greater and ["number",
     * "negative-number"] for numbers with values less than zero.
     *
     * @param item the item to render
     * @return a list of style classes for the specified item
     */
    @Override
    public List<String> getStyleClass(T item) {
        List<String> styleClass = super.getStyleClass(); // TODO: correct??? Not super.getStyleClass(T item) ???
        styleClass.add("number");
        if (item != null && isNegative(item)) {
            styleClass.add("negative-number");
        }
        return styleClass;
    }

    protected boolean isNegative(T number) {
        return Comparables.isLess(number, zero);
    }
}

