package org.drombler.commons.fx.scene.control;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.PercentageStringConverter;
import org.drombler.commons.fx.util.converter.XCurrencyStringConvert;
import org.drombler.commons.fx.util.converter.XNumberStringConverter;

/**
 *
 * @author puce
 */
public final class TextFormatterUtils {

    private TextFormatterUtils() {
    }

    public static TextFormatter<Number> createNumberFormatter(XNumberStringConverter converter, Number defaulValue) {
        return new TextFormatter<>(converter, defaulValue, createFilter(converter.getNumberFormat()));
    }

    public static TextFormatter<Number> createNumberFormatter(XCurrencyStringConvert converter, Number defaulValue) {
        return new TextFormatter<>(converter, defaulValue, createFilter(converter.getNumberFormat()));
    }

    public static TextFormatter<Number> createNumberFormatter(PercentageStringConverter converter, Number defaulValue) {
        return new TextFormatter<>(converter, defaulValue, createFilter(converter.getNumberFormat()));
    }

    // https://stackoverflow.com/questions/8381374/how-to-implement-a-numberfield-in-javafx-2-0
    private static UnaryOperator<TextFormatter.Change> createFilter(NumberFormat numberFormat) {
        return change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty()) {
                return change;
            }

            ParsePosition parsePosition = new ParsePosition(0);
            Object object = numberFormat.parse(newText, parsePosition);
            if (object == null || parsePosition.getIndex() < newText.length()) {
                return null;
            } else {
                return change;
            }
        };
    }

    // from SoftSmithy AbstractXNumberFormatter:
       /**
     * Returns the <code>Number</code> representation of the <code>String</code> <code>text</code>.
     *
     * @param text <code>String</code> to convert
     * @return <code>Number</code> representation of text
     * @throws ParseException if there is an error in the conversion
     */
//    @Override
//    public Object stringToValue(String text) throws ParseException {
//        Number value = null;
//        try {
//            String valueText = text;
//            if (((DecimalFormat) getNumberFormat()).isGroupingUsed()) {
//                valueText = valueText.replaceAll("\\" + ((DecimalFormat) getNumberFormat()).getDecimalFormatSymbols().getGroupingSeparator(), "");
//            }
//            valueText = valueText.replaceAll("\\" + ((DecimalFormat) getNumberFormat()).getDecimalFormatSymbols().getDecimalSeparator(), ".");
//            value = valueToRange(stringToNumber(valueText));
//            //System.out.println("First number conversion worked!");
//        } catch (RuntimeException ex) { //try this, but only long and double precision supported (important for BigInteger and BigDecimal)!
//            //System.out.println("First number conversion failed!");
//            try {
//                value = valueToRange(stringToNumber(getNumberFormat() != null ? getNumberFormat().parse(text).toString() : text));
//                value = (Number) super.stringToValue(getNumberFormat() != null ? getNumberFormat().format(value.toString()) : value.toString()); // needed?
//                //System.out.println("Second number conversion worked!");
//            } catch (ParseException | RuntimeException nfe) {
//                //System.out.println("Second number conversion failed!");
//                //nfe.printStackTrace();
//                value = (Number) super.stringToValue(text); // will throw a ParseException
//                //System.out.println("Third number conversion worked!");
//            }
//        }
//        return value;
//    }
}
