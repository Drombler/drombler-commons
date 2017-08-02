package org.drombler.commons.fx.scene.control.impl.skin;

import javafx.beans.binding.Bindings;
import javafx.geometry.Point2D;
import javafx.scene.control.SkinBase;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import org.drombler.commons.fx.scene.Nodes;
import org.drombler.commons.fx.scene.control.ProgressMonitor;

/**
 *
 * @author puce
 */
public class ProgressMonitorSkin extends SkinBase<ProgressMonitor> {

    private final ProgressMonitorContentPane contentPane = new ProgressMonitorContentPane();
    private final Popup tasksPopup;

    /**
     *
     * @param progressMonitor
     */
    public ProgressMonitorSkin(ProgressMonitor progressMonitor) {
        super(progressMonitor);
        getChildren().add(contentPane);
        contentPane.taskProperty().bind(getSkinnable().mainTaskProperty());
        contentPane.numberOfAdditionalTasksProperty().bind(Bindings.size(getSkinnable().getTasks()).subtract(1));
        contentPane.visibleProperty().bind(Bindings.isNotNull(getSkinnable().mainTaskProperty()));
        contentPane.setOnMouseClicked(event -> displayAllTasks());

        tasksPopup = createPopup();
    }

    private void displayAllTasks() {
        if (tasksPopup.isShowing()) {
            tasksPopup.hide();
        } else {
            tasksPopup.setWidth(contentPane.getWidth());
            Point2D screenLocation = Nodes.getScreenLocation(contentPane);
            tasksPopup.show(contentPane, screenLocation.getX(), screenLocation.getY());
        }
    }

    private Popup createPopup() {
        // TODO: check if hide instead of display?
//        PopupControl tasksPopup = new PopupControl();
        ProgressMonitorPopupContentPane popupContentPane = new ProgressMonitorPopupContentPane();
        Bindings.bindContent(popupContentPane.getTasks(), getSkinnable().getTasks());
        Popup popup = new Popup();
        popup.getContent().add(popupContentPane);
        popup.setAutoHide(true);
        popup.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_BOTTOM_LEFT);
        return popup;
    }

}
