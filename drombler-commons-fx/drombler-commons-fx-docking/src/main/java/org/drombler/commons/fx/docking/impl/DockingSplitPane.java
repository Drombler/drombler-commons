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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectPropertyBase;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import org.drombler.commons.client.docking.LayoutConstraintsDescriptor;
import org.drombler.commons.client.docking.spi.ShortPathPart;
import org.drombler.commons.client.docking.spi.SplitLevel;
import org.drombler.commons.fx.docking.impl.skin.Stylesheets;
import org.drombler.commons.fx.geometry.OrientationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softsmithy.lib.util.Positionable;
import org.softsmithy.lib.util.PositionableAdapter;
import org.softsmithy.lib.util.PositionableComparator;
import org.softsmithy.lib.util.Positionables;

/**
 *
 * @author puce
 */
public class DockingSplitPane extends DockingSplitPaneChildBase {

    private static final Logger LOG = LoggerFactory.getLogger(DockingSplitPane.class);

    private static final String DEFAULT_STYLE_CLASS = "docking-split-pane";
    private final int position;

    private final ObservableList<DockingSplitPaneChildBase> dockingSplitPaneChildren = FXCollections.
            observableArrayList();
    private final ObservableList<DockingSplitPaneChildBase> unmodifiableDockingSplitPaneChildren = FXCollections.
            unmodifiableObservableList(dockingSplitPaneChildren);

//    private final ChangeListener<LayoutConstraintsDescriptor> layoutConstraintsListener
//            = (observable, oldValue, newValue) -> recalculateLayoutConstraints();
    private final DockingSplitPaneManager manager;

    public DockingSplitPane(int position, int level, SplitLevel actualLevel) {
        super(true);
        this.position = position;
        this.manager = new DockingSplitPaneManager(level, actualLevel);

        getStyleClass().setAll(DEFAULT_STYLE_CLASS);

        // TODO: correct?
//        dockingSplitPaneChildren.addListener(
//                (ListChangeListener.Change<? extends DockingSplitPaneChildBase> change) -> {
//                    while (change.next()) {
//                        if (change.wasAdded()) {
//                            change.getAddedSubList().forEach(child -> addChildListeners(child));
//                        }
//                        if (change.wasRemoved()) {
//                            change.getRemoved().forEach(child -> removeChildListeners(child));
//                        }
//                    }
//                    recalculateLayoutConstraints();
//                });
        dockingSplitPaneChildren.addListener(
                (ListChangeListener.Change<? extends DockingSplitPaneChildBase> c) -> recalculateLayoutConstraints());
        recalculateLayoutConstraints();

    }

    @Override
    protected String getUserAgentStylesheet() {
        return Stylesheets.getDefaultStylesheet();
    }

//    @Override
//    public void requestLayout() {
//        LOG.debug("{}: requestLayout...", toString());
//        super.requestLayout(); //To change body of generated methods, choose Tools | Templates.
//    }
    public final Orientation getOrientation() {
        return orientationProperty().get();
    }

    public ReadOnlyObjectProperty<Orientation> orientationProperty() {
        return manager.orientationProperty();
    }

    public boolean isHorizontal() {
        return getOrientation().equals(Orientation.HORIZONTAL);
    }

    /**
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    public int getLevel() {
        return manager.getLevel();
    }

    /**
     * @return the actualLevel
     */
    public int getActualLevel() {
        return manager.getActualLevel();
    }

    /**
     * @return the children
     */
    public ObservableList<DockingSplitPaneChildBase> getDockingSplitPaneChildren() {
        return unmodifiableDockingSplitPaneChildren;
    }
//
//    private void addChildListeners(DockingSplitPaneChildBase child) {
//        if (child instanceof DockingSplitPane) {
//            ((DockingSplitPane) child).layoutConstraintsProperty().addListener(layoutConstraintsListener);
//        }
//    }
//
//    private void removeChildListeners(DockingSplitPaneChildBase child) {
//        if (child instanceof DockingSplitPane) {
//            ((DockingSplitPane) child).layoutConstraintsProperty().removeListener(layoutConstraintsListener);
//        }
//    }

    public void addDockingArea(DockingAreaPane dockingArea) {
        if (dockingArea.isVisualizable()) {
            manager.addDockingArea(dockingArea);
        }
    }

    public void removeDockingArea(DockingAreaPane dockingArea) {
        if (dockingArea.isVisualized()) {
            manager.removeDockingArea(dockingArea);
        }
    }

    private void recalculateLayoutConstraints() {
        double prefWidth = 0;
        double prefHeight = 0;
        for (DockingSplitPaneChildBase child : dockingSplitPaneChildren) {
            LayoutConstraintsDescriptor childLayoutConstraints = child.getLayoutConstraints();
            if (LayoutConstraintsDescriptor.isPreferred(prefWidth)
                    && (LayoutConstraintsDescriptor.isFlexible(childLayoutConstraints.getPrefWidth())
                    || childLayoutConstraints.getPrefWidth() > prefWidth)) {
                prefWidth = childLayoutConstraints.getPrefWidth();
            }
            if (LayoutConstraintsDescriptor.isPreferred(prefHeight)
                    && (LayoutConstraintsDescriptor.isFlexible(childLayoutConstraints.getPrefHeight())
                    || childLayoutConstraints.getPrefHeight() > prefHeight)) {
                prefHeight = childLayoutConstraints.getPrefHeight();

            }
            if (LayoutConstraintsDescriptor.isFlexible(prefWidth) && LayoutConstraintsDescriptor.isFlexible(prefHeight)) {
                break;
            }
        }
        final LayoutConstraintsDescriptor layoutConstraintsDescriptor
                = LayoutConstraintsDescriptor.getLayoutConstraints(prefWidth, prefHeight);
        setLayoutConstraints(layoutConstraintsDescriptor);
    }

    @Override
    public String toString() {
        return "DockingSplitPane[position=" + position + ", level=" + getLevel() + ", actualLevel=" + getActualLevel() + ", orientation=" + getOrientation() + "]";
    }

    @Override
    public void updateLayoutConstraints() {
        dockingSplitPaneChildren.forEach(dockingSplitPaneChild -> dockingSplitPaneChild.updateLayoutConstraints());
        recalculateLayoutConstraints();
    }

    public class DockingSplitPaneManager {

        private final Map<String, DockingSplitPane> areaIdsInSplitPane = new HashMap<>();
        private final Map<Integer, DockingSplitPane> splitPanes = new HashMap<>();
        private final Map<String, PositionableAdapter<DockingAreaPane>> areaIdsToAreaPaneMap = new HashMap<>();
        private final Map<Integer, PositionableAdapter<DockingAreaPane>> areaPanes = new HashMap<>(); // TODO: PositionableAdapter needed?
        private final List<Positionable> positionableChildren = new ArrayList<>();
        private final ObservableList<DockingSplitPaneChildBase> dockingSplitPaneChildren = FXCollections.
                observableArrayList();
        private final OrientationProperty orientation = new OrientationProperty();
        private final int level;
        private int actualLevel;

        public DockingSplitPaneManager(int level, SplitLevel actualLevel) {
            this.level = level;
            adjust(actualLevel);
        }

        public void addDockingArea(DockingAreaPane dockingArea) {
            //            System.out.println(DockingSplitPane.class.getName() + ": adding docking area: " + dockingArea.getAreaId());
            List<DockingAreaPane> removedDockingAreas = new ArrayList<>();
            addDockingArea(dockingArea.getShortPath(), dockingArea, removedDockingAreas);

            reAddDockingAreas(removedDockingAreas);

            updateSplitPaneChildren();
        }

        private void addDockingArea(List<ShortPathPart> path, DockingAreaPane dockingAreaPane,
                List<DockingAreaPane> removedDockingAreas) {
            if (level >= path.size()) {
                throw new IllegalStateException("Level is too high! Level=" + level + ", areaId="
                        + dockingAreaPane.getAreaId() + ", path=" + path);
            }
            ShortPathPart pathPart = path.get(level);
            adjust(pathPart.getInActualLevel(), removedDockingAreas);
            if (!isLastPathPart(path)) {
                int childLevel = level + 1;
                DockingSplitPane splitPane = getSplitPane(pathPart.getPosition(), childLevel,
                        path.get(childLevel).getInActualLevel(), removedDockingAreas);
                // recursion
                splitPane.manager.addDockingArea(path, dockingAreaPane, removedDockingAreas);
                areaIdsInSplitPane.put(dockingAreaPane.getAreaId(), splitPane);
            } else {
                addDockingArea(pathPart.getPosition(), dockingAreaPane);
            }
        }

        private void adjust(SplitLevel splitLevel, List<DockingAreaPane> removedDockingAreas) {
            if (actualLevel != splitLevel.getLevel()) {
                if (isEmpty()) {
                    adjust(splitLevel);
                } else {
                    removeAllDockingAreas(removedDockingAreas);
                    adjust(splitLevel);
                }
            }
        }

        private void adjust(SplitLevel splitLevel) {
            actualLevel = splitLevel.getLevel();
            setOrientation(OrientationUtils.getOrientation(splitLevel.getOrientation()));
        }

        private DockingSplitPane getSplitPane(int position, int childLevel, SplitLevel inActualLevel,
                List<DockingAreaPane> removedDockingAreas) {
            if (!splitPanes.containsKey(position)) {
                if (areaPanes.containsKey(position)) {
                    removeDockingArea(position, removedDockingAreas);
                }
                DockingSplitPane splitPane = new DockingSplitPane(position, childLevel, inActualLevel);
                addSplitPane(position, splitPane);
            }
            return splitPanes.get(position);
        }

        private void removeDockingArea(int position, List<DockingAreaPane> removedDockingAreas) {
            PositionableAdapter<DockingAreaPane> areaPane = areaPanes.get(position);
            removeDockingArea(areaPane, removedDockingAreas);
        }

        private void removeDockingArea(PositionableAdapter<DockingAreaPane> dockingArea,
                List<DockingAreaPane> removedAreaPanes) {
            removeDockingAreaOnly(dockingArea);
            removedAreaPanes.add(dockingArea.getAdapted());
        }

        private void addDockingArea(int position, DockingAreaPane dockingArea) {
            PositionableAdapter<DockingAreaPane> dockingAreaAdapter = new PositionableAdapter<>(dockingArea, position);
            // TODO: handle situatation if another child has the same position
            areaPanes.put(position, dockingAreaAdapter);
            areaIdsToAreaPaneMap.put(dockingArea.getAreaId(), dockingAreaAdapter);
            addChild(position, dockingArea);
            dockingArea.setVisualized(true);
//        System.out.println(DockingSplitPane.class.getName() + ": added docking area: " + dockingArea.getAreaId());
        }

        private void addSplitPane(int position, DockingSplitPane splitPane) {
            splitPanes.put(position, splitPane);
            addChild(position, splitPane);
        }

        private void addChild(int position, DockingSplitPaneChildBase child) {
            child.setParentSplitPane(DockingSplitPane.this);
            PositionableAdapter<DockingSplitPaneChildBase> positionableChild
                    = new PositionableAdapter<>(child, position);
            int insertionPoint = Positionables.getInsertionPoint(positionableChildren, positionableChild);
            positionableChildren.add(insertionPoint, positionableChild);
            dockingSplitPaneChildren.add(insertionPoint, child);
        }

        private void reAddDockingAreas(List<DockingAreaPane> removedDockingAreas) throws IllegalStateException {
            for (DockingAreaPane removedDockingArea : removedDockingAreas) {
                List<DockingAreaPane> areas = new ArrayList<>();
                addDockingArea(removedDockingArea.getShortPath(), removedDockingArea, areas);
                if (!areas.isEmpty()) {
                    // TODO: should not happen (?) -> log?
                    throw new IllegalStateException();
                }
            }
            removeEmptySplitPanes(); // TODO needed?
        }

        private void removeEmptySplitPanes() {
            List<DockingSplitPane> dockingSplitPanes = new ArrayList<>(splitPanes.values()); // avoid ConcurrentModificationException
            dockingSplitPanes.stream().
                    map(splitPane -> {
                        // recursion
                        splitPane.manager.removeEmptySplitPanes();
                        return splitPane;
                    }).
                    filter(splitPane -> !splitPane.manager.containsAnyDockingAreas()).
                    forEach(splitPane -> removeSplitPane(splitPane));
        }

        public void removeDockingArea(DockingAreaPane dockingArea) {
            LOG.debug("{}: Start removing DockingArea: {}", DockingSplitPane.this, dockingArea);

            List<DockingAreaPane> removedAreaPanes = new ArrayList<>();
            removeDockingArea(dockingArea, removedAreaPanes);

            reAddDockingAreas(removedAreaPanes);

            updateSplitPaneChildren();

            LOG.debug("{}: End removing DockingArea: {}", DockingSplitPane.this, dockingArea);
        }

        private void removeDockingArea(DockingAreaPane dockingArea, List<DockingAreaPane> allRemovedAreaPanes) {
            LOG.debug("{}: remove DockingArea: {}", DockingSplitPane.this, dockingArea);
            if (areaIdsInSplitPane.containsKey(dockingArea.getAreaId())) {
                DockingSplitPane splitPane = areaIdsInSplitPane.remove(dockingArea.getAreaId());
                List<DockingAreaPane> removedAreaPanes = new ArrayList<>();
                // recursion
                splitPane.manager.removeDockingArea(dockingArea, removedAreaPanes);
                allRemovedAreaPanes.addAll(removedAreaPanes);

                removedAreaPanes.forEach((removedAreaPane) -> areaIdsInSplitPane.remove(removedAreaPane.getAreaId()));

                if (!splitPane.manager.containsAnyDockingAreas()) {
                    removeSplitPane(splitPane);
                }
            } else {
                if (!areaIdsToAreaPaneMap.containsKey(dockingArea.getAreaId())) {
                    throw new IllegalStateException("No area pane with areaId: " + dockingArea.getAreaId());
                }

                PositionableAdapter<DockingAreaPane> dockingAreaAdapter = areaIdsToAreaPaneMap.get(dockingArea.
                        getAreaId());
                removeDockingAreaOnly(dockingAreaAdapter);

                if (dockingSplitPaneChildren.size() == 1) {
                    // short paths might have changed -> re-add
                    removeAllDockingAreas(allRemovedAreaPanes);
                }
            }

            if (isRoot() && isEmpty()) {
                adjust(SplitLevel.ROOT);
            }
        }

        private void removeDockingAreaOnly(PositionableAdapter<DockingAreaPane> dockingArea) {
            areaPanes.remove(dockingArea.getPosition());
            areaIdsToAreaPaneMap.remove(dockingArea.getAdapted().getAreaId());
            removeChild(dockingArea);
            dockingArea.getAdapted().setVisualized(false);
        }

        private void removeSplitPane(DockingSplitPane splitPane) {
            splitPanes.remove(splitPane.getPosition());
            removeChild(new PositionableAdapter<>(splitPane, splitPane.getPosition()));
        }

        private void removeChild(PositionableAdapter<? extends DockingSplitPaneChildBase> dockingSplitPaneChild) {
            dockingSplitPaneChild.getAdapted().setParentSplitPane(null);
            int index = Collections.binarySearch(positionableChildren, dockingSplitPaneChild,
                    new PositionableComparator());
            positionableChildren.remove(index);
            dockingSplitPaneChildren.remove(index);
        }

        private void removeAllDockingAreas(List<DockingAreaPane> removedDockingAreas) {
            new ArrayList<>(areaPanes.values()).
                    forEach((dockingArea) -> removeDockingArea(dockingArea, removedDockingAreas));
            new ArrayList<>(splitPanes.values()).forEach((splitPane) -> {
                // recursion
                splitPane.manager.removeAllDockingAreas(removedDockingAreas);
            });
            areaIdsInSplitPane.clear();
        }

        private boolean isRoot() {
            return getParentSplitPane() == null;
        }

        private boolean isLastPathPart(List<ShortPathPart> path) {
            return level == path.size() - 1;
        }

        /**
         * Checks if this DockingSplitPane contains any children.
         *
         * TODO: needed as public?
         *
         * @return true, if it contains no children, else false.
         */
        public boolean isEmpty() {
            return dockingSplitPaneChildren.isEmpty();
        }

        public final Orientation getOrientation() {
            return orientationProperty().get();
        }

        private void setOrientation(Orientation parentSplitPane) {
            orientation.set(parentSplitPane);
        }

        public ReadOnlyObjectProperty<Orientation> orientationProperty() {
            return orientation;
        }

        public int getLevel() {
            return level;
        }

        public int getActualLevel() {
            return actualLevel;
        }

        /**
         * Checks if this DockingSplitPane contains a DockingAreaPane or a child DockingSplitPane, which contains any
         * docking area.
         *
         *
         * @return if this DockingSplitPane directly or indirectly contains any DockingAreaPane, else false.
         */
        private boolean containsAnyDockingAreas() {
            boolean contains = !areaPanes.isEmpty();
            if (!contains) {
                for (DockingSplitPane splitPane : splitPanes.values()) {
                    // recursion
                    contains = splitPane.manager.containsAnyDockingAreas();
                    if (contains) {
                        break;
                    }
                }
            }
            return contains;
        }

        private void updateSplitPaneChildren() {
            LOG.debug("{}: update SplitPane children...", DockingSplitPane.this);
            splitPanes.values().forEach(splitPane -> splitPane.manager.updateSplitPaneChildren());
            DockingSplitPane.this.dockingSplitPaneChildren.setAll(dockingSplitPaneChildren);
        }

        private class OrientationProperty extends ReadOnlyObjectPropertyBase<Orientation> {

            private Orientation orientation = null;

            @Override
            public final Orientation get() {
                return orientation;
            }

            private void set(Orientation newValue) {
                if (!Objects.equals(orientation, newValue)) {
                    orientation = newValue;
                    fireValueChangedEvent();
                }
            }

            @Override
            public Object getBean() {
                return DockingSplitPaneManager.this;
            }

            @Override
            public String getName() {
                return "orientation";
            }
        }
    }

}
