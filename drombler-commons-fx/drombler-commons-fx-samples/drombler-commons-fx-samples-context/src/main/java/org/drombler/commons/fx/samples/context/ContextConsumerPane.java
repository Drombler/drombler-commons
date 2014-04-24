
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.commons.fx.samples.context;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.drombler.commons.context.ActiveContextSensitive;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.ContextEvent;
import org.drombler.commons.context.ContextListener;
import org.drombler.commons.fx.docking.DockablePane;
import org.drombler.commons.fx.fxml.FXMLLoaders;

public class ContextConsumerPane extends DockablePane implements ActiveContextSensitive {

    private static final String FXML_EXTENSION = ".fxml";

    private Context activeContext;
    @FXML
    private Label nameLabel;

    private Sample sample;

    public ContextConsumerPane() throws IOException {
        loadFXML();
    }

    private void loadFXML() throws IOException {
        FXMLLoaders.loadRoot(this);
    }

    @Override
    public void setActiveContext(Context activeContext) {
        this.activeContext = activeContext;
        this.activeContext.addContextListener(Sample.class, new ContextListener() {
            @Override
            public void contextChanged(ContextEvent event) {
                ContextConsumerPane.this.contextChanged();
            }
        });
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
