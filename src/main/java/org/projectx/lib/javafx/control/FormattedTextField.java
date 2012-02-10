/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.control;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import org.projectx.lib.javafx.beans.property.FiniteComparableProperty;
import org.softsmithy.lib.text.Parser;

/**
 *
 * @author puce
 */
public class FormattedTextField<T extends Comparable<? super T>> extends TextField {

    private final FiniteComparableProperty<T> value = new FiniteComparableProperty<>(this, "value");
    private final CellRenderer<? super T> cellRenderer;
    private final Parser<? extends T> parser;
    private boolean adjusting = false;

    public FormattedTextField(CellRenderer<? super T> cellRenderer, Parser<? extends T> parser) {
        this.cellRenderer = cellRenderer;
        this.parser = parser;
        value.addListener(new ChangeListener<T>() {

            @Override
            public void changed(ObservableValue<? extends T> ov, T oldValue, T newValue) {
                adjusting = true;
                setText(FormattedTextField.this.cellRenderer.getText(newValue));
                adjusting = false;
            }
        });
        textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (!adjusting) {
                    try {
                        setValue(FormattedTextField.this.parser.parse(t1));
                    } catch (ParseException ex) {
                        Logger.getLogger(FormattedTextField.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

    }

    public final T getValue() {
        return value.get();
    }

    public final void setValue(T value) {
        this.value.set(value);
    }

    public FiniteComparableProperty<T> valueProperty() {
        return value;
    }
}
