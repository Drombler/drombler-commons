package org.drombler.commons.fx.samples.data;

import javafx.scene.control.TreeItem;
import org.drombler.commons.fx.beans.binding.CollectionBindings;

/**
 *
 * @author puce
 */


public class FloorTreeItem extends TreeItem<Facility>{


    public FloorTreeItem(Floor floor) {
       super(floor);
        CollectionBindings.bindContent(getChildren(), floor.getRooms(), RoomTreeItem::new);
    }
    
}
