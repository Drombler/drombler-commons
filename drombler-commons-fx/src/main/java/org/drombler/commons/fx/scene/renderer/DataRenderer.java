/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.commons.fx.scene.renderer;

import java.util.List;
import javafx.scene.Node;

/**
 * Note: more methods might be added
 *
 * @author puce
 */
public interface DataRenderer<T> {

//    StringProperty displayStringProperty();
//
//    ObjectProperty<TextAlignment> textAlignmentProperty();
//
//    ObjectProperty<Node> graphicProperty();
    String getText(T item); // TODO: is it possible to set the locale of a node? if yes, pass it as parameter.

    //TextAlignment getTextAlignment();

    Node getGraphic(T item);// TODO: is it possible to set the locale of a node? if yes, pass it as parameter.

    List<String> getStyleClass(T item);

    List<String> getStyleClass();
}
