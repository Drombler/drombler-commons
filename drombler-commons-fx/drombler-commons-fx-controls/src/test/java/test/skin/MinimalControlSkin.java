package test.skin;

import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.BorderPane;
import javafx.stage.PopupWindow;
import test.MinimalControl;

public class MinimalControlSkin extends SkinBase<MinimalControl> {

    private final Label label = new Label();
    private final BorderPane contentPane = new BorderPane(label);
    private final MinimalPopup popup = new MinimalPopup();

    public MinimalControlSkin(MinimalControl control) {
        super(control);
        getChildren().add(contentPane);

        label.textProperty().bind(control.textProperty());
        label.setOnMouseClicked(event -> openPopup());

        popup.setAutoHide(true);
        popup.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_BOTTOM_LEFT);
    }

    private void openPopup() {
        Bounds localBounds = label.getBoundsInLocal();
        Bounds screenBounds = label.localToScreen(localBounds);

        popup.show(label, screenBounds.getMinX(), screenBounds.getMinY());
    }

}
