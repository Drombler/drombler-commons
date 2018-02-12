package test.skin;

import javafx.scene.control.PopupControl;
import javafx.scene.control.Skin;

public class MinimalPopup extends PopupControl {

    private static final String DEFAULT_STYLE_CLASS = "minimal-popup";

    public MinimalPopup() {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new MinimalPopupSkin(this);
    }

}
