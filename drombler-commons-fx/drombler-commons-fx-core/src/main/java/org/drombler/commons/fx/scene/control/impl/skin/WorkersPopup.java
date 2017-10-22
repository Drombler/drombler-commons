package org.drombler.commons.fx.scene.control.impl.skin;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.scene.control.PopupControl;
import javafx.scene.control.Skin;

/**
 *
 * @author puce
 */


public class WorkersPopup extends PopupControl {

    private static final String DEFAULT_STYLE_CLASS = "worker-popup";

    private final ObservableList<Worker<?>> workers = FXCollections.observableArrayList();
    private final ObjectProperty<Worker<?>> mainWorker = new SimpleObjectProperty<>(this, "mainWorker");

    public WorkersPopup() {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new WorkersPopupSkin(this);
    }

    public ObservableList<Worker<?>> getWorkers() {
        return workers;
    }

    public final Worker<?> getMainWorker() {
        return mainWorkerProperty().get();
    }

    public final void setMainWorker(Worker<?> mainWorker) {
        mainWorkerProperty().set(mainWorker);
    }

    public ObjectProperty<Worker<?>> mainWorkerProperty() {
        return mainWorker;
    }


}
