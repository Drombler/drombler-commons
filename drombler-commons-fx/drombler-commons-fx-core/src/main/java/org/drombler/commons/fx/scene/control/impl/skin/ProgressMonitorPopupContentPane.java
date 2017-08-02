package org.drombler.commons.fx.scene.control.impl.skin;

import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.drombler.commons.fx.fxml.FXMLLoaders;
import org.drombler.commons.fx.scene.control.LabeledUtils;

/**
 *
 * @author puce
 */
public class ProgressMonitorPopupContentPane extends BorderPane {

    @FXML
    private VBox taskBox;

    private final ObservableList<Task<?>> tasks = FXCollections.observableArrayList();
    private final TaskRenderer taskRenderer = new TaskRenderer();

    public ProgressMonitorPopupContentPane() {
        FXMLLoaders.loadRoot(this);
        tasks.addListener((ListChangeListener.Change<? extends Task<?>> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(task -> {
                        Label taskLabel = new Label();
                        LabeledUtils.configure(taskLabel, taskRenderer, task);
                        taskBox.getChildren().add(taskLabel);
                    });
                } else {
                    if (change.wasRemoved()) {
                        change.getRemoved().forEach(task -> {
                            Optional<TaskPane> foundTaskPane = taskBox.getChildren().stream()
                                    .map(node -> (Label) node)
                                    .map(label -> (TaskPane) label.getGraphic())
                                    .filter(taskPane -> taskPane.getTask().equals(task))
                                    .findFirst();
                            if (foundTaskPane.isPresent()) { // should always be the case
                                taskBox.getChildren().remove(foundTaskPane.get());
                            }
                        });
                    }
                }
            }
        });
    }

    public ObservableList<Task<?>> getTasks() {
        return tasks;
    }
}
