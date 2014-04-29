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


import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.drombler.commons.fx.docking.DockablePane;
import org.drombler.commons.fx.fxml.FXMLLoaders;



public class SampleEditorPane extends DockablePane  {
    private final Sample sample;

    @FXML
    private TextField nameField;

    public SampleEditorPane(Sample sample) throws IOException {
        loadFXML();
        this.sample = sample;

        nameField.setText(sample.getName());

        titleProperty().bind(nameField.textProperty());

    }

    private void loadFXML() throws IOException {
        FXMLLoaders.loadRoot(this);
    }

    public Sample getSample() {
        return sample;
    }



    
}
