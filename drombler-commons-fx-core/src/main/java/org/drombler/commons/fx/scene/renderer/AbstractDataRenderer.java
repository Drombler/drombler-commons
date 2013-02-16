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
