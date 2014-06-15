/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (Sourceforge.net user: puce).
 * Copyright 2012 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.action.fx;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import org.drombler.commons.action.ToggleActionListener;

/**
 *
 * @author puce
 */
public class ToggleActionListenerAdapter extends ActionListenerAdapter implements FXToggleAction {

    private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected");

    public ToggleActionListenerAdapter(final ToggleActionListener<? super ActionEvent> listener) {
        super(listener);

        listener.addPropertyChangeListener("selected", evt -> selected.set((Boolean) evt.getNewValue()));
        selected.addListener((ov,  oldValue,  newValue) -> listener.setSelected(newValue));
        selected.set(listener.isSelected());
    }

    @Override
    public BooleanProperty selectedProperty() {
        return selected;
    }

    @Override
    public final boolean isSelected() {
        return selectedProperty().get();
    }

    @Override
    public final void setSelected(boolean selected) {
        selectedProperty().set(selected);
    }
}
