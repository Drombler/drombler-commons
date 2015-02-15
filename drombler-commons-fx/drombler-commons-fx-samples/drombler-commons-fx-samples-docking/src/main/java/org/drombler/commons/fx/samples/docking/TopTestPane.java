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
package org.drombler.commons.fx.samples.docking;

import javafx.scene.layout.BorderPane;
import org.drombler.commons.fx.fxml.FXMLLoaders;

public class TopTestPane extends BorderPane {

    public TopTestPane() {
        loadFXML();
    }

    private void loadFXML() {
        FXMLLoaders.loadRoot(this);
    }

}
