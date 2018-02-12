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
package org.drombler.commons.fx.scene.control;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import org.drombler.commons.fx.concurrent.WorkerUtils;
import org.drombler.commons.fx.scene.control.impl.skin.ProgressMonitorSkin;
import org.drombler.commons.fx.scene.control.impl.skin.Stylesheets;

/**
 * A control to monitor the progress of a list of background {@link Worker}s.
 * <p>
 *   <img src="doc-files/progress-monitor.png" alt="ProgressMonitor Sample">
 * </p>
 *
 * @author puce
 */
public class ProgressMonitor extends Control {

    private static final String DEFAULT_STYLE_CLASS = "progress-monitor";

    /**
     * The workers to monitor.
     */
    private final ObservableList<Worker<?>> workers = FXCollections.observableArrayList();
    /**
     * The main worker, which gets always shown.
     */
    private final MainWorkerProperty mainWorker = new MainWorkerProperty();
    private final ObjectBinding<Worker<?>> firstWorkerBinding;
    private final Map<Worker<?>, WorkerFinishedListener> workerFinishedListeners = new HashMap<>();

    /**
     * Creates a new instance of this class.
     */
    public ProgressMonitor() {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);
        firstWorkerBinding = Bindings.valueAt(workers, 0);
        firstWorkerBinding.addListener((observable, oldValue, newValue) -> mainWorker.set(newValue));
        workers.addListener((ListChangeListener.Change<? extends Worker<?>> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(this::addWorkerFinishedListener);
                } else if (change.wasRemoved()) {
                        change.getRemoved().forEach(this::removeWorkerFinishedListener);
                    }
                }
        });
    }

    private void addWorkerFinishedListener(Worker<?> worker) {
        WorkerFinishedListener workerFinishedListener = new WorkerFinishedListener(worker);
        workerFinishedListeners.put(worker, workerFinishedListener);
        worker.stateProperty().addListener(workerFinishedListener);

        if (WorkerUtils.getFinishedStates().contains(worker.getState())) { // state listener won't get triggered
            workers.remove(worker);
        }
    }

    private void removeWorkerFinishedListener(Worker<?> worker) {
        WorkerFinishedListener workerFinishedListener = workerFinishedListeners.remove(worker);
        worker.stateProperty().removeListener(workerFinishedListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserAgentStylesheet() {
        return Stylesheets.getDefaultStylesheet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new ProgressMonitorSkin(this);
    }

    /**
     * Gets the list of background {@link Worker}s
     *
     * @return the list of background Workers
     */
    public ObservableList<Worker<?>> getWorkers() {
        return workers;
    }

    public Worker<?> getMainWorker() {
        return mainWorkerProperty().get();
    }

    public ReadOnlyObjectProperty<Worker<?>> mainWorkerProperty() {
        return mainWorker;
    }

    private class MainWorkerProperty extends ReadOnlyObjectPropertyBase<Worker<?>> {

        private Worker<?> mainWorker = null;

        @Override
        public final Worker<?> get() {
            return mainWorker;
        }

        private void set(Worker<?> newValue) {
            if (!Objects.equals(mainWorker, newValue)) {
                mainWorker = newValue;
                fireValueChangedEvent();
            }
        }

        @Override
        public Object getBean() {
            return ProgressMonitor.this;
        }

        @Override
        public String getName() {
            return "mainWorker";
        }
    }

    private class WorkerFinishedListener implements ChangeListener<Worker.State> {

        private final Worker<?> worker;

        public WorkerFinishedListener(Worker<?> worker) {
            this.worker = worker;
        }

        @Override
        public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
            if (WorkerUtils.getFinishedStates().contains(newValue)) {
                workers.remove(worker);
            }
        }
    }
}
