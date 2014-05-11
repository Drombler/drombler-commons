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
import javafx.collections.ListChangeListener;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.control.SplitPane;
import org.drombler.commons.client.docking.LayoutConstraintsDescriptor;
import org.drombler.commons.fx.docking.impl.DockingSplitPane;
import org.drombler.commons.fx.docking.impl.DockingSplitPaneChildBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author puce
 */
public class DockingSplitPaneSkin implements Skin<DockingSplitPane> {
    private static final Logger LOG = LoggerFactory.getLogger(DockingSplitPaneSkin.class);

    private DockingSplitPane control;
    private SplitPane splitPane = new SplitPane();

    private final ListChangeListener<Node> splitPaneItemsListener = change -> recalculateDividerPositions();

    private final ChangeListener<Number> sizeChangeListener = (ov, oldValue, newValue) -> recalculateDividerPositions();

    public DockingSplitPaneSkin(DockingSplitPane control) {
        this.control = control;
        splitPane.orientationProperty().bind(this.control.orientationProperty());
//        splitPane.getItems().addAll(control.getDockingSplitPaneChildren());
        Bindings.bindContent(splitPane.getItems(), control.getDockingSplitPaneChildren());
        this.splitPane.getItems().addListener(splitPaneItemsListener);

        this.control.getDockingSplitPaneChildren().addListener(splitPaneItemsListener);
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
        recalculateDividerPositions();
    }

    private void recalculateDividerPositions() {
        LOG.debug("Split Pane: " + control + ": Orientation: " + control.getOrientation());
        if (control.getOrientation().equals(Orientation.HORIZONTAL)) {
            recalculateDividerPositionsHorizontal();
        } else {
            recalculateDividerPositionsVertical();
        }
    }

    private void recalculateDividerPositionsHorizontal() {
        double[] prefWidths = new double[control.getDockingSplitPaneChildren().size()];
        double[] currentWidths = new double[control.getDockingSplitPaneChildren().size()];

        int i = 0;
        for (DockingSplitPaneChildBase child : control.getDockingSplitPaneChildren()) {
            LayoutConstraintsDescriptor layoutConstraints = child.getLayoutConstraints();
            prefWidths[i] = layoutConstraints.getPrefWidth();
            currentWidths[i] = child.getWidth();
            i++;
        }

        recalculateDividerPositions(prefWidths, currentWidths, control.getWidth());
    }

    private void recalculateDividerPositionsVertical() {
        double[] prefHeights = new double[control.getDockingSplitPaneChildren().size()];
        double[] currentHeights = new double[control.getDockingSplitPaneChildren().size()];

        int i = 0;
        for (DockingSplitPaneChildBase child : control.getDockingSplitPaneChildren()) {
            LayoutConstraintsDescriptor layoutConstraints = child.getLayoutConstraints();
            prefHeights[i] = layoutConstraints.getPrefHeight();
            currentHeights[i] = child.getHeight();
            i++;
        }
        recalculateDividerPositions(prefHeights, currentHeights, control.getHeight());
    }

    private void recalculateDividerPositions(double[] prefs, double[] current, double parentSize) {
        LOG.debug("######################");
        LOG.debug("Level: " + control.getLevel() + "; orientation: " + control.getOrientation());
        LOG.debug("Parent size: " + parentSize);
        double[] relativeSizes = new double[prefs.length];
        double requiredRelativeSize = 0;
        int flexiblePositions = 0;
        if (parentSize > 0) {
            for (int i = 0; i < prefs.length; i++) {
                if (prefs[i] > 0) {
                    relativeSizes[i] = prefs[i] / parentSize;
                    requiredRelativeSize += relativeSizes[i];
                } else {
                    relativeSizes[i] = prefs[i];
                    flexiblePositions++;
                }
                LOG.debug(
                        i + ": pref: " + prefs[i] + " -> current: " + current[i] + " -> relative pref: " + relativeSizes[i]);
            }

            if (requiredRelativeSize > 1) {
                // TODO: shrink sizes
            }

            if (flexiblePositions > 0) {
                double flexibleRelativeSize = (1 - requiredRelativeSize) / flexiblePositions;
                for (int i = 0; i < prefs.length; i++) {
                    if (prefs[i] < 0) {
                        relativeSizes[i] = flexibleRelativeSize;
                    }
                }
            }

            double currentPosition = 0;
            for (int i = 0; i < relativeSizes.length - 1; i++) {
                if (relativeSizes[i] > 0) {
                    currentPosition += relativeSizes[i];
                    splitPane.setDividerPosition(i, currentPosition);
                    LOG.debug(
                            "Set divider position: " + i + " to " + currentPosition + ". Actual position: " + splitPane.
                            getDividerPositions()[i]);
                }
            }
        }
        LOG.debug("Actual divider positions: " + Arrays.toString(splitPane.getDividerPositions()));
        LOG.debug("######################");
        LOG.debug("                      ");
    }

    @Override
    public DockingSplitPane getSkinnable() {
        return control;
    }

    @Override
    public Node getNode() {
        return splitPane;
    }

    @Override
    public void dispose() {
        Bindings.unbindContent(splitPane.getItems(), control.getDockingSplitPaneChildren());
        splitPane.getItems().removeListener(splitPaneItemsListener);
        control.getDockingSplitPaneChildren().removeListener(splitPaneItemsListener);
        control.widthProperty().removeListener(sizeChangeListener);
        control.heightProperty().removeListener(sizeChangeListener);

        control = null;
        splitPane = null;
    }
}
