package org.drombler.commons.fx.samples.scene.control.treeview.facility;

import org.drombler.commons.fx.beans.binding.CollectionBindings;

/**
 *
 * @author puce
 */


public class FloorTreeItem extends AbstractFacilityTreeItem<Floor>{


    public FloorTreeItem(Floor floor) {
       super(floor);
        CollectionBindings.bindContent(getChildren(), floor.getRooms(), RoomTreeItem::new);
    }
    
        @Override
    public FloorPane createFacilityEditor() {
        return new FloorPane(getFacility());
    }
    
}
