package org.drombler.commons.fx.scene.control.impl.skin;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import org.drombler.commons.fx.fxml.FXMLLoaders;

/**
 *
 * @author puce
 */


public class ProgressMonitorContentPane extends GridPane {

    private final ObjectProperty<Task<?>> task = new SimpleObjectProperty<>(this, "task", null);
    private final IntegerProperty numberOfAdditionalTasks = new SimpleIntegerProperty(this, "numberOfAdditionalTasks", 0);

    @FXML
    private Label titleLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button cancelButton;
    @FXML
    private Label moreTasksIndicatorLabel;

    public ProgressMonitorContentPane() {
        FXMLLoaders.loadRoot(this);

        moreTasksIndicatorLabel.visibleProperty().bind(Bindings.greaterThan(numberOfAdditionalTasks, 0));
        moreTasksIndicatorLabel.setText("(?? more...)");

        cancelButton.setTooltip(new Tooltip("Click to cancel task"));

        task.addListener((observable, oldValue, newValue) -> {
            titleLabel.textProperty().unbind();
            progressBar.progressProperty().unbind();
            cancelButton.setOnAction(null);

            if (newValue != null) {
                titleLabel.textProperty().bind(newValue.titleProperty());
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

    public final int getNumberOfAdditionalTasks() {
        return numberOfAdditionalTasksProperty().get();
    }

    public final void setnumberOfAdditionalTasks(int numberOfAdditionalTasks) {
        numberOfAdditionalTasksProperty().set(numberOfAdditionalTasks);
    }

    public IntegerProperty numberOfAdditionalTasksProperty() {
        return numberOfAdditionalTasks;
    }


}
