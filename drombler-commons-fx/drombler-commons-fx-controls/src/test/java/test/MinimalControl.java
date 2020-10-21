package test;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import test.skin.MinimalControlSkin;
import test.skin.Stylesheets;


public class MinimalControl extends Control {

    private static final String DEFAULT_STYLE_CLASS = "minimal-control";

    private final StringProperty text = new SimpleStringProperty(this, "text");

    public MinimalControl() {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);
    }

    @Override
    public String getUserAgentStylesheet() {
        return Stylesheets.getDefaultStylesheet();
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new MinimalControlSkin(this);
    }

    public final String getText() {
        return textProperty().get();
    }

    public final void setText(String text) {
        textProperty().set(text);
    }

    public StringProperty textProperty() {
        return text;
    }
}
