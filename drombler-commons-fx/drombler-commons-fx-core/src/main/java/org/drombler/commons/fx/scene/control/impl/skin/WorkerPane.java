package org.drombler.commons.fx.scene.control.impl.skin;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Worker;
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

    private static final String MAIN_TASK_TITLE_LABEL_STYLE_CLASS = "worker-pane-main-worker-title-label";

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

        mainWorker.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                titleLabel.getStyleClass().add(MAIN_TASK_TITLE_LABEL_STYLE_CLASS);
            } else {
                titleLabel.getStyleClass().remove(MAIN_TASK_TITLE_LABEL_STYLE_CLASS);
            }
        });
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
}
