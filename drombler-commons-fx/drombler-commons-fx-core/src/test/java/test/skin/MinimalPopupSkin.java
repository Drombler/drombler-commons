package test.skin;

import javafx.scene.Node;
import javafx.scene.control.Skin;

public class MinimalPopupSkin implements Skin<MinimalPopup> {

    private final MinimalPopup popup;
    private MinimalPopupContentPane contentPane = new MinimalPopupContentPane();

    public MinimalPopupSkin(MinimalPopup popup) {
        this.popup = popup;
        contentPane.idProperty().bind(popup.idProperty());
        contentPane.styleProperty().bind(popup.styleProperty());
        contentPane.getStyleClass().addAll(popup.getStyleClass());
    }

    @Override
    public MinimalPopup getSkinnable() {
        return popup;
    }

    @Override
    public Node getNode() {
        return contentPane;
    }

    @Override
    public void dispose() {
        contentPane = null;
    }

}
