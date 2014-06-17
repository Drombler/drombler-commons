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

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCombination;
import org.drombler.commons.fx.scene.GraphicFactory;

/**
 * FXAction keeps the state (enabled/ disabled etc.), the information (texts, image etc.) and the logic between menu
 * items and toolbar buttons in sync.
 *
 * @author puce
 */
public interface FXAction extends EventHandler<ActionEvent> {

    String getDisplayName();

    void setDisplayName(String displayName);

    /**
     * The display name.
     *
     * @return the display name property
     */
    StringProperty displayNameProperty();

    KeyCombination getAccelerator();

    void setAccelerator(KeyCombination keyCombination);

    /**
     * The accelerator.
     *
     * @return the accelerator property
     */
    ObjectProperty<KeyCombination> acceleratorProperty();

    boolean isDisabled();

    /**
     * The disabled state of this Action.
     *
     * @return the disabled state property
     */
    ReadOnlyBooleanProperty disabledProperty();
    
    GraphicFactory getGraphicFactory();
    
    void setGraphicFactory(GraphicFactory graphicFactory);

    /**
     * The {@link GraphicFactory}.
     *
     * @return the GraphicFactory
     */
    ObjectProperty<GraphicFactory> graphicFactoryProperty();

}
