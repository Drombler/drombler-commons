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
package org.drombler.commons.fx.scene.renderer;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import org.softsmithy.lib.text.FormatFormatter;
import org.softsmithy.lib.util.Comparables;

/**
 * A {@link DataRenderer} for whole numbers. This renderer provides no graphical
 * representations by default.
 *
 * @param <T> the number type of the data to render
 * @author puce
 */
public class WholeNumberRenderer<T extends Number & Comparable<T>> extends FormatterDataRenderer<T> {

    private final T zero;

    /**
     * Creates a new instance of this class.
     *
     * @param zero the zero representation of the used number type
     */
    public WholeNumberRenderer(T zero) {
        this(NumberFormat.getIntegerInstance(), zero);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param numberFormat A {@link NumberFormat} used to format the numbers
     * @param zero the zero representation of the used number type
     */
    public WholeNumberRenderer(NumberFormat numberFormat, T zero) {
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

    private boolean isNegative(T number) {
        return Comparables.isLess(number, zero);
    }
}
