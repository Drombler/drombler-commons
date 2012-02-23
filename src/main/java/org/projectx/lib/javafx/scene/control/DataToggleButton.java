/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.scene.control;

import org.projectx.lib.javafx.scene.renderer.DataRenderer;
import org.projectx.lib.javafx.scene.renderer.ObjectRenderer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ToggleButton;

/**
 *
 * @author puce
 */
public class DataToggleButton<T> extends ToggleButton {

    private final ObjectProperty<T> data = new SimpleObjectProperty<>(this, "data");
    private final DataRenderer<? super T> cellRenderer;

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

    public DataToggleButton(DataRenderer<? super T> cellRenderer) {
        this(cellRenderer, null);
    }

    public DataToggleButton(T data) {
        this(new ObjectRenderer(), data);
    }

    public DataToggleButton(DataRenderer<? super T> cellRenderer, T data) {
        this.cellRenderer = cellRenderer;
        this.data.addListener(new ChangeListener<T>() {

            @Override
            public void changed(ObservableValue<? extends T> ov, T oldData, T newData) {
                LabeledUtils.configure(DataToggleButton.this, DataToggleButton.this.cellRenderer, newData);
            }
        });
        setData(data);
    }
}
