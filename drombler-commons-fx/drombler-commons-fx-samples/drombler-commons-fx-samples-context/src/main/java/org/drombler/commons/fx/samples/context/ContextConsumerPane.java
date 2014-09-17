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

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.drombler.commons.client.util.ResourceBundleUtils;
import org.drombler.commons.context.ActiveContextSensitive;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.ContextEvent;
import org.drombler.commons.fx.fxml.FXMLLoaders;

public class ContextConsumerPane extends GridPane implements ActiveContextSensitive {

    private Context activeContext;
    @FXML
    private Label nameLabel;

    private Sample sample;

    public ContextConsumerPane() throws IOException {
        loadFXML();
    }

    private void loadFXML() throws IOException {
        FXMLLoaders.loadRoot(this, ResourceBundleUtils.getPackageResourceBundle(ContextConsumerPane.class));
    }

    @Override
    public void setActiveContext(Context activeContext) {
        this.activeContext = activeContext;
        this.activeContext.addContextListener(Sample.class, (ContextEvent event) -> contextChanged());
        contextChanged();
    }

    private void contextChanged() {
        Sample newSample = activeContext.find(Sample.class);
        if ((sample == null && newSample != null) || (sample != null && !sample.equals(newSample))) {
            if (sample != null) {
                unregister();
            }
            sample = newSample;
            if (sample != null) {
                register();
            }
        }
    }

    private void unregister() {
        nameLabel.textProperty().unbind();
        nameLabel.setText(null);
    }

    private void register() {
        nameLabel.textProperty().bind(sample.nameProperty());
    }

}
