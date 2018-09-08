package org.drombler.commons.fx.scene.control;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TextField;

/**
 *
 * @author puce
 */
public class DataField<T> extends TextField {

    private final ObjectProperty<T> data = new SimpleObjectProperty<>(this, "data");

    public DataField() {
    }

    public DataField(String text) {
        super(text);
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
}
