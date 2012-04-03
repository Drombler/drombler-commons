/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.scene.control;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.projectx.lib.javafx.scene.renderer.DataRenderer;

/**
 *
 * @author puce
 */
public class RenderedListCellFactory<T> implements Callback<ListView<T>, ListCell<T>> {

    private final DataRenderer<? super T> dataRenderer;

    public RenderedListCellFactory(DataRenderer<? super T> dataRenderer) {
        this.dataRenderer = dataRenderer;
    }

    @Override
    public ListCell<T> call(ListView<T> p) {
        return new RenderedListCell<>(dataRenderer); // TODO: create a new one or cache it?
    }

    private static class RenderedListCell<T> extends ListCell<T> {

        private final DataRenderer<? super T> dataRenderer;

        public RenderedListCell(DataRenderer<? super T> dataRenderer) {
            this.dataRenderer = dataRenderer;
        }

        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            LabeledUtils.configure(this, dataRenderer, item);
//        setText(cellRenderer.getText(item));
//        setGraphic(cellRenderer.getGraphic(item));
            //setGraphic(new Text("@"));
            //setTextAlignment(cellRenderer.getTextAlignment());
            //setStyle("-fx-background-color: blue, red; -fx-background-insets: 2, 5;");
            //setAlignment(Pos.CENTER_RIGHT);
//        getStyleClass().addAll(cellRenderer.getStyleClass(item));
        }
    }
}
