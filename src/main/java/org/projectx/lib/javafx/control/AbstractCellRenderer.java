/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.control;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author puce
 */
public abstract class AbstractCellRenderer<T> implements CellRenderer<T> {

    private final TextAlignment textAlignment;

    public AbstractCellRenderer() {
        this(TextAlignment.LEFT); // TODO: No "LEADING" in JavaFX?
    }

    public AbstractCellRenderer(TextAlignment textAlignment) {
        this.textAlignment = textAlignment;
    }

    @Override
    public TextAlignment getTextAlignment() {
        return textAlignment;
    }

    @Override
    public Node getGraphic(T item) {
        return null;
    }
//    private final StringProperty displayStringProperty = new SimpleStringProperty();
//    private final ObjectProperty<TextAlignment> textAlignmentProperty = new SimpleObjectProperty<>();
//    private final ObjectProperty<Node> graphicProperty = new SimpleObjectProperty<>();
//
//    @Override
//    public StringProperty displayStringProperty() {
//        return displayStringProperty;
//    }
//
//    @Override
//    public ObjectProperty<TextAlignment> textAlignmentProperty() {
//        return textAlignmentProperty;
//    }
//
//    @Override
//    public ObjectProperty<Node> graphicProperty() {
//        return graphicProperty;
//    }
}
