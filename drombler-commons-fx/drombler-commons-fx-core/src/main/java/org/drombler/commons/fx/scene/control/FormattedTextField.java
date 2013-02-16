/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (Sourceforge.net user: puce).
 * Copyright 2012 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.fx.scene.control;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import org.drombler.commons.fx.beans.property.LimitedComparableProperty;
import org.drombler.commons.fx.scene.renderer.DataRenderer;
import org.drombler.commons.fx.scene.renderer.FormatterDataRenderer;
import org.softsmithy.lib.text.Formatter;
import org.softsmithy.lib.text.Parser;

/**
 *
 * @author puce
 */
public class FormattedTextField<T extends Comparable<? super T>> extends TextField {

    private final LimitedComparableProperty<T> value = new LimitedComparableProperty<>(this, "value");
    private final ObjectProperty<DataRenderer<? super T>> dataRenderer = new SimpleObjectProperty<>(this, "dataRenderer");
    private final ObjectProperty<Parser<? extends T>> parser = new SimpleObjectProperty<>(this, "parser");
    private boolean adjusting = false;

    public FormattedTextField() {
        this((Formatter<? super T>) null, null);
    }

    public FormattedTextField(Formatter<? super T> formatter, Parser<? extends T> parser) {
        this(new FormatterDataRenderer<>(formatter), parser);
        
    }
    public FormattedTextField(DataRenderer<? super T> dataRenderer, Parser<? extends T> parser) {
        setDataRenderer(dataRenderer);
        setParser(parser);
        value.addListener(new ChangeListener<T>() {

            @Override
            public void changed(ObservableValue<? extends T> ov, T oldValue, T newValue) {
                adjusting = true;
                if (getDataRenderer() != null) {
                    setText(getDataRenderer().getText(newValue));
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

    public LimitedComparableProperty<T> valueProperty() {
        return value;
    }

    public final DataRenderer<? super T> getDataRenderer() {
        return dataRenderer.get();
    }

    public final void setDataRenderer(DataRenderer<? super T> dataRenderer) {
        this.dataRenderer.set(dataRenderer);
    }

    public ObjectProperty<DataRenderer<? super T>> dataRendererProperty() {
        return dataRenderer;
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
