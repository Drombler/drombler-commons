package test.skin;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class MinimalPopupContentPane extends BorderPane {
    private final Label label = new Label("some popup text");

    public MinimalPopupContentPane() {
        setCenter(label);
    }

    @Override
    public String getUserAgentStylesheet() {
        return Stylesheets.getDefaultStylesheet();
    }

    public final String getText() {
        return textProperty().get();
    }

    public final void setText(String text) {
        textProperty().set(text);
    }

    public StringProperty textProperty() {
        return label.textProperty();
    }

}
