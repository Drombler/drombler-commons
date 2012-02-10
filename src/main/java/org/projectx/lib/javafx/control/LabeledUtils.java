/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.control;

import javafx.geometry.Pos;
import javafx.scene.control.Labeled;

/**
 *
 * @author puce
 */
public class LabeledUtils {

    public static <T> void configure(Labeled labeled, CellRenderer<? super T> cellRenderer, T data) {
        labeled.setText(cellRenderer.getText(data));
        labeled.setGraphic(cellRenderer.getGraphic(data));
        labeled.setTextAlignment(cellRenderer.getTextAlignment());
//        labeled.setAlignment(Pos.BASELINE_RIGHT);
    }
}
