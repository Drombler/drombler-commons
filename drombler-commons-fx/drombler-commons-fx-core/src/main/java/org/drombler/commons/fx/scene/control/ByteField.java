package org.drombler.commons.fx.scene.control;

import java.text.NumberFormat;
import org.drombler.commons.fx.scene.renderer.WholeNumberRenderer;
import org.softsmithy.lib.text.ByteParser;


/**
 * A {@link Short} field.
 *
 * @author puce
 */
public class ByteField extends FormattedTextField<Byte> {

    private static final Byte ZERO = 0;
    /**
     * Creates a new instance of this class.
     */
//    public ByteField() {
//        super(new WholeNumberRenderer<>(0), new ByteParser());
//    }

    /**
     * Creates a new instance of this class.
     *
     * @param numberFormat the {@link NumberFormat} to configure the
     * {@link #dataRendererProperty()} and the {@link #parserProperty()}
     */
    public ByteField(NumberFormat numberFormat) {
        super(new WholeNumberRenderer<>(numberFormat, ZERO), new ByteParser(numberFormat));
    }
}