package org.drombler.commons.action.fx;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;

/**
 *
 * @author puce
 */


public abstract class AbstractFXToggleAction extends AbstractFXAction implements FXToggleAction {

    private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected");


    @Override
    public final boolean isSelected() {
        return selectedProperty().get();
    }

    @Override
    public final void setSelected(boolean selected) {
        selectedProperty().set(selected);
    }

    @Override
    public BooleanProperty selectedProperty() {
        return selected;
    }

    /**
     * Does nothing by default.
     *
     * @param event the action event
     */
    @Override
    public void handle(ActionEvent event) {
        // do nothing
    }
}
