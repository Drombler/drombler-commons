package org.drombler.commons.fx.samples.scene.control.treeview.facility.handler;

import org.drombler.commons.fx.samples.scene.control.treeview.facility.editor.BuildingPane;
import org.drombler.commons.fx.samples.scene.control.treeview.facility.model.Building;
import org.drombler.commons.fx.samples.scene.control.treeview.facility.model.Floor;

/**
 *
 * @author puce
 */
public class BuildingHandler extends AbstractFacilityHandler<Building, Floor> {

    public BuildingHandler(Building building) {
        super(building, new BuildingPane(building), true);

    }

    @Override
    public FloorHandler createChildHandler() {
        return new FloorHandler(new Floor());
    }

    @Override
    public void addChild(Floor floor) {
        getFacility().getFloors().add(floor);
    }

}
