package org.drombler.commons.fx.samples.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author puce
 */
public class Room  extends AbstractFacility{

    private final IntegerProperty numPersons = new SimpleIntegerProperty(this, "numPersons");

    public final int   getNumPersons() {
        return numPersonsProperty().get();
    }

    public final void setNumPersons(int numPersons) {
        numPersonsProperty().set(numPersons);
    }

    public IntegerProperty numPersonsProperty() {
        return numPersons;
    }
}
