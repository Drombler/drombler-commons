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
 * Copyright 2014 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.fx.samples.action;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCombination;
import org.drombler.commons.action.fx.AbstractFXAction;
import org.drombler.commons.action.fx.FXToggleAction;
import org.drombler.commons.fx.scene.image.IconFactory;
import org.softsmithy.lib.util.ResourceLoader;

public class Test5ToggleAction extends AbstractFXAction implements FXToggleAction {

    private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected");

    public Test5ToggleAction() {
        setDisplayName("Test _5");
        setAccelerator(KeyCombination.keyCombination("Shortcut+5"));
        setGraphicFactory(new IconFactory("five.png", new ResourceLoader(Test5ToggleAction.class), false));
        selected.addListener((ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue)
                -> System.out.println("Test5ToggleAction selection changed: " + newValue));
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

    @Override
    public void handle(ActionEvent t) {
        // do nothing
    }
}
