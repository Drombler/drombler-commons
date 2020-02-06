package org.drombler.commons.fx.samples.scene.control.treeview.facility.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author puce
 */
public class Room extends AbstractFacility {

    private final IntegerProperty capacity = new SimpleIntegerProperty(this, "capacity");

    public final int getCapacity() {
        return capacityProperty().get();
    }

    public final void setCapacity(int capacity) {
        capacityProperty().set(capacity);
    }

    public IntegerProperty capacityProperty() {
        return capacity;
    }
}
