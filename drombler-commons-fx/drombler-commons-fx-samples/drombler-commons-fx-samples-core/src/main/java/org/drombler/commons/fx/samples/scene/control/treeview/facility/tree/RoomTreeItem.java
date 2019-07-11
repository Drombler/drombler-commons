package org.drombler.commons.fx.samples.scene.control.treeview.facility.tree;

import org.drombler.commons.fx.samples.scene.control.treeview.facility.handler.RoomHandler;
import org.drombler.commons.fx.samples.scene.control.treeview.facility.model.Room;

/**
 *
 * @author puce
 */
public class RoomTreeItem extends AbstractFacilityTreeItem<Room> {

    public RoomTreeItem(Room room) {
        super(new RoomHandler(room));
    }

}
