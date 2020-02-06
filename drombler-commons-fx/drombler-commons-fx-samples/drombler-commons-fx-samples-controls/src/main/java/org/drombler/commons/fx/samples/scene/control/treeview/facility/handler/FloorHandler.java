package org.drombler.commons.fx.samples.scene.control.treeview.facility.handler;

import org.drombler.commons.fx.samples.scene.control.treeview.facility.editor.FloorPane;
import org.drombler.commons.fx.samples.scene.control.treeview.facility.model.Floor;
import org.drombler.commons.fx.samples.scene.control.treeview.facility.model.Room;

/**
 *
 * @author puce
 */


public class FloorHandler extends AbstractFacilityHandler<Floor, Room> {

    public FloorHandler(Floor floor) {
        super(floor, new FloorPane(floor), true);
    }

    @Override
    public RoomHandler createChildHandler() {
        return new RoomHandler(new Room());
    }

    @Override
    public void addChild(Room room) {
        getFacility().getRooms().add(room);
    }

}
