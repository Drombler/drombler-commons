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

import javafx.beans.binding.Bindings;
import javafx.scene.control.SkinBase;
import org.drombler.commons.fx.scene.control.StatusBar;

/**
 *
 * @author puce
 */
public class StatusBarSkin extends SkinBase<StatusBar> {
    private final StatusBarContentPane contentPane = new StatusBarContentPane();

    public StatusBarSkin(StatusBar control) {
        super(control);
        getChildren().add(contentPane);
        Bindings.bindContent(contentPane.getLeftPane().getChildren(), getSkinnable().getLeftEntries());
        Bindings.bindContent(contentPane.getCenterPane().getChildren(), getSkinnable().getCenterEntries());
        Bindings.bindContent(contentPane.getRightPane().getChildren(), getSkinnable().getRightEntries());
    }

}
