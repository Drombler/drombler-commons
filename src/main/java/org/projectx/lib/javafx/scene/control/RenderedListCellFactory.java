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

    private final CellRenderer<? super T> cellRenderer;

    public RenderedListCellFactory(CellRenderer<? super T> cellRenderer) {
        this.cellRenderer = cellRenderer;
    }

    @Override
    public ListCell<T> call(ListView<T> p) {
        return new RenderedListCell<>(cellRenderer); // TODO: create a new one or cache it?
    }
}
