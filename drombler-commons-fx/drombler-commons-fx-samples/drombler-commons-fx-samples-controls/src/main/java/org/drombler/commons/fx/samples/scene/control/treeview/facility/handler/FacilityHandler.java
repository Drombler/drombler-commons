package org.drombler.commons.fx.samples.scene.control.treeview.facility.handler;

import javafx.scene.Node;
import org.drombler.commons.fx.samples.scene.control.treeview.facility.model.Facility;

/**
 *
 * @author puce
 */


public interface FacilityHandler<F extends Facility, C extends Facility> {

    F getFacility();

    Node getEditor();

    boolean isSupportingChildren();

    FacilityHandler<C, ?> createChildHandler();

    void addChild(C childFacility);
}
