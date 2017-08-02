package org.drombler.commons.fx.scene.control.impl.skin;

import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import org.drombler.commons.fx.scene.renderer.DataRenderer;

/**
 *
 * @author puce
 */
public class TaskRenderer implements DataRenderer<Task<?>> {

    @Override
    public String getText(Task<?> item) {
        return null;
    }

    @Override
    public Node getGraphic(Task<?> item) {
        TaskPane contentPane = new TaskPane();
        contentPane.setTask(item);
        return contentPane;
    }

    @Override
    public Tooltip getTooltip(Task<?> item) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText(item.getTitle());
        return tooltip;
    }

    @Override
    public List<String> getStyleClass(Task<?> item) {
        return new ArrayList<>();
    }

    @Override
    public List<String> getStyleClass() {
        return new ArrayList<>();
    }

}
