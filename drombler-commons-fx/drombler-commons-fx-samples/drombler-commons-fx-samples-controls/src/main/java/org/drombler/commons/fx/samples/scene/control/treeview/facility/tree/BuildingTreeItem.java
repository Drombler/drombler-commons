package org.drombler.commons.fx.samples.scene.control.treeview.facility.tree;

import org.drombler.commons.fx.beans.binding.CollectionBindings;
import org.drombler.commons.fx.samples.scene.control.treeview.facility.handler.BuildingHandler;
import org.drombler.commons.fx.samples.scene.control.treeview.facility.model.Building;

/**
 *
 * @author puce
 */
public class BuildingTreeItem extends AbstractFacilityTreeItem<Building> {

    public BuildingTreeItem(Building building) {
        super(new BuildingHandler(building));
        CollectionBindings.bindContent(getChildren(), building.getFloors(), FloorTreeItem::new);
    }


}
