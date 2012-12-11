/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.commons.fx.scene.layout;

import javafx.beans.DefaultProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import org.drombler.commons.fx.scene.control.impl.skin.Stylesheets;

/**
 *
 * @author puce
 */
@DefaultProperty("content")
class ContentPane extends Pane {

    private static final String DEFAULT_STYLE_CLASS = "content-pane";
    private final ObjectProperty<Node> content = new SimpleObjectProperty<>(this, "content", null);


    public ContentPane() {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);
    }


    public Node getContent() {
        return content.get();
    }

    public void setContent(Node content) {
        this.content.set(content);
    }

    public ObjectProperty<Node> contentProperty() {
        return content;
    }

    
}
