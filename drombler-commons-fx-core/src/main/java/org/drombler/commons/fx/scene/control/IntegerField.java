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
import org.drombler.commons.fx.scene.renderer.WholeNumberRenderer;
import org.softsmithy.lib.text.IntegerParser;

/**
 *
 * @author puce
 */
public class IntegerField extends FormattedTextField<Integer> {

    public IntegerField() {
        super(new WholeNumberRenderer<>(0), new IntegerParser());
    }

    public IntegerField(NumberFormat numberFormat) {
        super(new WholeNumberRenderer<>(numberFormat, 0), new IntegerParser(numberFormat));
    }
}
