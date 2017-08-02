package org.drombler.commons.fx.scene.control.impl.skin;

import javafx.beans.property.ObjectProperty;
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

    @FXML
    private Label titleLabel;
    @FXML
    private Label messageLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button cancelButton;

    private final ObjectProperty<Task<?>> task = new SimpleObjectProperty<>(this, "task");

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

}
