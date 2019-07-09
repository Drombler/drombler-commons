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
 * Copyright 2015 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.docking.fx.impl.skin;

import java.beans.PropertyChangeListener;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Region;
import org.drombler.commons.docking.LayoutConstraintsDescriptor;
import static org.drombler.commons.docking.LayoutConstraintsDescriptor.PREF_HEIGHT_PROPERTY_NAME;
import static org.drombler.commons.docking.LayoutConstraintsDescriptor.PREF_WIDTH_PROPERTY_NAME;
import org.drombler.commons.docking.fx.impl.DockingSplitPane;
import org.drombler.commons.docking.fx.impl.DockingSplitPaneChildBase;
import org.drombler.commons.fx.scene.layout.RegionDimension;

/**
 *
 * @author puce
 */
public class DockingSplitPaneChildPreferencesManager implements AutoCloseable {

    private final ListChangeListener<DockingSplitPaneChildBase> dockingSplitPaneChildrenListener = change -> {
        while (change.next()) {
            if (change.wasAdded()) {
                configureChildren(change.getAddedSubList(), true);
            }
            if (change.wasRemoved()) {
                change.getRemoved().forEach(child -> removeChildListeners(child));
            }
        }
    };

    private final ChangeListener<LayoutConstraintsDescriptor> layoutConstraintsChangeListener;
    private final PropertyChangeListener layoutConstraintsDimensionChangeListener;
    private final DockingSplitPane dockingSplitPane;

    public DockingSplitPaneChildPreferencesManager(DockingSplitPane dockingSplitPane) {
        this.dockingSplitPane = dockingSplitPane;
        this.dockingSplitPane.getDockingSplitPaneChildren().addListener(dockingSplitPaneChildrenListener);
        layoutConstraintsChangeListener = (observable, oldValue, newValue)
                -> configureChildren(this.dockingSplitPane.getDockingSplitPaneChildren(), false);
        layoutConstraintsDimensionChangeListener = evt
                -> configureChildren(this.dockingSplitPane.getDockingSplitPaneChildren(), false);
        configureChildren(this.dockingSplitPane.getDockingSplitPaneChildren(), true);

    }

    private void configureChildren(List<? extends DockingSplitPaneChildBase> children, boolean addListeners) {
        children.forEach(child -> configureChild(child, addListeners));
    }

    private void configureChild(DockingSplitPaneChildBase child, boolean addListeners) {
        LayoutConstraintsDescriptor layoutConstraints = child.getLayoutConstraints();
        if (dockingSplitPane.isHorizontal()) {
            configureChild(child, RegionDimension.WIDTH, layoutConstraints.getPrefWidth(), addListeners);
        } else {
            configureChild(child, RegionDimension.HEIGHT, layoutConstraints.getPrefHeight(), addListeners);
        }
    }

    private void configureChild(DockingSplitPaneChildBase child, RegionDimension dimension, double prefSize,
            boolean addListeners) {
        if (LayoutConstraintsDescriptor.isPreferred(prefSize)) {
            dimension.setPrefSize(child, prefSize);
//            dimension.setMinSize(child, prefSize);
            SplitPane.setResizableWithParent(child, Boolean.FALSE);
        } else {
            dimension.setPrefSize(child, Region.USE_COMPUTED_SIZE);
            SplitPane.setResizableWithParent(child, Boolean.TRUE);
        }
        dimension.setMaxSize(child, Double.MAX_VALUE);
        if (addListeners) {
            addChildListeners(child);
        }
    }

    private void addChildListeners(DockingSplitPaneChildBase child) {
        child.layoutConstraintsProperty().addListener(layoutConstraintsChangeListener);
        child.getLayoutConstraints().addPropertyChangeListener(PREF_WIDTH_PROPERTY_NAME, layoutConstraintsDimensionChangeListener);
        child.getLayoutConstraints().addPropertyChangeListener(PREF_HEIGHT_PROPERTY_NAME, layoutConstraintsDimensionChangeListener);
    }

    private void removeChildListeners(DockingSplitPaneChildBase child) {
        child.layoutConstraintsProperty().removeListener(layoutConstraintsChangeListener);
        child.getLayoutConstraints().removePropertyChangeListener(PREF_WIDTH_PROPERTY_NAME, layoutConstraintsDimensionChangeListener);
        child.getLayoutConstraints().removePropertyChangeListener(PREF_HEIGHT_PROPERTY_NAME, layoutConstraintsDimensionChangeListener);
    }

    @Override
    public void close() {
        dockingSplitPane.getDockingSplitPaneChildren().forEach(child -> removeChildListeners(child));
    }

}
