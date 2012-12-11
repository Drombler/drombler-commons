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
