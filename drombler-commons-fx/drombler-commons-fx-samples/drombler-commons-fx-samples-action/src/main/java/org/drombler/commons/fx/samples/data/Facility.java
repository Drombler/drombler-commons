package org.drombler.commons.fx.samples.data;

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
