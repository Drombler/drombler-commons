package org.drombler.commons.fx.samples.scene.control.treeview.facility.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author puce
 */
public class Floor extends AbstractFacility {

    private final ObservableList<Room> rooms = FXCollections.observableArrayList();

    /**
     * @return the rooms
     */
    public ObservableList<Room> getRooms() {
        return rooms;
    }

}
