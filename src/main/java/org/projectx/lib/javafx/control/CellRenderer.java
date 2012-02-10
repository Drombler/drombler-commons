/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.control;

import java.util.Locale;
import javafx.scene.Node;
import javafx.scene.text.TextAlignment;

/**
 * Note: more methods might be added
 *
 * @author puce
 */
public interface CellRenderer<T> {

//    StringProperty displayStringProperty();
//
//    ObjectProperty<TextAlignment> textAlignmentProperty();
//
//    ObjectProperty<Node> graphicProperty();
    String getText(T item); // TODO: is it possible to set the locale of a node? if yes, pass it as parameter.

    TextAlignment getTextAlignment();

    Node getGraphic(T item);// TODO: is it possible to set the locale of a node? if yes, pass it as parameter.
}
