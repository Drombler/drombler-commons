package org.drombler.commons.fx.scene.control.impl.skin;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
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
    private final WorkersPopup workersPopup = new WorkersPopup();
//    private final ProgressMonitorPopupContentPane popupContentPane = new ProgressMonitorPopupContentPane();
    private final BooleanBinding workersEmptyBinding = Bindings.isEmpty(workersPopup.getWorkers());

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
        contentPane.managedProperty().bind(contentPane.visibleProperty());
        contentPane.setOnMouseClicked(event -> displayAllTasks());

//        workersPopup = createPopup();
        createPopup();
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

    private void createPopup() {
        // TODO: check if hide instead of display?
//        PopupControl workersPopup = new PopupControl();

        Bindings.bindContent(workersPopup.getWorkers(), getSkinnable().getWorkers());
        workersPopup.mainWorkerProperty().bind(getSkinnable().mainWorkerProperty());
//        Bindings.isEmpty(popupContentPane.getWorkers()).and(popupContentPane.focusedProperty()).addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
//            getSkinnable()
//        });

//        WorkersPopup popup = new WorkersPopup();// {
        WorkersPopup popup = this.workersPopup;


//            @Override
//            public Styleable getStyleableParent() {
//                return ProgressMonitorSkin.this.getSkinnable();
//            }

//            {
//        popup.setSkin(new Skin<Skinnable>() {
//                    @Override
//                    public Skinnable getSkinnable() {
//                        return popup;
//                    }
//
//                    @Override
//                    public Node getNode() {
//                        return popupContentPane;
//                    }
//
//                    @Override
//                    public void dispose() {
//                    }
//                });
//            }
//        };

        getSkinnable().sceneProperty().addListener(o -> {
            if (((ObservableValue) o).getValue() == null) {
                if (popup.isShowing()) {
                    popup.hide();
                }
            }
        });
        workersEmptyBinding.addListener((observable, oldValue, newValue) -> {
            if (popup.isShowing()) {
                popup.hide();
            }
        });

//        popup.getScene().setRoot(popupContentPane);
//        popup.getScene().setUserAgentStylesheet(getSkinnable().getUserAgentStylesheet());
        popup.setAutoHide(true);
        popup.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_BOTTOM_LEFT);
//        popup.getScene().setFill(Color.LIGHTBLUE);
//        popup.setStyle("-fx-background-color: green;");//-fx-border-color: black; -fx-border-width: 1px;");
        popup.getStyleClass().add("progress-monitor-popup");

//        return popup;
    }

}
