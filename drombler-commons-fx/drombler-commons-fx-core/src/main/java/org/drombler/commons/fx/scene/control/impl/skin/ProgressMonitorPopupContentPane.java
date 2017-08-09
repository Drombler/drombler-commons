package org.drombler.commons.fx.scene.control.impl.skin;

import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.drombler.commons.fx.fxml.FXMLLoaders;

/**
 *
 * @author puce
 */
public class ProgressMonitorPopupContentPane extends BorderPane {

    @FXML
    private VBox taskBox;

    private final ObservableList<Task<?>> tasks = FXCollections.observableArrayList();
    private final ObjectProperty<Task<?>> mainTask = new SimpleObjectProperty<>(this, "mainTask");

    public ProgressMonitorPopupContentPane() {
        FXMLLoaders.loadRoot(this);
        tasks.addListener((ListChangeListener.Change<? extends Task<?>> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(task -> {
                        TaskPane taskPane = new TaskPane();
                        taskPane.setTask(task);
                        Tooltip tooltip = new Tooltip();
                        tooltip.textProperty().bind(Bindings.format("%s (%s)", task.titleProperty(), task.stateProperty()));
                        Tooltip.install(taskPane, tooltip);
                        taskBox.getChildren().add(taskPane);
                    });
                } else {
                    if (change.wasRemoved()) {
                        change.getRemoved().forEach(task -> {
                            Optional<TaskPane> foundTaskPane = findTaskPane(task);
                            if (foundTaskPane.isPresent()) { // should always be the case
                                taskBox.getChildren().remove(foundTaskPane.get());
                            }
                        });
                    }
                }
            }
        });

        mainTask.addListener((observable, oldValue, newValue) -> {
            Optional<TaskPane> foundOldMainTaskPane = findTaskPane(oldValue);
            if (foundOldMainTaskPane.isPresent()) {
                foundOldMainTaskPane.get().setMainTask(false);
            }

            Optional<TaskPane> foundNewMainTaskPane = findTaskPane(newValue);
            if (foundNewMainTaskPane.isPresent()) {
                foundNewMainTaskPane.get().setMainTask(true);
            }
        });
    }

    private Optional<TaskPane> findTaskPane(Task<?> task) {
        Optional<TaskPane> foundTaskPane = taskBox.getChildren().stream()
                .map(node -> (TaskPane) node)
                .filter(taskPane -> taskPane.getTask().equals(task))
                .findFirst();
        return foundTaskPane;
    }

    public ObservableList<Task<?>> getTasks() {
        return tasks;
    }

    public final Task<?> getMainTask() {
        return mainTaskProperty().get();
    }

    public final void setMainTask(Task<?> mainTask) {
        mainTaskProperty().set(mainTask);
    }

    public ObjectProperty<Task<?>> mainTaskProperty() {
        return mainTask;
    }
}
