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
package org.drombler.commons.docking.fx.impl;

import java.util.Objects;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectPropertyBase;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import org.drombler.commons.docking.LayoutConstraintsDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author puce
 */
// TODO: consider to implement Positionable
public abstract class DockingSplitPaneChildBase extends Control {

    private static final Logger LOG = LoggerFactory.getLogger(DockingSplitPaneChildBase.class);

    private final ObjectProperty<DockingSplitPane> parentSplitPane = new SimpleObjectProperty<>(this,
            "parentSplitPane", null);
    private final LayoutConstraintsProperty layoutConstraints = new LayoutConstraintsProperty();
    /**
     * Flag that indicates if this child is a {@link DockingSplitPane}.
     */
    private final boolean splitPane;

    public DockingSplitPaneChildBase(boolean splitPane) {
        this.splitPane = splitPane;
        setFocusTraversable(false);
    }

    public DockingSplitPane getParentSplitPane() {
        return parentSplitPane.get();
    }

    public ObjectProperty<DockingSplitPane> parentSplitPaneProperty() {
        return parentSplitPane;
    }

    public void setParentSplitPane(DockingSplitPane parentSplitPane) {
        this.parentSplitPane.set(parentSplitPane);
    }

    public boolean isSplitPane() {
        return splitPane;
    }

    public final LayoutConstraintsDescriptor getLayoutConstraints() {
        return layoutConstraintsProperty().get();
    }

    protected void setLayoutConstraints(LayoutConstraintsDescriptor layoutConstraints) {
        this.layoutConstraints.set(layoutConstraints);
        // TODO: good?
        requestParentLayout();
    }

    public ReadOnlyObjectProperty<LayoutConstraintsDescriptor> layoutConstraintsProperty() {
        return layoutConstraints;
    }

    public abstract void updateLayoutConstraints();

    private class LayoutConstraintsProperty extends ReadOnlyObjectPropertyBase<LayoutConstraintsDescriptor> {

        private LayoutConstraintsDescriptor layoutConstraints = null;

        @Override
        public final LayoutConstraintsDescriptor get() {
            return layoutConstraints;
        }

        private void set(LayoutConstraintsDescriptor newValue) {
            if (!Objects.equals(layoutConstraints, newValue)) {
                LOG.debug("{}: layoutConstraints changed! old: {} -> new: {}", getBean(), layoutConstraints, newValue);
                layoutConstraints = newValue;
                fireValueChangedEvent();
            }
        }

        @Override
        public Object getBean() {
            return DockingSplitPaneChildBase.this;
        }

        @Override
        public String getName() {
            return "layoutConstraints";
        }
    }
}
