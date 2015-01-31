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
package org.drombler.commons.fx.docking.impl.skin;

import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Region;
import org.drombler.commons.client.docking.LayoutConstraintsDescriptor;
import org.drombler.commons.fx.docking.impl.DockingSplitPane;
import org.drombler.commons.fx.docking.impl.DockingSplitPaneChildBase;
import org.drombler.commons.fx.scene.layout.RegionDimension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author puce
 */
public class DividerPositionRecalculator implements AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(DividerPositionRecalculator.class);

    private static final String PREF_WIDTH = "prefWidth";
    private static final String PREF_HEIGHT = "prefHeight";

    private final DockingSplitPane dockingSplitPane;
    private final SplitPane splitPane;
    private final BooleanProperty adjusting = new SimpleBooleanProperty(this, "adjusting", false);

    private final ChangeListener<Object> sizeChangeListener = (ov, oldValue, newValue) -> {
        LOG.debug("ObservableValue: {}: old value: {}, new value: {}", ov, oldValue, newValue);
        recalculateDividerPositions();
    };

//    private final ListChangeListener<Node> splitPaneItemsListener = change -> {
//        LOG.debug("SplitPane items change: {}", change);
//        recalculateDividerPositions();
//    };
    private final ListChangeListener<DockingSplitPaneChildBase> dockingSplitPaneChildrenListener = change -> {
        while (change.next()) {
            if (change.wasAdded()) {
                configureChildren(change.getAddedSubList(), true);
            }
            if (change.wasRemoved()) {
                change.getRemoved().forEach(child -> removeChildListeners(child));
            }
        }
        LOG.debug("DockingSplitPane children change: {}", change);
        recalculateDividerPositions();
    };

    private final ChangeListener<LayoutConstraintsDescriptor> layoutConstraintsChangeListener;
    private final PropertyChangeListener layoutConstraintsDimensionChangeListener;

    public DividerPositionRecalculator(DockingSplitPane dockingSplitPane, SplitPane splitPane) {
        this.dockingSplitPane = dockingSplitPane;
        this.splitPane = splitPane;

//        this.splitPane.getItems().addListener(splitPaneItemsListener);
        this.dockingSplitPane.getDockingSplitPaneChildren().addListener(dockingSplitPaneChildrenListener);
        this.dockingSplitPane.widthProperty().addListener(sizeChangeListener);
        this.dockingSplitPane.heightProperty().addListener(sizeChangeListener);

        layoutConstraintsChangeListener = (observable, oldValue, newValue)
                -> configureChildren(this.dockingSplitPane.getDockingSplitPaneChildren(), false);
        layoutConstraintsDimensionChangeListener = evt
                -> configureChildren(this.dockingSplitPane.getDockingSplitPaneChildren(), false);
        configureChildren(this.dockingSplitPane.getDockingSplitPaneChildren(), true);
        this.dockingSplitPane.layoutConstraintsProperty().addListener(sizeChangeListener);
    }

    private void configureChildren(List<? extends DockingSplitPaneChildBase> children, boolean addListeners) {
        children.forEach(child -> configureChild(child, addListeners));
    }

    private void configureChild(DockingSplitPaneChildBase child, boolean addListeners) {
        LayoutConstraintsDescriptor layoutConstraints = child.getLayoutConstraints();
        if (isHorizontal()) {
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
        child.getLayoutConstraints().addPropertyChangeListener(PREF_WIDTH, layoutConstraintsDimensionChangeListener);
        child.getLayoutConstraints().addPropertyChangeListener(PREF_HEIGHT, layoutConstraintsDimensionChangeListener);
    }

    private void removeChildListeners(DockingSplitPaneChildBase child) {
        child.layoutConstraintsProperty().removeListener(layoutConstraintsChangeListener);
        child.getLayoutConstraints().removePropertyChangeListener(PREF_WIDTH, layoutConstraintsDimensionChangeListener);
        child.getLayoutConstraints().removePropertyChangeListener(PREF_HEIGHT, layoutConstraintsDimensionChangeListener);
    }

    public final boolean isAdjusting() {
        return adjustingProperty().get();
    }

    public final void setAdjusting(boolean active) {
        adjustingProperty().set(active);
    }

    public BooleanProperty adjustingProperty() {
        return adjusting;
    }

    public void recalculateDividerPositions() {
//        try {
//            throw new IllegalArgumentException();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        }
//        // TODO: good?
//        getSkinnable().requestLayout();
//        if (getSkinnable().getParent() != null) {
//            // TODO: good?
//            getSkinnable().getParent().requestLayout();
//        }
        recalculateDividerPositions2();
    }

    private void recalculateDividerPositions2() {
        LOG.debug("{}: inSync: {}, adjusting: {}", dockingSplitPane, isInSync(), isAdjusting());
        if (isInSync() && !isAdjusting()) {
            setAdjusting(true);
            if (isHorizontal()) {
                recalculateDividerPositionsHorizontal();
            } else {
                recalculateDividerPositionsVertical();
            }
            setAdjusting(false);
            LOG.debug("{}: adjusting finished!", dockingSplitPane);
        }
    }

    private boolean isHorizontal() {
        return dockingSplitPane.getOrientation().equals(Orientation.HORIZONTAL);
    }

    private boolean isInSync() {
        return dockingSplitPane.getDockingSplitPaneChildren().size() == splitPane.getItems().size();
    }

    private void recalculateDividerPositionsHorizontal() {
        double[] prefWidths = new double[dockingSplitPane.getDockingSplitPaneChildren().size()];
        double[] currentWidths = new double[dockingSplitPane.getDockingSplitPaneChildren().size()];

        int i = 0;
        for (DockingSplitPaneChildBase child : dockingSplitPane.getDockingSplitPaneChildren()) {
            LayoutConstraintsDescriptor layoutConstraints = child.getLayoutConstraints();
            prefWidths[i] = layoutConstraints.getPrefWidth();

            currentWidths[i] = child.getWidth();
            i++;
        }

        recalculateDividerPositions(prefWidths, currentWidths, dockingSplitPane.getWidth());
    }

    private void recalculateDividerPositionsVertical() {
        double[] prefHeights = new double[dockingSplitPane.getDockingSplitPaneChildren().size()];
        double[] currentHeights = new double[dockingSplitPane.getDockingSplitPaneChildren().size()];

        int i = 0;
        for (DockingSplitPaneChildBase child : dockingSplitPane.getDockingSplitPaneChildren()) {
            LayoutConstraintsDescriptor layoutConstraints = child.getLayoutConstraints();
            prefHeights[i] = layoutConstraints.getPrefHeight();

            currentHeights[i] = child.getHeight();
            i++;
        }
        recalculateDividerPositions(prefHeights, currentHeights, dockingSplitPane.getHeight());
    }

    private void recalculateDividerPositions(double[] prefs, double[] current, double parentSize) {
        LOG.debug("######################");
        LOG.debug(dockingSplitPane.toString());
        LOG.debug("Parent size: {}", parentSize);
        LOG.debug("Num children: {}", dockingSplitPane.getDockingSplitPaneChildren().size());
        LOG.debug("Num items: {}", splitPane.getItems().size());
        double[] relativeSizes = new double[prefs.length];
        double requiredRelativeSize = 0;
        int flexiblePositions = 0;
        if (parentSize > 0) {
            for (int i = 0; i < prefs.length; i++) {
                if (LayoutConstraintsDescriptor.isPreferred(prefs[i])) {
                    relativeSizes[i] = prefs[i] / parentSize;
                    requiredRelativeSize += relativeSizes[i];
                } else {
                    relativeSizes[i] = prefs[i];
                    flexiblePositions++;
                }
                LOG.debug("{}: pref: {} -> current: {} -> relative pref: {}", i, prefs[i], current[i],
                        relativeSizes[i]);
            }

            if (requiredRelativeSize > 1) {
                // TODO: shrink sizes
            }

            if (flexiblePositions > 0) {
                double flexibleRelativeSize = (1 - requiredRelativeSize) / flexiblePositions;
                for (int i = 0; i < prefs.length; i++) {
                    if (LayoutConstraintsDescriptor.isFlexible(prefs[i])) {
                        relativeSizes[i] = flexibleRelativeSize;
                    }
                }
            }

            double currentPosition = 0;
            for (int i = 0; i < relativeSizes.length - 1; i++) {
                if (relativeSizes[i] > 0) {
                    currentPosition += relativeSizes[i];
                    splitPane.setDividerPosition(i, currentPosition);
                    LOG.debug("Set divider position: {} to {}. Actual position: {}", i, currentPosition,
                            splitPane.getDividerPositions()[i]);
                }
            }
        }
        LOG.debug("Actual divider positions: {}", Arrays.toString(splitPane.getDividerPositions()));
        LOG.debug("######################");
        LOG.debug("                      ");
    }

    @Override
    public void close() {
        dockingSplitPane.getDockingSplitPaneChildren().removeListener(dockingSplitPaneChildrenListener);
        dockingSplitPane.widthProperty().removeListener(sizeChangeListener);
        dockingSplitPane.heightProperty().removeListener(sizeChangeListener);
        dockingSplitPane.layoutConstraintsProperty().removeListener(sizeChangeListener);
    }
}
