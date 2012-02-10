/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.control;

import javafx.scene.control.ListCell;

/**
 *
 * @author puce
 */
public class RenderedListCell<T> extends ListCell<T> {

    private final CellRenderer<? super T> cellRenderer;

    public RenderedListCell(CellRenderer<? super T> cellRenderer) {
        this.cellRenderer = cellRenderer;
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        setText(cellRenderer.getText(item));
        setGraphic(cellRenderer.getGraphic(item));
        setTextAlignment(cellRenderer.getTextAlignment());
    }
}
