/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.control;

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

    public DataToggleButton() {
        this(new ObjectCellRenderer());
    }

    public DataToggleButton(CellRenderer<? super T> cellRenderer) {
        this(cellRenderer, null);
    }

    public DataToggleButton(T data) {
        this(new ObjectCellRenderer(), data);
    }

    public DataToggleButton(CellRenderer<? super T> cellRenderer, T data) {
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
