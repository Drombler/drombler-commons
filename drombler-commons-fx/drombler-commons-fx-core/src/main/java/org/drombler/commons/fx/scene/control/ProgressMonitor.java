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
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import org.drombler.commons.fx.concurrent.WorkerUtils;
import org.drombler.commons.fx.scene.control.impl.skin.ProgressMonitorSkin;
import org.drombler.commons.fx.scene.control.impl.skin.Stylesheets;

/**
 * A control to monitor the progress of a list of background {@link Task}s.
 *
 * @author puce
 */
public class ProgressMonitor extends Control {

    private static final String DEFAULT_STYLE_CLASS = "progress-monitor";

    /**
     * The tasks to monitor.
     */
    private final ObservableList<Task<?>> tasks = FXCollections.observableArrayList();
    /**
     * The main task, which gets always shown.
     */
    private final MainTaskProperty mainTask = new MainTaskProperty();
    private final ObjectBinding<Task<?>> firstTaskBinding;
    private final Map<Task<?>, TaskFinishedListener> taskFinishedListeners = new HashMap<>();

    /**
     * Creates a new instance of this class.
     */
    public ProgressMonitor() {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);
        firstTaskBinding = Bindings.valueAt(tasks, 0);
        firstTaskBinding.addListener((observable, oldValue, newValue) -> mainTask.set(newValue));
        tasks.addListener((ListChangeListener.Change<? extends Task<?>> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(this::addTaskFinishedListener);
                } else if (change.wasRemoved()) {
                    change.getRemoved().forEach(this::removeTaskFinishedListener);
                    }
            }
        });
    }

    private void addTaskFinishedListener(Task<?> task) {
        TaskFinishedListener taskFinishedListener = new TaskFinishedListener(task);
        taskFinishedListeners.put(task, taskFinishedListener);
        task.stateProperty().addListener(taskFinishedListener);

        if (WorkerUtils.getFinishedStates().contains(task.getState())) { // state listener won't get triggered
            tasks.remove(task);
        }
    }

    private void removeTaskFinishedListener(Task<?> task) {
        TaskFinishedListener taskFinishedListener = taskFinishedListeners.remove(task);
        task.stateProperty().removeListener(taskFinishedListener);
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

    public ObservableList<Task<?>> getTasks() {
        return tasks;
    }

    public Task<?> getMainTask() {
        return mainTaskProperty().get();
    }

    public ReadOnlyObjectProperty<Task<?>> mainTaskProperty() {
        return mainTask;
    }

    private class MainTaskProperty extends ReadOnlyObjectPropertyBase<Task<?>> {

        private Task<?> mainTask = null;

        @Override
        public final Task<?> get() {
            return mainTask;
        }

        private void set(Task<?> newValue) {
            if (!Objects.equals(mainTask, newValue)) {
                mainTask = newValue;
                fireValueChangedEvent();
            }
        }

        @Override
        public Object getBean() {
            return ProgressMonitor.this;
        }

        @Override
        public String getName() {
            return "mainTask";
        }
    }

    private class TaskFinishedListener implements ChangeListener<Worker.State> {

        private final Task<?> task;

        public TaskFinishedListener(Task<?> task) {
            this.task = task;
        }

        @Override
        public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
            if (WorkerUtils.getFinishedStates().contains(newValue)) {
                tasks.remove(task);
            }
        }
    }
}
