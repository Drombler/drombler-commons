package org.drombler.commons.fx.samples.scene.control.treeview.facility;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author puce
 */
public abstract class AbstractFacility implements Facility{

    private final StringProperty name = new SimpleStringProperty(this, "name");


    @Override
    public final String getName() {
        return nameProperty().get();
    }

    @Override
    public final void setName(String name) {
        nameProperty().set(name);
    }

    @Override
    public StringProperty nameProperty() {
        return name;
    }

  

}
