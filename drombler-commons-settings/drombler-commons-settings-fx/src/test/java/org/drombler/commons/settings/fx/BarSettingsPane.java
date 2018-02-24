package org.drombler.commons.settings.fx;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author puce
 */


public class BarSettingsPane extends GridPane{

    public BarSettingsPane() {
        Label label = new Label("Bar: ");
        TextField textField = new TextField();
        textField.setPromptText("bar");
        add(label, 0, 0);
        add(textField, 1, 0);
        setHgap(5.0);
    }
    
}
