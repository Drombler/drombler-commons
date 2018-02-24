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
package org.drombler.commons.fx.scene.control;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.util.Callback;
import org.drombler.commons.fx.scene.renderer.DataRenderer;

/**
 * A list cell factory using a {@link DataRenderer}.
 *
 * @author puce
 */
public class RenderedTreeCellFactory<T> implements Callback<TreeView<T>, TreeCell<T>> {

    private final DataRenderer<? super T> dataRenderer;

    /**
     * Creates a new instance of this class.
     *
     * @param dataRenderer a {@link DataRenderer}
     */
    public RenderedTreeCellFactory(DataRenderer<? super T> dataRenderer) {
        this.dataRenderer = dataRenderer;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public TreeCell<T> call(TreeView<T> p) {
        return new RenderedTreeCell<>(dataRenderer); // TODO: create a new one or cache it?
    }

    private static class RenderedTreeCell<T> extends TreeCell<T> {

        private final DataRenderer<? super T> dataRenderer;

        public RenderedTreeCell(DataRenderer<? super T> dataRenderer) {
            this.dataRenderer = dataRenderer;
        }

        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                LabeledUtils.unconfigure(this, dataRenderer);
            } else {
                LabeledUtils.configure(this, dataRenderer, item);
//        setText(cellRenderer.getText(item));
//        setGraphic(cellRenderer.getGraphic(item));
                //setGraphic(new Text("@"));
                //setTextAlignment(cellRenderer.getTextAlignment());
                //setStyle("-fx-background-color: blue, red; -fx-background-insets: 2, 5;");
                //setAlignment(Pos.CENTER_RIGHT);
//        getStyleClass().addAll(cellRenderer.getStyleClass(item));
            }
        }
    }
}
