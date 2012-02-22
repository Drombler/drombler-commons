/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.scene.control;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

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
}
