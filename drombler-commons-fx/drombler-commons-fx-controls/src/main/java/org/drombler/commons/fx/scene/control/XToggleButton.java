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
package org.drombler.commons.fx.scene.control;

import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

/**
 * A ToggleButton, which cannot be unselected if it is selected and a member of a {@link ToggleGroup}.
 *
 * @author puce
 */
//TODO: better name?
public class XToggleButton extends ToggleButton {

    /**
     * Creates a new instance of this class.
     */
    public XToggleButton() {
    }

    /**
     * Creates a new instance of this class.
     *
     * @param text the text of this button
     */
    public XToggleButton(String text) {
        super(text);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param text the text of this button
     * @param graphic the graphic of this button
     */
    public XToggleButton(String text, Node graphic) {
        super(text, graphic);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fire() {
        if (getToggleGroup() == null || !isSelected()) {
            super.fire();
        }
    }

}
