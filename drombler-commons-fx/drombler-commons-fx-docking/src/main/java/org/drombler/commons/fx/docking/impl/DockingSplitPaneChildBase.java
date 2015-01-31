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
package org.drombler.commons.fx.docking.impl;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import org.drombler.commons.client.docking.LayoutConstraintsDescriptor;

/**
 *
 * @author puce
 */
// TODO: consider to implement Positionable
public abstract class DockingSplitPaneChildBase extends Control {

    private final ObjectProperty<DockingSplitPane> parentSplitPane = new SimpleObjectProperty<>(this,
            "parentSplitPane", null);
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

    public abstract LayoutConstraintsDescriptor getLayoutConstraints();

    public abstract void updateLayoutConstraints();
}
