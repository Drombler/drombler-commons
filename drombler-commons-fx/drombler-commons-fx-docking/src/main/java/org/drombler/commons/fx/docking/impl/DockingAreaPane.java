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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SingleSelectionModel;
import org.drombler.commons.client.docking.LayoutConstraintsDescriptor;
import org.drombler.commons.client.docking.spi.DockingArea;
import org.drombler.commons.client.docking.spi.DockingAreaManager;
import org.drombler.commons.client.docking.spi.ShortPathPart;
import org.drombler.commons.fx.docking.FXDockableEntry;
import org.drombler.commons.fx.docking.impl.skin.Stylesheets;
import org.drombler.commons.fx.scene.control.ListSingleSelectionModel;
import org.softsmithy.lib.util.PositionableAdapter;
import org.softsmithy.lib.util.Positionables;

/**
 *
 * @author puce
 */
public class DockingAreaPane extends DockingSplitPaneChildBase implements DockingArea<DockingAreaPane> {

    private static final String DEFAULT_STYLE_CLASS = "docking-area-pane";
    /**
     * The area ID.
     */
    private final String areaId;
    private final ObservableList<PositionableAdapter<FXDockableEntry>> dockables = FXCollections.observableArrayList();
    private final ObservableList<PositionableAdapter<FXDockableEntry>> unmodifiableDockables = FXCollections.
            unmodifiableObservableList(dockables);
    private final Set<FXDockableEntry> dockableSet = new HashSet<>();
    private final int position;
    /**
     * Flag if the space for this docking area should be preserved, if it's empty, or if it's space should be freed.
     */
    private final boolean permanent;
    private DockingAreaManager<DockingAreaPane> parentManager;
    /**
     * Flag if this DockingArea has been added to a {@link DockingSplitPane} already.
     */
    private final BooleanProperty visualized = new SimpleBooleanProperty(this, "visualized", false);
    private final ObjectProperty<SingleSelectionModel<PositionableAdapter<FXDockableEntry>>> selectionModel
            = new SimpleObjectProperty<>(this, "singleSelectionModel", new ListSingleSelectionModel<>(dockables));

    public DockingAreaPane(String areaId, int position, boolean permanent, LayoutConstraintsDescriptor layoutConstraints) {
        super(false);
        this.areaId = areaId;
        this.position = position;
        this.permanent = permanent;
        setLayoutConstraints(layoutConstraints);
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);
    }

    @Override
    public String getUserAgentStylesheet() {
        return Stylesheets.getDefaultStylesheet();
    }

    /**
     * @return the areaId
     */
    public String getAreaId() {
        return areaId;
    }

    @Override
    public int getPosition() {
        return position;
    }

    public final boolean isVisualized() {
        return visualizedProperty().get();
    }

    public final void setVisualized(boolean visualized) {
        visualizedProperty().set(visualized);
    }

    public BooleanProperty visualizedProperty() {
        return visualized;
    }

    public final SingleSelectionModel<PositionableAdapter<FXDockableEntry>> getSelectionModel() {
        return singleModelProperty().get();
    }

    public final void setSelectionModel(SingleSelectionModel<PositionableAdapter<FXDockableEntry>> selectionModel) {
        singleModelProperty().set(selectionModel);
    }

    public final ObjectProperty<SingleSelectionModel<PositionableAdapter<FXDockableEntry>>> singleModelProperty() {
        return selectionModel;
    }

    public boolean addDockable(PositionableAdapter<FXDockableEntry> dockable) {
        if (!dockableSet.contains(dockable.getAdapted())) {
            dockableSet.add(dockable.getAdapted());
            int insertionPoint = Positionables.getInsertionPoint(dockables, dockable);
            dockables.add(insertionPoint, dockable);
            return true;
        } else {
            return false;
        }
    }

    public PositionableAdapter<FXDockableEntry> removeDockable(int index) {
        PositionableAdapter<FXDockableEntry> dockable = dockables.remove(index);
        dockableSet.remove(dockable.getAdapted());
        return dockable;
    }

    /**
     * @return the dockablePanes
     */
    public ObservableList<PositionableAdapter<FXDockableEntry>> getDockables() {
        return unmodifiableDockables;
    }

    /**
     * @return the permanent
     */
    public boolean isPermanent() {
        return permanent;
    }

    @Override
    public void setParentManager(DockingAreaManager<DockingAreaPane> parentManager) {
        this.parentManager = parentManager;
    }

    /**
     * Gets the short path.
     *
     * The short path is the path without any unnecessary split panes.
     *
     * @return a {@link List} of {@link ShortPathPart}s forming the short path.
     */
    public List<ShortPathPart> getShortPath() {
        return parentManager.getShortPath(this);
    }

    /**
     * Indicates if this DockingAreaPane is either {@link #isPermanent()} or non-empty.
     *
     * @return returns true if permanent or non-empty.
     */
    //TODO: good name?
    public boolean isVisualizable() {
        return isPermanent() || !getDockables().isEmpty();
    }

    // when the last dockable is beeing removed from the docking area, 
    // the docking area is not visualizable (empty) but still visualized 
    // (child of a DockingSplitPane; and thus has to be removed from it)
    //TODO: good name?
    @Override
    public boolean isVisual() {
        return isVisualizable() || isVisualized();
    }

    @Override
    public void updateLayoutConstraints() {
        if (LayoutConstraintsDescriptor.isPreferred(getLayoutConstraints().getPrefWidth())) {
            getLayoutConstraints().setPrefWidth(getWidth());
        }
        if (LayoutConstraintsDescriptor.isPreferred(getLayoutConstraints().getPrefHeight())) {
            getLayoutConstraints().setPrefHeight(getHeight());
        }
    }

    @Override
    public String toString() {
        return DockingAreaPane.class.getSimpleName() + "[areaId=" + areaId
                + ", position=" + position + ", permanent=" + permanent
                + ", visuablizable=" + isVisualizable()
                + ", visualized=" + isVisualized() + "]";
    }

}
