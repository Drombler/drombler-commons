/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.scene.control;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import org.projectx.lib.javafx.scene.renderer.DataRenderer;
import org.projectx.lib.javafx.scene.renderer.ObjectRenderer;

/**
 *
 * @author puce
 */
public class DataButton<T> extends Button {

    private final ObjectProperty<T> data = new SimpleObjectProperty<>(this, "data");
    private final DataRenderer<? super T> dataRenderer;

    public final T getData() {
        return data.get();
    }

    public final void setData(T data) {
        this.data.set(data);
    }

    public ObjectProperty<T> dataProperty() {
        return data;
    }

    public DataButton() {
        this(new ObjectRenderer());
    }

    public DataButton(DataRenderer<? super T> dataRenderer) {
        this(dataRenderer, null);
    }

    public DataButton(T data) {
        this(new ObjectRenderer(), data);
    }

    public DataButton(DataRenderer<? super T> dataRenderer, T data) {
        this.dataRenderer = dataRenderer;
        this.data.addListener(new ChangeListener<T>() {

            @Override
            public void changed(ObservableValue<? extends T> ov, T oldData, T newData) {
                LabeledUtils.configure(DataButton.this, DataButton.this.dataRenderer, newData);
            }
        });
        setData(data);
    }
}
