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
package org.drombler.commons.fx.samples.context;

import java.util.Locale;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.drombler.commons.action.command.Savable;
import org.drombler.commons.client.docking.DockableData;
import org.drombler.commons.client.docking.DockableDataSensitive;
import org.drombler.commons.client.util.ResourceBundleUtils;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.LocalContextProvider;
import org.drombler.commons.context.SimpleContext;
import org.drombler.commons.context.SimpleContextContent;
import org.drombler.commons.fx.docking.FXDockableData;
import org.drombler.commons.fx.fxml.FXMLLoaders;

public class ContextProviderPane extends GridPane implements LocalContextProvider, DockableDataSensitive<FXDockableData> {

    private final SimpleContextContent contextContent = new SimpleContextContent();
    private final SimpleContext context = new SimpleContext(contextContent);
    private final Sample sample;

    @FXML
    private TextField nameField;
    private DockableData dockableData;

    public ContextProviderPane(Sample sample) {
        loadFXML();
        this.sample = sample;

        // Add the sample to the context, so Views can see it
        contextContent.add(sample);

        nameField.setText(sample.getName());

        // Mark this Editor as modified if any control has been modified
        nameField.textProperty().addListener((observable, oldValue, newValue) -> markModified());

    }

    private void loadFXML() {
        FXMLLoaders.loadRoot(this, ResourceBundleUtils.getPackageResourceBundle(ContextProviderPane.class));
    }

    @Override
    public Context getLocalContext() {
        return context;
    }

    public Sample getSample() {
        return sample;
    }

    private void markModified() {
        if (context.find(SampleSavable.class) == null) {
            // Add a SampleSavable to the context to enable the Save and the "Save All" actions.
            contextContent.add(new SampleSavable());
        }
    }

    @Override
    public void setDockableData(FXDockableData dockableData) {
        this.dockableData = dockableData;
        dockableData.titleProperty().bind(nameField.textProperty());

    }

    private class SampleSavable implements Savable {

        @Override
        public void save() {
            System.out.println("Save " + getDisplayString(Locale.getDefault()));
            sample.setName(nameField.getText());

            // Here you would e.g. write to a file/ db, call a WebService ...
            contextContent.remove(this);
        }

        @Override
        public String getDisplayString(Locale inLocale) {
            return "Sample: " + nameField.getText();
        }
    }

}
