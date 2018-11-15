package org.drombler.commons.fx.samples.data;

import javafx.scene.control.TreeItem;
import org.drombler.commons.fx.beans.binding.CollectionBindings;

/**
 *
 * @author puce
 */


public class BuildingTreeItem extends TreeItem<Facility>{


    public BuildingTreeItem(Building building) {
       super(building);
        CollectionBindings.bindContent(getChildren(), building.getFloors(), FloorTreeItem::new);
    }
    
}
