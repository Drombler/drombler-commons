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

import javafx.scene.control.Labeled;
import org.drombler.commons.fx.scene.renderer.DataRenderer;

/**
 *
 * @author puce
 */
public class LabeledUtils {

    private LabeledUtils() {
    }

    public static <T> void configure(Labeled labeled, DataRenderer<? super T> dataRenderer, T data) {
        labeled.setText(dataRenderer.getText(data));
        labeled.setGraphic(dataRenderer.getGraphic(data));
//        labeled.setTextAlignment(cellRenderer.getTextAlignment());
//        labeled.setAlignment(Pos.BASELINE_RIGHT);
        labeled.getStyleClass().removeAll(dataRenderer.getStyleClass());
        labeled.getStyleClass().addAll(dataRenderer.getStyleClass(data));
    }
}
