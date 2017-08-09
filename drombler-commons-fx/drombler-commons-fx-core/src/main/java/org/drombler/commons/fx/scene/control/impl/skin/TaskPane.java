package org.drombler.commons.fx.scene.control.impl.skin;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
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
public class TaskPane extends GridPane {

    private static final String MAIN_TASK_TITLE_LABEL_STYLE_CLASS = "task-pane-main-task-title-label";

    @FXML
    private Label titleLabel;
    @FXML
    private Label messageLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button cancelButton;

    private final ObjectProperty<Task<?>> task = new SimpleObjectProperty<>(this, "task");

    private final BooleanProperty mainTask = new SimpleBooleanProperty(this, "mainTask", false);

    public TaskPane() {
        FXMLLoaders.loadRoot(this);

        task.addListener((observable, oldValue, newValue) -> {
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

        mainTask.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                titleLabel.getStyleClass().add(MAIN_TASK_TITLE_LABEL_STYLE_CLASS);
            } else {
                titleLabel.getStyleClass().remove(MAIN_TASK_TITLE_LABEL_STYLE_CLASS);
            }
        });
    }

    public final Task<?> getTask() {
        return taskProperty().get();
    }

    public final void setTask(Task<?> task) {
        taskProperty().set(task);
    }

    public ObjectProperty<Task<?>> taskProperty() {
        return task;
    }

    public final boolean isMainTask() {
        return mainTaskProperty().get();
    }

    public final void setMainTask(boolean mainTask) {
        mainTaskProperty().set(mainTask);
    }

    public BooleanProperty mainTaskProperty() {
        return mainTask;
    }
}
