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
 * A {@link FXToggleAction} adapter for {@code ToggleActionListener<? super ActionEvent>}.
 *
 * This class allows to use a {@code ToggleActionListener} as a FXToggleAction.
 *
 * @author puce
 */
public class ToggleActionListenerAdapter extends ActionListenerAdapter implements FXToggleAction {

    private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected");

    /**
     * Creates a new instance of this class.
     *
     * @param listener the action listener
     */
    public ToggleActionListenerAdapter(final ToggleActionListener<? super ActionEvent> listener) {
        super(listener);

        listener.addPropertyChangeListener("selected", evt -> selected.set((Boolean) evt.getNewValue()));
        selected.addListener((ov,  oldValue,  newValue) -> listener.setSelected(newValue));
        selected.set(listener.isSelected());
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public BooleanProperty selectedProperty() {
        return selected;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public final boolean isSelected() {
        return selectedProperty().get();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public final void setSelected(boolean selected) {
        selectedProperty().set(selected);
    }
}
