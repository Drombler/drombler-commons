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
package org.drombler.commons.fx.docking;

import javafx.beans.DefaultProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectPropertyBase;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Control;
import org.drombler.commons.client.docking.Dockable;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.Contexts;
import org.drombler.commons.fx.docking.impl.skin.Stylesheets;

/**
 * A container which can be docked in the Docking System.
 *
 * TODO: Should this class extend javafx.scene.layout.Pane instead of Control?
 *
 * Note: This class might be removed in the future!
 *
 * @author puce
 */
@DefaultProperty("content")
public class DockablePane extends Control implements Dockable {

    private static final String DEFAULT_STYLE_CLASS = "dockable-pane";
    /**
     * The title of this {@link Dockable}. It is used to represent this dockable e.g. in menus or tabs.
     */
    private final StringProperty title = new SimpleStringProperty(this, "titleProperty", "");
    /**
     * The graphic of this {@link Dockable}. It is used to represent this dockable e.g. in menus or tabs.
     */
    private final ObjectProperty<Node> graphic = new SimpleObjectProperty<>(this, "graphic", null);
    /**
     * The actual content of this {@link Dockable}.
     */
    private final ObjectProperty<Node> content = new SimpleObjectProperty<>(this, "content", null);
    /**
     * The local context of this {@link Dockable}.
     */
    private final ContextProperty context = new ContextProperty();

    public DockablePane() {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);
    }

    @Override
    protected String getUserAgentStylesheet() {
        return Stylesheets.getDefaultStylesheet();
    }

//    @Override
    public final String getTitle() {
        return titleProperty().get();
    }

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

    public final Node getContent() {
        return contentProperty().get();
    }

    public final void setContent(Node content) {
        contentProperty().set(content);
    }

    public ObjectProperty<Node> contentProperty() {
        return content;
    }

    @Override
    public Context getContext() {
        return contextProperty().get();
    }

    protected void setContext(Context context) {
        this.context.set(context);
    }

    public ReadOnlyObjectProperty<Context> contextProperty() {
        return context;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void requestActive() {
        requestFocus();
    }

//    protected final void load() throws IOException {
//        Node root = (Node) FXMLLoaders.load(this);
//        setContent(root);
//    }
    private class ContextProperty extends ReadOnlyObjectPropertyBase<Context> {

        private Context context = Contexts.emptyContext();

        @Override
        public final Context get() {
            return context;
        }

        private void set(Context newValue) {
            context = newValue;
            fireValueChangedEvent();
        }

        @Override
        public Object getBean() {
            return DockablePane.this;
        }

        @Override
        public String getName() {
            return "context";
        }
    }
}
