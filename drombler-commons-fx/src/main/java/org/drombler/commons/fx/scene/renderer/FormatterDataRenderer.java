/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.commons.fx.scene.renderer;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.softsmithy.lib.text.FormatException;
import org.softsmithy.lib.text.Formatter;

/**
 *
 * @author puce
 */
public class FormatterDataRenderer<T> extends AbstractDataRenderer<T> {

    private final Formatter<? super T> formatter;

    public FormatterDataRenderer(Formatter<? super T> formatter) {
        this.formatter = formatter;
    }

    @Override
    public String getText(T item) {
        if (item != null) {
            try {
                return formatter.format(item);
            } catch (FormatException ex) {
                // TODO: good enough just to log?
                Logger.getLogger(FormatterDataRenderer.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } else {
            return null;
        }

    }
}
