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


import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.drombler.commons.client.docking.DockableDataSensitive;
import org.drombler.commons.client.util.ResourceBundleUtils;
import org.drombler.commons.fx.docking.FXDockableData;
import org.drombler.commons.fx.fxml.FXMLLoaders;



public class SampleEditorPane extends GridPane implements DockableDataSensitive<FXDockableData> {
    private final Sample sample;

    @FXML
    private TextField nameField;
    private FXDockableData dockableData;

    public SampleEditorPane(Sample sample)  {
        loadFXML();
        this.sample = sample;

        nameField.setText(sample.getName());
    }

    private void loadFXML()  {
        FXMLLoaders.loadRoot(this, ResourceBundleUtils.getPackageResourceBundle(SampleEditorPane.class));
    }

    public Sample getSample() {
        return sample;
    }

    @Override
    public void setDockableData(FXDockableData dockableData) {
        this.dockableData = dockableData;
        this.dockableData.titleProperty().bind(nameField.textProperty());
    }


    
}
