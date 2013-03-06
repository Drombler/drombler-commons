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

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ToggleButton;
import org.drombler.commons.fx.scene.renderer.DataRenderer;
import org.drombler.commons.fx.scene.renderer.ObjectRenderer;

/**
 * A {@link ToggleButton} which can hold some data and knows how to render it.
 *
 * @author puce
 */
public class DataToggleButton<T> extends ToggleButton {

    /**
     * The data of this toggle button.
     */
    private final ObjectProperty<T> data = new SimpleObjectProperty<>(this, "data");
    /**
     * The {@link DataRenderer} to render the {@link #data}.
     */
    private final ObjectProperty<DataRenderer<? super T>> dataRenderer = new SimpleObjectProperty<>(this, "dataRenderer");

    /**
     * Creates a new instance of this class.
     *
     * It uses a {@link DataRenderer} which uses {@link Object#toString() } to
     * render the data.
     */
    public DataToggleButton() {
        this(new ObjectRenderer());
    }

    /**
     * Creates a new instance of this class.
     *
     * @param dataRenderer the {@link DataRenderer} to render the {@link #data}
     * of this button.
     */
    public DataToggleButton(DataRenderer<? super T> dataRenderer) {
        this(dataRenderer, null);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param data the data of this toggle button.
     */
    public DataToggleButton(T data) {
        this(new ObjectRenderer(), data);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param dataRenderer the {@link DataRenderer} to render the {@link #data}
     * of this button.
     * @param data the data of this button.
     */
    public DataToggleButton(DataRenderer<? super T> dataRenderer, T data) {
        this.data.addListener(new ChangeListener<T>() {
            @Override
            public void changed(ObservableValue<? extends T> ov, T oldData, T newData) {
                LabeledUtils.configure(DataToggleButton.this, getDataRenderer(), newData);
            }
        });

        this.dataRenderer.addListener(new ChangeListener<DataRenderer<? super T>>() {
            @Override
            public void changed(ObservableValue<? extends DataRenderer<? super T>> ov, DataRenderer<? super T> oldData, DataRenderer<? super T> newData) {
                LabeledUtils.configure(DataToggleButton.this, newData, getData());
            }
        });

        setData(data);
        setDataRenderer(dataRenderer);
    }

    public final T getData() {
        return data.get();
    }

    public final void setData(T data) {
        this.data.set(data);
    }

    public ObjectProperty<T> dataProperty() {
        return data;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void fire() {
        if (getToggleGroup() == null || !isSelected()) {
            super.fire();
        }
    }
}
