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

/**
 *
 * @author puce
 */
public class ProgressMonitorPopupContentPane extends BorderPane {

    @FXML
    private VBox workerBox;

    private final ObservableList<Worker<?>> workers = FXCollections.observableArrayList();
    private final ObjectProperty<Worker<?>> mainWorker = new SimpleObjectProperty<>(this, "mainWorker");

    public ProgressMonitorPopupContentPane() {
        FXMLLoaders.loadRoot(this);
        workers.addListener((ListChangeListener.Change<? extends Worker<?>> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(worker -> {
                        WorkerPane workerPane = new WorkerPane();
                        workerPane.setWorker(worker);
                        Tooltip tooltip = new Tooltip();
                        tooltip.textProperty().bind(Bindings.format("%s (%s)", worker.titleProperty(), worker.stateProperty()));
                        Tooltip.install(workerPane, tooltip);
                        workerPane.setMaxWidth(Double.MAX_VALUE);
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
        });

        mainWorker.addListener((observable, oldValue, newValue) -> {
            Optional<WorkerPane> foundOldMainWorkerPane = findWorkerPane(oldValue);
            if (foundOldMainWorkerPane.isPresent()) {
                foundOldMainWorkerPane.get().setMainWorker(false);
            }

            Optional<WorkerPane> foundNewMainWorkerPane = findWorkerPane(newValue);
            if (foundNewMainWorkerPane.isPresent()) {
                foundNewMainWorkerPane.get().setMainWorker(true);
            }
        });
    }

    private Optional<WorkerPane> findWorkerPane(Worker<?> worker) {
        Optional<WorkerPane> foundWorkerPane = workerBox.getChildren().stream()
                .map(node -> (WorkerPane) node)
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
