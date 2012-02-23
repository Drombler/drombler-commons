/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.scene.renderer;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;

/**
 *
 * @author puce
 */
public abstract class AbstractDataRenderer<T> implements DataRenderer<T> {


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

    @Override
    public List<String> getStyleClass(T item) {
        return new ArrayList<>();
    }

    @Override
    public List<String> getStyleClass() {
        return new ArrayList<>();
    }
    
    
}
