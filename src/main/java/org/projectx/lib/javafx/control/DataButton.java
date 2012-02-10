/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.control;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;

/**
 *
 * @author puce
 */
public class DataButton<T> extends Button {

    private final ObjectProperty<T> data = new SimpleObjectProperty<>(this, "data");
    private final CellRenderer<? super T> cellRenderer;

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
        this(new ObjectCellRenderer());
    }

    public DataButton(CellRenderer<? super T> cellRenderer) {
        this(cellRenderer, null);
    }

    public DataButton(T data) {
        this(new ObjectCellRenderer(), data);
    }

    public DataButton(CellRenderer<? super T> cellRenderer, T data) {
        this.cellRenderer = cellRenderer;
        this.data.addListener(new ChangeListener<T>() {

            @Override
            public void changed(ObservableValue<? extends T> ov, T oldData, T newData) {
                LabeledUtils.configure(DataButton.this, DataButton.this.cellRenderer, newData);
            }
        });
        setData(data);
    }
}
