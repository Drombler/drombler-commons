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
package org.drombler.commons.fx.ensemble.time.impl;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.drombler.acp.core.docking.ViewDocking;
import org.drombler.acp.core.docking.WindowMenuEntry;
import org.drombler.fx.core.commons.fx.fxml.FXMLLoaders;
import org.drombler.fx.core.docking.DockablePane;

/**
 *
 * @author puce
 */
@ViewDocking(areaId = "left", displayName = "#OverviewPane.displayName", position = 10, menuEntry =
@WindowMenuEntry(path = "", position = 10))
public class OverviewPane extends DockablePane {

    @FXML
    private Button dateTimeButton;
    public OverviewPane() throws IOException {
        loadFXML();
        openDateTimeEditor();
    }

    private void loadFXML() throws IOException {
        FXMLLoaders.loadRoot(this);
    }
    
    @FXML
    public void showDateTimeEditor(ActionEvent actionEvent) throws IOException{
        openDateTimeEditor();
    }

    private void openDateTimeEditor() throws IOException {
        DateTimeEditorPane dateTimeEditorPane = new DateTimeEditorPane();
        dateTimeEditorPane.open();
    }
}
