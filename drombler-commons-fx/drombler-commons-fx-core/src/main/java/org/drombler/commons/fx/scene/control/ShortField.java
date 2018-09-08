package org.drombler.commons.fx.scene.control;

import java.text.NumberFormat;
import org.drombler.commons.fx.scene.renderer.WholeNumberRenderer;
import org.softsmithy.lib.text.ShortParser;


/**
 * A {@link Short} field.
 *
 * @author puce
 */
public class ShortField extends FormattedTextField<Short> {

    private static final Short ZERO = 0;
    
    /**
     * Creates a new instance of this class.
     */
//    public ShortField() {
//        super(new WholeNumberRenderer<>(0), new ShortParser());
//    }

    /**
     * Creates a new instance of this class.
     *
     * @param numberFormat the {@link NumberFormat} to configure the
     * {@link #dataRendererProperty()} and the {@link #parserProperty()}
     */
    public ShortField(NumberFormat numberFormat) {
        super(new WholeNumberRenderer<Short>(numberFormat, ZERO), new ShortParser(numberFormat));
    }
}