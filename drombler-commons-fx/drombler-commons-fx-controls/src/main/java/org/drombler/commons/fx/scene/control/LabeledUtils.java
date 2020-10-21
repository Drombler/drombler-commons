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
 * A utility class for {@link Labeled}.
 *
 * @author puce
 */
public final class LabeledUtils {

    private LabeledUtils() {
    }

    /**
     * Configures a {@link Labeled} with some data and a corresponding {@link DataRenderer}.
     *
     * @param <T> the type of the data
     * @param labeled the Labeled instance to be configured
     * @param dataRenderer the DataRenderer to render the data
     * @param data the data
     */
    public static <T> void configure(Labeled labeled, DataRenderer<? super T> dataRenderer, T data) {
        labeled.setText(dataRenderer.getText(data));
        labeled.setGraphic(dataRenderer.getGraphic(data));
        labeled.setTooltip(dataRenderer.getTooltip(data));
//        labeled.setTextAlignment(cellRenderer.getTextAlignment());
//        labeled.setAlignment(Pos.BASELINE_RIGHT);
        labeled.getStyleClass().removeAll(dataRenderer.getStyleClass());
        labeled.getStyleClass().addAll(dataRenderer.getStyleClass(data));
    }

    /**
     * Unonfigures a {@link Labeled} with a corresponding {@link DataRenderer}.
     *
     * @param <T> the type of the data
     * @param labeled the Labeled instance to be unconfigured
     * @param dataRenderer the DataRenderer to render the data
     */
    public static <T> void unconfigure(Labeled labeled, DataRenderer<? super T> dataRenderer) {
        labeled.setText(null);
        labeled.setGraphic(null);
        labeled.setTooltip(null);
        labeled.getStyleClass().removeAll(dataRenderer.getStyleClass());
    }
}
