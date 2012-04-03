/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.scene.control;

import org.projectx.lib.javafx.scene.renderer.WholeNumberRenderer;
import java.text.NumberFormat;
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
