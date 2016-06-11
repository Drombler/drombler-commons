/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (Sourceforge.net user: puce).
 * Copyright 2012 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.fx.scene.renderer;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;

/**
 * Note: more methods might be added
 *
 * @param <T> the type of the data to render
 * @author puce
 */
public interface DataRenderer<T> {

//    StringProperty displayStringProperty();
//
//    ObjectProperty<TextAlignment> textAlignmentProperty();
//
//    ObjectProperty<Node> graphicProperty();
    /**
     * Gets a text representation of the item.
     *
     * @param item the item to render
     * @return a text representation of the item
     */
    String getText(T item); // TODO: is it possible to set the locale of a node? if yes, pass it as parameter.

    //TextAlignment getTextAlignment();
    /**
     * Gets a graphical representation of the item.
     *
     * @param item the item to render
     * @return a graphical representation of the item
     */
    Node getGraphic(T item);// TODO: is it possible to set the locale of a node? if yes, pass it as parameter.

    Tooltip getTooltip(T item);

    /**
     * Gets a list of style classes for the specified item.
     *
     * @param item the item to render
     * @return a list of style classes for the specified item
     */
    List<String> getStyleClass(T item);

    /**
     * Gets all possible style classes for any item.
     *
     * E.g. if getStyleClass(a) returns ["foo"] and getStyleClass(b) returns
     * ["bar"], then this method should return ["foo", "bar"].
     *
     * @return all possible style classes for any item
     */
    List<String> getStyleClass();
}
