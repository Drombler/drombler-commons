package org.drombler.commons.fx.samples.data;

import org.drombler.commons.fx.beans.binding.CollectionBindings;

/**
 *
 * @author puce
 */


public class BuildingTreeItem extends AbstractFacilityTreeItem<Building>{


    public BuildingTreeItem(Building building) {
       super(building);
        CollectionBindings.bindContent(getChildren(), building.getFloors(), FloorTreeItem::new);
    }

    @Override
    public BuildingPane createFacilityEditor() {
        return new BuildingPane(getFacility());
    }
    
}
