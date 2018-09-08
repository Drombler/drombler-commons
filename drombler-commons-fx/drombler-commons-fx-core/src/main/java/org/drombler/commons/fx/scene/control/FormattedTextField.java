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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TextField;
import org.drombler.commons.fx.beans.property.LimitedComparableProperty;
import org.drombler.commons.fx.scene.renderer.DataRenderer;
import org.drombler.commons.fx.scene.renderer.FormatterDataRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softsmithy.lib.text.Formatter;
import org.softsmithy.lib.text.Parser;

/**
 * A formatted {@link TextField}. A {@link Parser} specifies how to convert the text to the {@link #value}. A
 * {@link DataRenderer} specifies how to render the {@link #value}.
 *
 * @param <T> the type to format/ parse
 * @author puce
 */
public class FormattedTextField<T extends Comparable<? super T>> extends TextField {

    private static final Logger LOG = LoggerFactory.getLogger(FormattedTextField.class);

    /**
     * The value of this text field.
     */
    private final LimitedComparableProperty<T> value = new LimitedComparableProperty<>(this, "value");
    /**
     * The {@link DataRenderer} to render the {@link #value}.
     */
    private final ObjectProperty<DataRenderer<? super T>> dataRenderer = new SimpleObjectProperty<>(this, "dataRenderer");
    /**
     * The {@link Parser} to parse the {@link #value}.
     */
    private final ObjectProperty<Parser<? extends T>> parser = new SimpleObjectProperty<>(this, "parser");
    private boolean adjusting = false;

    /**
     * Creates a new instance of this class.
     */
    public FormattedTextField() {
        this((Formatter<? super T>) null, null);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param formatter a {@link Formatter} to configure the {@link #dataRenderer}
     * @param parser a {@link Parser} to parse the {@link #value}
     */
    public FormattedTextField(Formatter<? super T> formatter, Parser<? extends T> parser) {
        this(new FormatterDataRenderer<>(formatter), parser);

    }

    /**
     * Creates a new instance of this class.
     *
     * @param dataRenderer the {@link DataRenderer} to render the {@link #value}
     * @param parser the {@link Parser} to parse the {@link #value}.
     */
    public FormattedTextField(DataRenderer<? super T> dataRenderer, Parser<? extends T> parser) {
        setDataRenderer(dataRenderer);
        setParser(parser);
        value.addListener((ov, oldValue, newValue) -> {
            adjusting = true;
            if (getDataRenderer() != null) {
                setText(getDataRenderer().getText(newValue));
            } else {
                setText(newValue != null ? newValue.toString() : "");
            }
            adjusting = false;
        });
        textProperty().addListener((ov, oldValue, newValue) -> {
            if (!adjusting && getParser() != null) {
                try {
                    setValue(getParser().parse(newValue));
                } catch (ParseException ex) {
                    LOG.error(ex.getMessage(), ex);
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
