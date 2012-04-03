/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.scene.control;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ToggleButton;
import org.projectx.lib.javafx.scene.renderer.DataRenderer;
import org.projectx.lib.javafx.scene.renderer.ObjectRenderer;

/**
 *
 * @author puce
 */
public class DataToggleButton<T> extends ToggleButton {

    private final ObjectProperty<T> data = new SimpleObjectProperty<>(this, "data");
    private final ObjectProperty<DataRenderer<? super T>> dataRenderer = new SimpleObjectProperty<>(this, "dataRenderer");

    public final T getData() {
        return data.get();
    }

    public final void setData(T data) {
        this.data.set(data);
    }

    public ObjectProperty<T> dataProperty() {
        return data;
    }

    public DataToggleButton() {
        this(new ObjectRenderer());
    }

    public DataToggleButton(DataRenderer<? super T> dataRenderer) {
        this(dataRenderer, null);
    }

    public DataToggleButton(T data) {
        this(new ObjectRenderer(), data);
    }

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