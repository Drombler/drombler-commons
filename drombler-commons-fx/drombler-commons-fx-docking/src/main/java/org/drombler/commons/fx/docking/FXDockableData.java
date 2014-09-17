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
 * Copyright 2014 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.fx.docking;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import org.drombler.commons.client.docking.DockableData;
import org.drombler.commons.fx.scene.GraphicFactory;

/**
 *
 * @author puce
 */
public class FXDockableData implements DockableData {

//    private final ResourceBundle resourceBundle;
//
//    public FXDockableData(ResourceBundle resourceBundle) {
//        this.resourceBundle = resourceBundle;
//    }
//
//    /**
//     * @return the resourceBundle
//     */
//    public ResourceBundle getResourceBundle() {
//        return resourceBundle;
//    }
    /**
     * The title of this Dockable. It is used to represent this dockable e.g. in menus or tabs.
     */
    private final StringProperty title = new SimpleStringProperty(this, "titleProperty", "");
    /**
     * The graphic of this Dockable. It is used to represent this dockable e.g. in menus or tabs.
     */
    private final ObjectProperty<Node> graphic = new SimpleObjectProperty<>(this, "graphic", null);

    private final ObjectProperty<GraphicFactory> graphicFactory = new SimpleObjectProperty<>(this, "graphicFactory",
            null);

    /**
     * TODO: needed?
     */
    private final ObjectProperty<ContextMenu> contextMenu = new SimpleObjectProperty<>(this, "contextMenu",
            null);

//    @Override
    @Override
    public final String getTitle() {
        return titleProperty().get();
    }

    @Override
    public final void setTitle(String title) {
        titleProperty().set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public final Node getGraphic() {
        return graphicProperty().get();
    }

    public final void setGraphic(Node graphic) {
        graphicProperty().set(graphic);
    }

    public ObjectProperty<Node> graphicProperty() {
        return graphic;
    }

    public final GraphicFactory getGraphicFactory() {
        return graphicFactoryProperty().get();
    }

    public final void setGraphicFactory(GraphicFactory graphicFactory) {
        graphicFactoryProperty().set(graphicFactory);
    }

    public ObjectProperty<GraphicFactory> graphicFactoryProperty() {
        return graphicFactory;
    }

    public final ContextMenu getContextMenu() {
        return contextMenuProperty().get();
    }

    public final void setContextMenu(ContextMenu contextMenu) {
        contextMenuProperty().set(contextMenu);
    }

    public ObjectProperty<ContextMenu> contextMenuProperty() {
        return contextMenu;
    }

    @Override
    public String toString() {
        return "FXDockableData[title=" + getTitle() + "]";
    }

}
