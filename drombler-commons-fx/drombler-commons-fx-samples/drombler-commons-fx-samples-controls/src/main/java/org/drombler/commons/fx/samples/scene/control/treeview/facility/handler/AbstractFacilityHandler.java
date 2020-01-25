package org.drombler.commons.fx.samples.scene.control.treeview.facility.handler;

import javafx.scene.Node;
import org.drombler.commons.fx.samples.scene.control.treeview.facility.model.Facility;

/**
 *
 * @author puce
 */


public abstract class AbstractFacilityHandler<F extends Facility, C extends Facility> implements FacilityHandler<F, C> {

    private final F facility;
    private final Node facilityEditor;
    private final boolean supportingChildren;

    public AbstractFacilityHandler(F facility, Node facilityEditor, boolean supportingChildren) {
        this.facility = facility;
        this.facilityEditor = facilityEditor;
        this.supportingChildren = supportingChildren;
    }

    @Override
    public F getFacility() {
        return facility;
    }

    @Override
    public Node getEditor() {
        return facilityEditor;
    }

    @Override
    public boolean isSupportingChildren() {
        return supportingChildren;
    }

}
