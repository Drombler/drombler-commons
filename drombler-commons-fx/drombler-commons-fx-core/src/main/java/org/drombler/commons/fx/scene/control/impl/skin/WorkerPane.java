/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2017 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.fx.scene.control.impl.skin;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Worker;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import org.drombler.commons.fx.fxml.FXMLLoaders;

/**
 *
 * @author puce
 */
public class WorkerPane extends GridPane {

    private static final PseudoClass MAIN_WORKER_PSEUDO_CLASS = PseudoClass.getPseudoClass("main-worker");

    @FXML
    private Label titleLabel;
    @FXML
    private Label messageLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button cancelButton;

    private final ObjectProperty<Worker<?>> worker = new SimpleObjectProperty<>(this, "worker");

    private final BooleanProperty mainWorker = new SimpleBooleanProperty(this, "mainWorker", false);

    public WorkerPane() {
        FXMLLoaders.loadRoot(this);

        worker.addListener((observable, oldValue, newValue) -> {
            titleLabel.textProperty().unbind();
            messageLabel.textProperty().unbind();
            progressBar.progressProperty().unbind();
            cancelButton.setOnAction(null);

            if (newValue != null) {
                titleLabel.textProperty().bind(newValue.titleProperty());
                messageLabel.textProperty().bind(newValue.messageProperty());
                progressBar.progressProperty().bind(newValue.progressProperty());
                cancelButton.setOnAction(event -> newValue.cancel());
            }
        });

        mainWorker.addListener((observable) -> pseudoClassStateChanged(MAIN_WORKER_PSEUDO_CLASS, mainWorker.get()));
        
         getStyleClass().add("worker-pane");
    }

    public final Worker<?> getWorker() {
        return workerProperty().get();
    }

    public final void setWorker(Worker<?> worker) {
        workerProperty().set(worker);
    }

    public ObjectProperty<Worker<?>> workerProperty() {
        return worker;
    }

    public final boolean isMainWorker() {
        return mainWorkerProperty().get();
    }

    public final void setMainWorker(boolean mainWorker) {
        mainWorkerProperty().set(mainWorker);
    }

    public BooleanProperty mainWorkerProperty() {
        return mainWorker;
    }

    @Override
    public String toString() {
        return "WorkerPane[" + "worker=" + getWorker() + ", mainWorker=" + isMainWorker() + ']';
    }
    
    
}
