package org.drombler.commons.fx.samples.scene.control.treeview.facility.tree;

import org.drombler.commons.fx.beans.binding.CollectionBindings;
import org.drombler.commons.fx.samples.scene.control.treeview.facility.handler.FloorHandler;
import org.drombler.commons.fx.samples.scene.control.treeview.facility.model.Floor;

/**
 *
 * @author puce
 */
public class FloorTreeItem extends AbstractFacilityTreeItem<Floor> {

    public FloorTreeItem(Floor floor) {
        super(new FloorHandler(floor));
        CollectionBindings.bindContent(getChildren(), floor.getRooms(), RoomTreeItem::new);
    }


}
