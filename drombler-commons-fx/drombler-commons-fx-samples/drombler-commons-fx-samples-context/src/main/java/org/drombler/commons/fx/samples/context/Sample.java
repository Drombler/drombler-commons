
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.commons.fx.samples.context;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Sample {

    private StringProperty name = new SimpleStringProperty(this, "name");
    

    public Sample(String name) {
        setName(name);
    }

    public final String getName() {
        return nameProperty().get();
    }

    public final void setName(String name) {
        nameProperty().set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }
}
