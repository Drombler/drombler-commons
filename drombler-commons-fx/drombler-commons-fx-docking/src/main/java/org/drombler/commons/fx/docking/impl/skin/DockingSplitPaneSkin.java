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
package org.drombler.commons.fx.docking.impl.skin;

import java.util.Arrays;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.Orientation;
import javafx.scene.control.Control;
import javafx.scene.control.SkinBase;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Region;
import org.drombler.commons.client.docking.LayoutConstraintsDescriptor;
import org.drombler.commons.fx.docking.impl.DockingSplitPane;
import org.drombler.commons.fx.docking.impl.DockingSplitPaneChildBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author puce
 */
public class DockingSplitPaneSkin extends SkinBase<DockingSplitPane> {

    private static final Logger LOG = LoggerFactory.getLogger(DockingSplitPaneSkin.class);

    private SplitPane splitPane = new SplitPane() {

        @Override
        protected void layoutChildren() {
//            try {
//                throw new IllegalArgumentException();
//            } catch (IllegalArgumentException e) {
//                e.printStackTrace();
//            }
            LOG.debug("{}: layoutChildren...", getSkinnable());
            super.layoutChildren(); //To change body of generated methods, choose Tools | Templates.
        }

//        @Override
//        public void requestLayout() {
//            LOG.debug("{}: requestLayout...", getSkinnable());
//            super.requestLayout(); //To change body of generated methods, choose Tools | Templates.
//        }
    };

    private final ChangeListener<Object> sizeChangeListener = (ov, oldValue, newValue) -> {
        LOG.debug("ObservableValue: {}: old value: {}, new value: {}", ov, oldValue, newValue);
        recalculateDividerPositions();
    };

//    private final ListChangeListener<Node> splitPaneItemsListener = change -> {
//        LOG.debug("SplitPane items change: {}", change);
//        recalculateDividerPositions();
//    };
    private final ListChangeListener<DockingSplitPaneChildBase> dockingSplitPaneChildrenListener = change -> {
//        while (change.next()) {
//            if (change.wasAdded()) {
//                change.getAddedSubList().forEach(child -> addChildListeners(child));
//            }
//            if (change.wasRemoved()) {
//                change.getRemoved().forEach(child -> removeChildListeners(child));
//            }
//        }
        LOG.debug("DockingSplitPane children change: {}", change);
        recalculateDividerPositions();
    };

    private final ChangeListener<Number> dividerPositionChangeListener = (ObservableValue<? extends Number> ov, Number oldValue, Number newValue) -> {
        LOG.debug("{}: divider position changed: old value: {}, new value: {}", getSkinnable(), oldValue, newValue);
        splitPane.getItems().forEach(node -> LOG.debug("{}: property: {} min: {} ; max: {}", node,
                getProperty(splitPane.getOrientation()),
                getMin(splitPane.getOrientation(), (Control) node),
                getMax(splitPane.getOrientation(), (Control) node)));
//        throw new RuntimeException();
    };

    private String getProperty(Orientation orientation) {
        if (orientation == Orientation.HORIZONTAL) {
            return "width";
        } else {
            return "height";
        }
    }

    private double getMin(Orientation orientation, Control control) {
        if (orientation == Orientation.HORIZONTAL) {
            return control.getMinWidth();
        } else {
            return control.getMinHeight();
        }
    }

    private double getMax(Orientation orientation, Control control) {
        if (orientation == Orientation.HORIZONTAL) {
            return control.getMaxWidth();
        } else {
            return control.getMaxHeight();
        }
    }

    private final ListChangeListener<SplitPane.Divider> dividerListener = change -> {
        while (change.next()) {
            if (change.wasAdded()) {
                change.getAddedSubList().forEach(divider
                        -> divider.positionProperty().addListener(dividerPositionChangeListener));
            }
            if (change.wasRemoved()) {
                change.getRemoved().forEach(divider
                        -> divider.positionProperty().removeListener(dividerPositionChangeListener));
            }
        }
    };

    public DockingSplitPaneSkin(DockingSplitPane control) {
        super(control);
        getChildren().add(splitPane);
        splitPane.orientationProperty().bind(getSkinnable().orientationProperty());
//        splitPane.getItems().addAll(control.getDockingSplitPaneChildren());
        Bindings.bindContent(splitPane.getItems(), control.getDockingSplitPaneChildren());
        this.splitPane.getDividers().addListener(dividerListener);
        addDividerPositionChangeListeners();

//        this.splitPane.getItems().addListener(splitPaneItemsListener);
        getSkinnable().getDockingSplitPaneChildren().addListener(dockingSplitPaneChildrenListener);
//        this.control.getDockingSplitPaneChildren().forEach(child -> addChildListeners(child));
//        control.getDockingSplitPaneChildren().addListener(new ListChangeListener<DockingSplitPaneChildBase>() {
//
//            @Override
//            public void onChanged(ListChangeListener.Change<? extends DockingSplitPaneChildBase> change) {
//                while (change.next()) {
//                    for (DockingSplitPaneChildBase child : change.getRemoved()) {
//                        splitPane.getItems().remove(child);
//                    }
//
//                    int index = change.getFrom();
//                    for (DockingSplitPaneChildBase child : change.getAddedSubList()) {
//                        splitPane.getItems().add(index++, child);
//                    }
//                }
//                //TODO enough? wasPermutated? etv. see below also see: https://forums.oracle.com/forums/thread.jspa?threadID=2382997&tstart=0
//
////                while (change.next()) {
////                    if (change.wasPermutated()) {
////                        for (int i = change.getFrom(); i < change.getTo(); ++i) {
////                            // TODO: ???
////                        }
////                    } else if (change.wasUpdated()) {
////                        // TODO: ???
////                    } else if (change.wasRemoved()) {
////                        for (DockablePane remitem : change.getRemoved()) {
////                            // TODO: ???
////                        }
////                    } else if (change.wasAdded()) {
////                        for (int index = change.getFrom(); index < change.getTo(); index++) {
////                            addTab(change.getList().get(index));
////                        }
////                    } else if (change.wasReplaced()) {
////                        // TODO: ???
////                    }
////                }
//            }
//        });

        control.widthProperty().addListener(sizeChangeListener);
        control.heightProperty().addListener(sizeChangeListener);
        control.layoutConstraintsProperty().addListener(sizeChangeListener);
        recalculateDividerPositions();
    }

//    private void addChildListeners(DockingSplitPaneChildBase child) {
//        if (child instanceof DockingSplitPane) {
//            ((DockingSplitPane) child).layoutConstraintsProperty().addListener(sizeChangeListener);
//        }
//        child.widthProperty().addListener(sizeChangeListener);
//        child.heightProperty().addListener(sizeChangeListener);
//    }
//
//    private void removeChildListeners(DockingSplitPaneChildBase child) {
//        if (child instanceof DockingSplitPane) {
//            ((DockingSplitPane) child).layoutConstraintsProperty().removeListener(sizeChangeListener);
//        }
//        child.widthProperty().removeListener(sizeChangeListener);
//        child.heightProperty().removeListener(sizeChangeListener);
//    }
    private void recalculateDividerPositions() {
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
        if (isInSync()) {
            if (getSkinnable().getOrientation().equals(Orientation.HORIZONTAL)) {
                recalculateDividerPositionsHorizontal();
            } else {
                recalculateDividerPositionsVertical();
            }
        }
    }

    private boolean isInSync() {
        return getSkinnable().getDockingSplitPaneChildren().size() == splitPane.getItems().size();
    }

    private void recalculateDividerPositionsHorizontal() {
        double[] prefWidths = new double[getSkinnable().getDockingSplitPaneChildren().size()];
        double[] currentWidths = new double[getSkinnable().getDockingSplitPaneChildren().size()];

        int i = 0;
        for (DockingSplitPaneChildBase child : getSkinnable().getDockingSplitPaneChildren()) {
            LayoutConstraintsDescriptor layoutConstraints = child.getLayoutConstraints();
            prefWidths[i] = layoutConstraints.getPrefWidth();
            if (LayoutConstraintsDescriptor.isPreferred(layoutConstraints.getPrefWidth())) {
                child.prefWidth(layoutConstraints.getPrefWidth());
//                child.minWidth(layoutConstraints.getPrefWidth());
            } else {
                child.prefWidth(Region.USE_COMPUTED_SIZE);
            }
            child.setMaxWidth(Double.MAX_VALUE);
            currentWidths[i] = child.getWidth();
            i++;
        }

        recalculateDividerPositions(prefWidths, currentWidths, getSkinnable().getWidth());
    }

    private void recalculateDividerPositionsVertical() {
        double[] prefHeights = new double[getSkinnable().getDockingSplitPaneChildren().size()];
        double[] currentHeights = new double[getSkinnable().getDockingSplitPaneChildren().size()];

        int i = 0;
        for (DockingSplitPaneChildBase child : getSkinnable().getDockingSplitPaneChildren()) {
            LayoutConstraintsDescriptor layoutConstraints = child.getLayoutConstraints();
            prefHeights[i] = layoutConstraints.getPrefHeight();
            if (LayoutConstraintsDescriptor.isPreferred(layoutConstraints.getPrefHeight())) {
                child.setPrefHeight(layoutConstraints.getPrefHeight());
//                child.minHeight(layoutConstraints.getPrefHeight());
            } else {
                child.setPrefHeight(Region.USE_COMPUTED_SIZE);
            }
            child.setMaxHeight(Double.MAX_VALUE);
            currentHeights[i] = child.getHeight();
            i++;
        }
        recalculateDividerPositions(prefHeights, currentHeights, getSkinnable().getHeight());
    }

    private void recalculateDividerPositions(double[] prefs, double[] current, double parentSize) {
        removeDividerPositionChangeListeners();

        LOG.debug("######################");
        LOG.debug(getSkinnable().toString());
        LOG.debug("Parent size: {}", parentSize);
        LOG.debug("Num children: {}", getSkinnable().getDockingSplitPaneChildren().size());
        LOG.debug("Num items: {}", splitPane.getItems().size());
        double[] relativeSizes = new double[prefs.length];
        double requiredRelativeSize = 0;
        int flexiblePositions = 0;
        if (parentSize > 0) {
            for (int i = 0; i < prefs.length; i++) {
                if (LayoutConstraintsDescriptor.isPreferred(prefs[i])) {
                    relativeSizes[i] = prefs[i] / parentSize;
                    requiredRelativeSize += relativeSizes[i];
                    SplitPane.setResizableWithParent(getSkinnable().getDockingSplitPaneChildren().get(i), Boolean.FALSE);
                } else {
                    relativeSizes[i] = prefs[i];
                    flexiblePositions++;
                    SplitPane.setResizableWithParent(getSkinnable().getDockingSplitPaneChildren().get(i), Boolean.TRUE);
                }
                LOG.debug("{}: pref: {} -> current: {} -> relative pref: {}", i, prefs[i], current[i], relativeSizes[i]);
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

        addDividerPositionChangeListeners();
    }

    private void removeDividerPositionChangeListeners() {
        splitPane.getDividers().
                forEach(divider -> divider.positionProperty().removeListener(dividerPositionChangeListener));
    }

    private void addDividerPositionChangeListeners() {
        splitPane.getDividers().
                forEach(divider -> divider.positionProperty().addListener(dividerPositionChangeListener));
    }

    @Override
    public void dispose() {
        Bindings.unbindContent(splitPane.getItems(), getSkinnable().getDockingSplitPaneChildren());
//        splitPane.getItems().removeListener(splitPaneItemsListener);
        splitPane.getDividers().removeListener(dividerListener);
        removeDividerPositionChangeListeners();
        getSkinnable().getDockingSplitPaneChildren().removeListener(dockingSplitPaneChildrenListener);
//        control.getDockingSplitPaneChildren().forEach(child -> removeChildListeners(child));

        getSkinnable().widthProperty().removeListener(sizeChangeListener);
        getSkinnable().heightProperty().removeListener(sizeChangeListener);
        getSkinnable().layoutConstraintsProperty().removeListener(sizeChangeListener);

        splitPane = null;

        super.dispose();
    }
//
//    @Override
//    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
//        super.layoutChildren(contentX, contentY, contentWidth, contentHeight);
//        recalculateDividerPositions2();
//    }

}
