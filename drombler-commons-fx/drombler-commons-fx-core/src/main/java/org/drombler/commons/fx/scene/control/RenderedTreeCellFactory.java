package org.drombler.commons.fx.scene.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.util.Callback;
import org.apache.commons.lang3.ClassUtils;
import org.drombler.commons.fx.scene.renderer.DataRenderer;
import org.drombler.commons.fx.scene.renderer.ObjectRenderer;

/**
 * A tree cell factory using a {@link DataRenderer}.
 *
 * @author puce
 */
public class RenderedTreeCellFactory<T> implements Callback<TreeView<T>, TreeCell<T>> {

    private final Map<Class<?>, DataRenderer<?>> dataRenderers = new HashMap<>();

    public <E extends T> DataRenderer<? super E> registerDataRenderer(Class<E> type, DataRenderer<? super E> dataRenderer) {
        return (DataRenderer<? super E>) dataRenderers.put(type, dataRenderer);
    }

    public <E extends T> DataRenderer<? super E> unregisterDataRenderer(Class<E> type) {
        return (DataRenderer<? super E>) dataRenderers.remove(type);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public TreeCell<T> call(TreeView<T> param) {
        return new RenderedTreeCell<>(dataRenderers); // TODO: create a new one or cache it?
    }

    private static class RenderedTreeCell<T> extends TreeCell<T> {

        private final Map<Class<?>, DataRenderer<?>> dataRenderers;
        private final DataRenderer<Object> defaultDataRenderer = new ObjectRenderer();

        public RenderedTreeCell(Map<Class<?>, DataRenderer<?>> dataRenderers) {
            this.dataRenderers = dataRenderers;
        }

        @Override
        protected void updateItem(T item, boolean empty) {
            if (getItem() != null && !isEmpty()) {
                DataRenderer<? super T> oldItemdataRenderer = getDataRenderer(getItem().getClass());
                LabeledUtils.unconfigure(this, oldItemdataRenderer);
            }
            super.updateItem(item, empty);
            if (item != null && !empty) {
                DataRenderer<? super T> dataRenderer = getDataRenderer(item.getClass());
                LabeledUtils.configure(this, dataRenderer, item);
            }
//        setText(cellRenderer.getText(item));
//        setGraphic(cellRenderer.getGraphic(item));
            //setGraphic(new Text("@"));
            //setTextAlignment(cellRenderer.getTextAlignment());
            //setStyle("-fx-background-color: blue, red; -fx-background-insets: 2, 5;");
            //setAlignment(Pos.CENTER_RIGHT);
//        getStyleClass().addAll(cellRenderer.getStyleClass(item));
        }

        private DataRenderer<? super T> getDataRenderer(Class<?> type) {
            DataRenderer<?> dataRenderer = null;
            for (Class<?> t = type; dataRenderer == null && t != null; t = t.getSuperclass()) {
                if (dataRenderers.containsKey(t)) {
                    dataRenderer = dataRenderers.get(t);
                }
            }
            if (dataRenderer == null) {
                List<Class<?>> interfaces = ClassUtils.getAllInterfaces(type);
                for (Class<?> t : interfaces) {
                    if (dataRenderers.containsKey(t)) {
                        dataRenderer = dataRenderers.get(t);
                        break;
                    }
                }
            }
            // use the default ObjectRenderer only if no interface renderer could be found either
            return dataRenderer != null ? (DataRenderer<? super T>) dataRenderer : defaultDataRenderer;
        }
    }

}
