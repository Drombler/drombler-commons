
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.commons.fx.samples.context;

import java.io.IOException;
import java.util.Locale;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.LocalContextProvider;
import org.drombler.commons.context.SimpleContext;
import org.drombler.commons.context.SimpleContextContent;
import org.drombler.commons.fx.docking.DockablePane;
import org.drombler.commons.fx.fxml.FXMLLoaders;

public class ContextProviderPane extends DockablePane implements LocalContextProvider {

    private final SimpleContextContent contextContent = new SimpleContextContent();
    private final SimpleContext context = new SimpleContext(contextContent);
    private final Sample sample;

    @FXML
    private TextField nameField;

    public ContextProviderPane(Sample sample) throws IOException {
        loadFXML();
        this.sample = sample;

        // Add the sample to the context, so Views can see it
        contextContent.add(sample);

        nameField.setText(sample.getName());

        titleProperty().bind(nameField.textProperty());

        // Mark this Editor as modified if any control has been modified
        nameField.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                markModified();
            }
        });

    }

    private void loadFXML() throws IOException {
        FXMLLoaders.loadRoot(this);
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
