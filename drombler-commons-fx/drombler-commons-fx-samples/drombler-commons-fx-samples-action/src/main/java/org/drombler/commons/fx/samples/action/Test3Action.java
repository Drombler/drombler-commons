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

import javafx.event.ActionEvent;
import javafx.scene.input.KeyCombination;
import org.drombler.commons.action.fx.AbstractFXAction;
import org.drombler.commons.fx.scene.image.IconFactory;
import org.softsmithy.lib.util.ResourceLoader;

public class Test3Action extends AbstractFXAction {

    public Test3Action() {
        setDisplayName("Test _3");
        setAccelerator(KeyCombination.keyCombination("Shortcut+3"));
        setGraphicFactory(new IconFactory("three.png", new ResourceLoader(Test3Action.class), false));
    }

    @Override
    public void handle(ActionEvent t) {
        System.out.println("Test3Action extends AbstractFXAction!");
    }
}
