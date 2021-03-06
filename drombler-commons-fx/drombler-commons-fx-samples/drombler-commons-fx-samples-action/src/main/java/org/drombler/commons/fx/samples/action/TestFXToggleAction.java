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

import javafx.scene.input.KeyCombination;
import org.drombler.commons.action.fx.AbstractFXToggleAction;
import org.drombler.commons.client.util.MnemonicUtils;
import org.drombler.commons.fx.scene.image.IconFactory;
import org.softsmithy.lib.util.ResourceLoader;

/**
 * Implementa {@code org.drombler.commons.action.fx.FXToggleAction}.
 *
 * <pre>
 * Pros:
 * + maximal control
 * Cons:
 * - dependencies on JavaFX
 * - dependencies on Drombler Commons
 * - currently: more code is required
 * </pre>
 *
 * @author puce
 */
public class TestFXToggleAction extends AbstractFXToggleAction {

    public TestFXToggleAction(String displayName, String accelerator, String icon) {
        setDisplayName(displayName);
        setAccelerator(KeyCombination.keyCombination(accelerator));
        setGraphicFactory(new IconFactory(icon, new ResourceLoader(TestFXToggleAction.class), false));
        selectedProperty().addListener((ov, oldValue, newValue)
                -> System.out.println(MnemonicUtils.removeMnemonicChar(getDisplayName()) + " selection changed: " + newValue));
    }

}
