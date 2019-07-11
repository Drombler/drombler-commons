package org.drombler.commons.fx.samples.scene.control.treeview.facility.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author puce
 */
public class Building extends AbstractFacility {

    private final StringProperty address = new SimpleStringProperty(this, "address");
    private final ObservableList<Floor> floors = FXCollections.observableArrayList();

    public final String getAddress() {
        return addressProperty().get();
    }

    public final void setAddress(String name) {
        addressProperty().set(name);
    }

    public StringProperty addressProperty() {
        return address;
    }

    /**
     * @return the floors
     */
    public ObservableList<Floor> getFloors() {
        return floors;
    }

}
