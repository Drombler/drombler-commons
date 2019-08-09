package org.drombler.commons.fx.samples.scene.control.treeview.facility.tree;

import javafx.scene.control.TreeItem;
import org.drombler.commons.fx.samples.scene.control.treeview.facility.handler.FacilityHandler;
import org.drombler.commons.fx.samples.scene.control.treeview.facility.model.Facility;

/**
 *
 * @author puce
 */
public abstract class AbstractFacilityTreeItem<T extends Facility> extends TreeItem<Facility> {

    private final FacilityHandler<T, ?> facilityFoo;

    /**
     *
     * @param facilityFoo
     */
    public AbstractFacilityTreeItem(FacilityHandler<T, ?> facilityFoo) {
        super(facilityFoo.getFacility());
        this.facilityFoo = facilityFoo;
    }


    /**
     * @return the facility
     */
    public FacilityHandler<T, ?> getFacilityHandler() {
        return facilityFoo;
    }


}
