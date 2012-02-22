/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.scene.control;

import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.text.Text;

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
        LabeledUtils.configure(this, cellRenderer, item);
//        setText(cellRenderer.getText(item));
//        setGraphic(cellRenderer.getGraphic(item));
        //setGraphic(new Text("@"));
        //setTextAlignment(cellRenderer.getTextAlignment());
        //setStyle("-fx-background-color: blue, red; -fx-background-insets: 2, 5;");
        //setAlignment(Pos.CENTER_RIGHT);
//        getStyleClass().addAll(cellRenderer.getStyleClass(item));
    }
}
