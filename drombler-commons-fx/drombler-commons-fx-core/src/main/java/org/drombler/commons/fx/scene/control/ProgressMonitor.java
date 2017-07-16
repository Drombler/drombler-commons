package org.drombler.commons.fx.scene.control;

import java.util.Objects;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectPropertyBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import org.drombler.commons.fx.scene.control.impl.skin.ProgressMonitorSkin;
import org.drombler.commons.fx.scene.control.impl.skin.Stylesheets;

/**
 *
 * @author puce
 */
public class ProgressMonitor extends Control {
    private static final String DEFAULT_STYLE_CLASS = "progress-monitor";

    private final ObservableList<Task<?>> tasks = FXCollections.observableArrayList();
    private final MainTaskProperty mainTask = new MainTaskProperty();

    public ProgressMonitor() {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserAgentStylesheet() {
        return Stylesheets.getDefaultStylesheet();
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new ProgressMonitorSkin(this);
    }

    /**
     * @return the tasks
     */
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
}
