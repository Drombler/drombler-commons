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
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import org.drombler.commons.fx.scene.renderer.DataRenderer;
import org.drombler.commons.fx.scene.renderer.ObjectRenderer;

/**
 * A {@link ToggleButton} which can hold some data and knows how to render it. This ToggleButton cannot be unselected if it is selected and a member of a {@link ToggleGroup}.
 *
 * @param <T> the data type
 * @author puce
 */
public class DataToggleButton<T> extends XToggleButton {

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
     * It uses a {@link DataRenderer} which uses {@link Object#toString() } to render the data.
     */
    public DataToggleButton() {
        this(new ObjectRenderer());
    }

    /**
     * Creates a new instance of this class.
     *
     * @param dataRenderer the {@link DataRenderer} to render the {@link #data} of this button.
     */
    public DataToggleButton(DataRenderer<? super T> dataRenderer) {
        this(dataRenderer, null);
    }

    /**
     * Creates a new instance of this class.
     *
     * It uses a {@link DataRenderer} which uses {@link Object#toString() } to render the data.
     *
     * @param data the data of this toggle button.
     */
    public DataToggleButton(T data) {
        this(new ObjectRenderer(), data);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param dataRenderer the {@link DataRenderer} to render the {@link #data} of this button.
     * @param data the data of this button.
     */
    public DataToggleButton(DataRenderer<? super T> dataRenderer, T data) {
        this.data.addListener((ov, oldData, newData) -> LabeledUtils.configure(DataToggleButton.this, getDataRenderer(), newData)); // TODO: this instead of DataToggleButton.this possible with lambdas?
        this.dataRenderer.addListener((ov, oldData, newData) -> LabeledUtils.configure(DataToggleButton.this, newData, getData())); // TODO: this instead of DataToggleButton.this possible with lambdas?

        setData(data);
        setDataRenderer(dataRenderer);
    }

    public final T getData() {
        return dataProperty().get();
    }

    public final void setData(T data) {
        dataProperty().set(data);
    }

    public ObjectProperty<T> dataProperty() {
        return data;
    }

    public final DataRenderer<? super T> getDataRenderer() {
        return dataRendererProperty().get();
    }

    public final void setDataRenderer(DataRenderer<? super T> dataRenderer) {
        dataRendererProperty().set(dataRenderer);
    }

    public ObjectProperty<DataRenderer<? super T>> dataRendererProperty() {
        return dataRenderer;
    }

}
