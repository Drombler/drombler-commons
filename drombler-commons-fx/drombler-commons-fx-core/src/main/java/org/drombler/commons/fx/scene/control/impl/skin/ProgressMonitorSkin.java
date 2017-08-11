package org.drombler.commons.fx.scene.control.impl.skin;

import javafx.beans.binding.Bindings;
import javafx.geometry.Point2D;
import javafx.scene.control.PopupControl;
import javafx.scene.control.SkinBase;
import javafx.stage.PopupWindow;
import org.drombler.commons.fx.scene.Nodes;
import org.drombler.commons.fx.scene.control.ProgressMonitor;

/**
 *
 * @author puce
 */
public class ProgressMonitorSkin extends SkinBase<ProgressMonitor> {

    private final ProgressMonitorContentPane contentPane = new ProgressMonitorContentPane();
    private final PopupControl workersPopup;

    /**
     *
     * @param progressMonitor
     */
    public ProgressMonitorSkin(ProgressMonitor progressMonitor) {
        super(progressMonitor);
        getChildren().add(contentPane);
        contentPane.workerProperty().bind(getSkinnable().mainWorkerProperty());
        contentPane.numberOfAdditionalWorkersProperty().bind(Bindings.size(getSkinnable().getWorkers()).subtract(1));
        contentPane.visibleProperty().bind(Bindings.isNotNull(getSkinnable().mainWorkerProperty()));
        contentPane.setOnMouseClicked(event -> displayAllTasks());

        workersPopup = createPopup();
    }

    private void displayAllTasks() {
        if (workersPopup.isShowing()) {
            workersPopup.hide();
        } else {
            workersPopup.setWidth(getSkinnable().getWidth());
            Point2D screenLocation = Nodes.getScreenLocation(getSkinnable());
            workersPopup.show(getSkinnable(), screenLocation.getX(), screenLocation.getY());
        }
    }

    private PopupControl createPopup() {
        // TODO: check if hide instead of display?
//        PopupControl workersPopup = new PopupControl();
        ProgressMonitorPopupContentPane popupContentPane = new ProgressMonitorPopupContentPane();
        Bindings.bindContent(popupContentPane.getWorkers(), getSkinnable().getWorkers());
        popupContentPane.mainWorkerProperty().bind(getSkinnable().mainWorkerProperty());

        PopupControl popup = new PopupControl();
        Bindings.size(popupContentPane.getWorkers()).isEqualTo(0).addListener((observable, oldValue, newValue) -> {
            if (popup.isShowing()) {
                popup.hide();
            }
        });

        popup.getScene().setRoot(popupContentPane);
//        popup.getScene().setUserAgentStylesheet(getSkinnable().getUserAgentStylesheet());
        popup.setAutoHide(true);
        popup.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_BOTTOM_LEFT);
//        popup.getScene().setFill(Color.LIGHTBLUE);
        popup.setStyle("-fx-background-color: green;");//-fx-border-color: black; -fx-border-width: 1px;");
        popup.getStyleClass().add("progress-monitor-popup");
//        popup.getStyleClass().add("progress-monitor-popup");

        return popup;
    }

}
