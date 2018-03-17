/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2018 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.fx.scene.control;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Hyperlink;
import org.drombler.commons.fx.scene.renderer.DataRenderer;
import org.drombler.commons.fx.scene.renderer.ObjectRenderer;

/**
 * A {@link Hyperlink} which can hold some data and knows how to render it.
 *
 * @author puce
 * @param <T>
 */
public class DataHyperlink<T> extends Hyperlink {

    /**
     * The data of this hyperlink.
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
    public DataHyperlink() {
        this(new ObjectRenderer());
    }

    /**
     * Creates a new instance of this class.
     *
     * @param dataRenderer the {@link DataRenderer} to render the {@link #data} of this hyperlink.
     */
    public DataHyperlink(DataRenderer<? super T> dataRenderer) {
        this(dataRenderer, null);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param data the data of this hyperlink.
     */
    public DataHyperlink(T data) {
        this(new ObjectRenderer(), data);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param dataRenderer the {@link DataRenderer} to render the {@link #data} of this hyperlink.
     * @param data the data of this hyperlink.
     */
    public DataHyperlink(DataRenderer<? super T> dataRenderer, T data) {
        this.data.addListener((ov, oldData, newData)
                -> LabeledUtils.configure(DataHyperlink.this, getDataRenderer(), newData));

        this.dataRenderer.addListener((ov, oldDataRenderer, newDataRenderer)
                -> LabeledUtils.configure(DataHyperlink.this, newDataRenderer, getData()));

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
}
