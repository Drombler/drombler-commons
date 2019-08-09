package org.drombler.commons.fx.samples.scene.control.treeview.facility.handler;

import org.drombler.commons.fx.samples.scene.control.treeview.facility.editor.RoomPane;
import org.drombler.commons.fx.samples.scene.control.treeview.facility.model.Room;

public class RoomHandler extends AbstractFacilityHandler<Room, Room> {


    public RoomHandler(Room room) {
        super(room, new RoomPane(room), false);
    }

    @Override
    public FacilityHandler<Room, ?> createChildHandler() {
        throw new UnsupportedOperationException("Room does not support children!");
    }

    @Override
    public void addChild(Room childFacility) {
        throw new UnsupportedOperationException("Room does not support children!");
    }

}
