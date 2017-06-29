package org.drombler.commons.fx.scene.control.impl.skin;

import javafx.beans.binding.Bindings;
import javafx.geometry.Point2D;
import javafx.scene.control.ListView;
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
    }

    private void displayAllTasks() {
        // TODO: check if hide instead of display?
//        PopupControl tasksPopup = new PopupControl();
        Popup tasksPopup = new Popup();
        tasksPopup.getContent().add(new ListView(getSkinnable().getTasks()));
        tasksPopup.setAutoHide(true);
        tasksPopup.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_BOTTOM_LEFT);
        Point2D screenLocation = Nodes.getScreenLocation(contentPane);
        tasksPopup.show(contentPane, screenLocation.getX(), screenLocation.getY());
    }

}
