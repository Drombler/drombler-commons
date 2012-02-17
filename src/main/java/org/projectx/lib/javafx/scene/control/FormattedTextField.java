/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.scene.control;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import org.projectx.lib.javafx.beans.property.FiniteComparableProperty;
import org.projectx.lib.javafx.scene.control.time.calendar.LocalDateCellRenderer;
import org.softsmithy.lib.text.Parser;

/**
 *
 * @author puce
 */
public class FormattedTextField<T extends Comparable<? super T>> extends TextField {

    private final FiniteComparableProperty<T> value = new FiniteComparableProperty<>(this, "value");
    private final ObjectProperty<CellRenderer<? super T>> cellRenderer = new SimpleObjectProperty<>(this, "cellRenderer");
    private final ObjectProperty<Parser<? extends T>> parser = new SimpleObjectProperty<>(this, "parser");
    private boolean adjusting = false;

    public FormattedTextField() {
        this(null, null);
    }

    public FormattedTextField(CellRenderer<? super T> cellRenderer, Parser<? extends T> parser) {
        setCellRenderer(cellRenderer);
        setParser(parser);
        value.addListener(new ChangeListener<T>() {

            @Override
            public void changed(ObservableValue<? extends T> ov, T oldValue, T newValue) {
                adjusting = true;
                if (getCellRenderer() != null) {
                    setText(getCellRenderer().getText(newValue));
                } else {
                    setText(newValue != null ? newValue.toString() : "");
                }
                adjusting = false;
            }
        });
        textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
                if (!adjusting && getParser() != null) {
                    try {
                        setValue(getParser().parse(newValue));
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

    public final CellRenderer<? super T> getCellRenderer() {
        return cellRenderer.get();
    }

    public final void setCellRenderer(CellRenderer<? super T> cellRenderer) {
        this.cellRenderer.set(cellRenderer);
    }

    public ObjectProperty<CellRenderer<? super T>> cellRendererProperty() {
        return cellRenderer;
    }

    public final Parser<? extends T> getParser() {
        return parser.get();
    }

    public final void setParser(Parser<? extends T> parser) {
        this.parser.set(parser);
    }

    public ObjectProperty<Parser<? extends T>> parserProperty() {
        return parser;
    }
}
