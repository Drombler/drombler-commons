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
package org.drombler.commons.fx.scene.control;

import java.text.NumberFormat;
import org.drombler.commons.fx.scene.renderer.RealNumberRenderer;
import org.softsmithy.lib.text.DoubleParser;

/**
 * An Integer field.
 *
 * @author puce
 */
public class DoubleField extends FormattedTextField<Double> {

    /**
     * Creates a new instance of this class.
     */
    public DoubleField() {
        super(new RealNumberRenderer<>(0d), new DoubleParser());
    }

    /**
     * Creates a new instance of this class.
     *
     * @param numberFormat the {@link NumberFormat} to configure the
     * {@link #dataRendererProperty()} and the {@link #parserProperty()}
     */
    public DoubleField(NumberFormat numberFormat) {
        super(new RealNumberRenderer<>(numberFormat, 0d), new DoubleParser(numberFormat));
    }
}
