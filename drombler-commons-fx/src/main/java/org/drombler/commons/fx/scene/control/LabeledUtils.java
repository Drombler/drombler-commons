/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
