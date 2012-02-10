/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.scene.control;

import java.text.NumberFormat;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author puce
 */
public class WholeNumberCellRenderer extends AbstractCellRenderer<Number> {

    private final NumberFormat numberFormat;

    public WholeNumberCellRenderer() {
        this(NumberFormat.getIntegerInstance());
    }

    public WholeNumberCellRenderer(NumberFormat numberFormat) {
        super(TextAlignment.RIGHT); // TODO: TRAILING? Does not exist...
        this.numberFormat = numberFormat;
    }

    @Override
    public String getText(Number item) {
        if (item != null) {
            return numberFormat.format(item);
        } else {
            return null;
        }
    }
}
