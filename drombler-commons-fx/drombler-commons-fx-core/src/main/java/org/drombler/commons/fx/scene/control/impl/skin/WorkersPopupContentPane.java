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

import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.drombler.commons.fx.fxml.FXMLLoaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author puce
 */
public class WorkersPopupContentPane extends BorderPane {

    private static final Logger LOG = LoggerFactory.getLogger(WorkersPopupContentPane.class);

    @FXML
    private VBox workerBox;

    private final ObservableList<Worker<?>> workers = FXCollections.observableArrayList();
    private final ObjectProperty<Worker<?>> mainWorker = new SimpleObjectProperty<>(this, "mainWorker");

    public WorkersPopupContentPane() {
        FXMLLoaders.loadRoot(this);
        workers.addListener((ListChangeListener.Change<? extends Worker<?>> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(worker -> {
                        WorkerPane workerPane = createWorkerPane(worker);
                        VBox.setVgrow(workerPane, Priority.ALWAYS);
                        workerBox.getChildren().add(workerPane);
                    });
                } else {
                    if (change.wasRemoved()) {
                        change.getRemoved().forEach(worker -> {
                            Optional<WorkerPane> foundWorkerPane = findWorkerPane(worker);
                            if (foundWorkerPane.isPresent()) { // should always be the case
                                workerBox.getChildren().remove(foundWorkerPane.get());
                                VBox.clearConstraints(foundWorkerPane.get());
                            }
                        });
                    }
                }
            }
            if (getMainWorker() != null) { // sometimes the list gets updated after the mainWorker property
                configureMainWorkerPane(getMainWorker(), true);
            }
        });

        mainWorker.addListener((observable, oldValue, newValue) -> {
            if (LOG.isDebugEnabled()) {
                LOG.debug("main worker changed! {} -> {}", oldValue, newValue);
                LOG.debug("workerPanes before applying the change: {}", workerBox.getChildren());
            }
            configureMainWorkerPane(oldValue, false);
            configureMainWorkerPane(newValue, true);
        });
    }

    private void configureMainWorkerPane(Worker<?> worker, boolean mainWorker) {
        Optional<WorkerPane> foundWorkerPane = findWorkerPane(worker);
        if (foundWorkerPane.isPresent() && foundWorkerPane.get().isMainWorker() != mainWorker) {
            foundWorkerPane.get().setMainWorker(mainWorker);
            if (! mainWorker && LOG.isDebugEnabled()) {
                LOG.debug("Old Main WorkerPane found!");
            }
            if (mainWorker && LOG.isDebugEnabled()) {
                LOG.debug("New Main WorkerPane found!");
            }
        }
    }

    private WorkerPane createWorkerPane(Worker<?> worker) {
        WorkerPane workerPane = new WorkerPane();
        workerPane.setWorker(worker);
        Tooltip tooltip = new Tooltip();
        tooltip.textProperty().bind(Bindings.format("%s (%s)", worker.titleProperty(), worker.stateProperty()));
        Tooltip.install(workerPane, tooltip);
        workerPane.setMaxWidth(Double.MAX_VALUE);
        return workerPane;
    }

    private Optional<WorkerPane> findWorkerPane(Worker<?> worker) {
        Optional<WorkerPane> foundWorkerPane = workerBox.getChildren().stream()
                .map(WorkerPane.class::cast)
                .filter(workerPane -> workerPane.getWorker().equals(worker))
                .findFirst();
        return foundWorkerPane;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserAgentStylesheet() {
        return Stylesheets.getDefaultStylesheet();
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
