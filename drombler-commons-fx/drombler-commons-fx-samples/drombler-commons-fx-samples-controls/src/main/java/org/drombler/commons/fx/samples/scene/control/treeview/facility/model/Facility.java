package org.drombler.commons.fx.samples.scene.control.treeview.facility.model;

import javafx.beans.property.StringProperty;

/**
 *
 * @author puce
 */
public interface Facility {

    String getName();

    void setName(String name);

    StringProperty nameProperty();
}
