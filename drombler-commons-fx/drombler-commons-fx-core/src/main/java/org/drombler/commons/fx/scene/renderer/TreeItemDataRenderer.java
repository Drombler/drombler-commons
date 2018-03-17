/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2018 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.fx.scene.renderer;

import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;

/**
 *
 * @author puce
 * @param <T>
 */
public class TreeItemDataRenderer<T> implements DataRenderer<TreeItem<T>> {

    /**
     * The {@link DataRenderer} to render the value of the {@link TreeItem}..
     */
    private final ObjectProperty<DataRenderer<? super T>> dataRenderer = new SimpleObjectProperty<>(this, "dataRenderer");

    public TreeItemDataRenderer() {
    }

    public TreeItemDataRenderer(DataRenderer<? super T> dataRenderer) {
        setDataRenderer(dataRenderer);
    }

    @Override
    public String getText(TreeItem<T> item) {
        return getDataRenderer().getText(item.getValue());
    }

    @Override
    public Node getGraphic(TreeItem<T> item) {
        return getDataRenderer().getGraphic(item.getValue());
    }

    @Override
    public Tooltip getTooltip(TreeItem<T> item) {
        return getDataRenderer().getTooltip(item.getValue());
    }

    @Override
    public List<String> getStyleClass(TreeItem<T> item) {
        return getDataRenderer().getStyleClass(item.getValue());
    }

    @Override
    public List<String> getStyleClass() {
        return getDataRenderer().getStyleClass();
    }

    public final DataRenderer<? super T> getDataRenderer() {
        return dataRenderer.get();
    }

    public final void setDataRenderer(DataRenderer<? super T> dataRenderer) {
        this.dataRenderer.set(dataRenderer);
    }

    public ObjectProperty<DataRenderer<? super T>> dataRendererProperty() {
        return dataRenderer;
    }
}
