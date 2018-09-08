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

/**
 * A {@link DataRenderer} for real numbers. This renderer provides no graphical
 * representations by default.
 *
 * @param <T> the number type of the data to render
 * @author puce
 */
public class RealNumberRenderer<T extends Number & Comparable<T>> extends AbstractNumberRenderer<T> {


    /**
     * Creates a new instance of this class.
     *
     * @param zero the zero representation of the used number type
     */
    public RealNumberRenderer(T zero) {
        this(NumberFormat.getInstance(), zero);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param numberFormat A {@link NumberFormat} used to format the numbers
     * @param zero the zero representation of the used number type
     */
    public RealNumberRenderer(NumberFormat numberFormat, T zero) {
        super(numberFormat, zero);
    }

}
