/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
