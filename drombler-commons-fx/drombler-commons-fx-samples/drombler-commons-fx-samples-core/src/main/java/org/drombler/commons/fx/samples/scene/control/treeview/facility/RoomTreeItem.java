package org.drombler.commons.fx.samples.scene.control.treeview.facility;

/**
 *
 * @author puce
 */


public class RoomTreeItem extends AbstractFacilityTreeItem<Room>{


    public RoomTreeItem(Room room) {
       super(room);
    }
    
        @Override
    public RoomPane createFacilityEditor() {
        return new RoomPane(getFacility());
    }
}
