/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2017 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.fx.scene.control.impl.skin;

import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author puce
 */
public class XProgressBar extends ProgressBar{

    public XProgressBar() {
        registerOnMouseClickedHandler();
    }

    public XProgressBar(double progress) {
        super(progress);
        registerOnMouseClickedHandler();
    }
    
    private void registerOnMouseClickedHandler() {
        setOnMouseClicked(this::refireEventToParent);
    }

    private void refireEventToParent(MouseEvent e) {
        Parent parent = getParent();
        if (parent != null) {
            parent.fireEvent(e);
        }
    }

    
}
