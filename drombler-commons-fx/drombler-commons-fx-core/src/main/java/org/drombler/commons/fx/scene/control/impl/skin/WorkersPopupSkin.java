package org.drombler.commons.fx.scene.control.impl.skin;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Skin;

/**
 *
 * @author puce
 */
public class WorkersPopupSkin implements Skin<WorkersPopup> {

    private final WorkersPopup workersPopup;
    private ProgressMonitorPopupContentPane contentPane = new ProgressMonitorPopupContentPane();


    public WorkersPopupSkin(WorkersPopup workersPopup) {
        this.workersPopup = workersPopup;
        Bindings.bindContent(contentPane.getWorkers(), workersPopup.getWorkers());
        contentPane.mainWorkerProperty().bind(workersPopup.mainWorkerProperty());
        contentPane.idProperty().bind(workersPopup.idProperty());
        contentPane.styleProperty().bind(workersPopup.styleProperty());
//        contentPane.getStyleClass().addAll(workersPopup.getStyleClass());
        Bindings.bindContent(contentPane.getStyleClass(), workersPopup.getStyleClass());

    }

    @Override
    public WorkersPopup getSkinnable() {
        return workersPopup;
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
