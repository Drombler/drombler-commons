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

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.control.SplitPane;
import org.drombler.commons.docking.fx.impl.DockingSplitPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LayoutConstraintsDescriptorManager implements AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(LayoutConstraintsDescriptorManager.class);

    private final Map<SplitPane.Divider, DividerPositionChangeListener> dividerPositionChangeListeners = new HashMap<>();
    private final ListChangeListener<SplitPane.Divider> dividerListener = (javafx.collections.ListChangeListener.Change<? extends javafx.scene.control.SplitPane.Divider> change) -> {
        while (change.next()) {
            if (change.wasAdded()) {
                IntStream.range(change.getFrom(), change.getTo()).
                        forEach(index -> addDividerPositionChangeListener(index));
            }
            if (change.wasRemoved()) {
                change.getRemoved().
                        forEach(divider -> removeDividerPositionChangeListener(divider));
            }
        }
    };
    private final DockingSplitPane dockingSplitPane;
    private final SplitPane splitPane;
    private final BooleanProperty adjusting = new SimpleBooleanProperty(this, "adjusting", false);

    public LayoutConstraintsDescriptorManager(DockingSplitPane dockingSplitPane, SplitPane splitPane) {
        this.dockingSplitPane = dockingSplitPane;
        this.splitPane = splitPane;
        this.splitPane.getDividers().addListener(dividerListener);
        addDividerPositionChangeListeners();
    }

    private void addDividerPositionChangeListener(int dividerIndex) {
        SplitPane.Divider divider = splitPane.getDividers().get(dividerIndex);
        if (dividerPositionChangeListeners.containsKey(divider)) {
            throw new IllegalStateException();
        }
        DividerPositionChangeListener dividerPositionChangeListener = new DividerPositionChangeListener(dividerIndex);
        dividerPositionChangeListener.adjustingProperty().bindBidirectional(adjustingProperty());
        dividerPositionChangeListeners.put(divider, dividerPositionChangeListener);
        divider.positionProperty().addListener(dividerPositionChangeListener);
    }

    private void removeDividerPositionChangeListener(SplitPane.Divider divider) {
        if (!dividerPositionChangeListeners.containsKey(divider)) {
            throw new IllegalStateException();
        }
        final DividerPositionChangeListener dividerPositionChangeListener = dividerPositionChangeListeners.remove(
                divider);
        dividerPositionChangeListener.adjustingProperty().unbindBidirectional(adjustingProperty());
        divider.positionProperty().removeListener(dividerPositionChangeListener);
    }

    private void addDividerPositionChangeListeners() {
        IntStream.range(0, splitPane.getDividers().size()).
                forEach((index) -> addDividerPositionChangeListener(index));
    }

    private void removeDividerPositionChangeListeners() {
        splitPane.getDividers().
                forEach((divider) -> removeDividerPositionChangeListener(divider));
    }

    @Override
    public void close() {
        splitPane.getDividers().removeListener(dividerListener);
        removeDividerPositionChangeListeners();
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

    public class DividerPositionChangeListener implements ChangeListener<Number> {

        private final int dividerIndex;
        private final BooleanProperty adjusting = new SimpleBooleanProperty(this, "adjusting", false);

        public DividerPositionChangeListener(int dividerIndex) {
            this.dividerIndex = dividerIndex;
        }

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            LOG.debug("{}: divider {} position changed: old value: {}, new value: {}, adjusting: {}", dockingSplitPane,
                    dividerIndex, oldValue, newValue, isAdjusting());
            if (!isAdjusting()) {
                setAdjusting(true);
                //        if (LOG.isDebugEnabled()) {
                //            splitPane.getItems().forEach(node -> LOG.debug("{}: property: {} min: {} ; max: {}", node,
                //                    getProperty(splitPane.getOrientation()),
                //                    getMin(splitPane.getOrientation(), (Control) node),
                //                    getMax(splitPane.getOrientation(), (Control) node)));
                //        }
                //        throw new RuntimeException();
                dockingSplitPane.getDockingSplitPaneChildren().get(dividerIndex).updateLayoutConstraints();
                dockingSplitPane.getDockingSplitPaneChildren().get(dividerIndex + 1).updateLayoutConstraints();

                setAdjusting(false);
                LOG.debug("{}: adjusting finished!", dockingSplitPane);
            }
        }

        //    private String getProperty(Orientation orientation) {
//        if (orientation == Orientation.HORIZONTAL) {
//            return "width";
//        } else {
//            return "height";
//        }
//    }
//
//    private double getMin(Orientation orientation, Control control) {
//        if (orientation == Orientation.HORIZONTAL) {
//            return control.getMinWidth();
//        } else {
//            return control.getMinHeight();
//        }
//    }
//
//    private double getMax(Orientation orientation, Control control) {
//        if (orientation == Orientation.HORIZONTAL) {
//            return control.getMaxWidth();
//        } else {
//            return control.getMaxHeight();
//        }
//    }
        public final boolean isAdjusting() {
            return adjustingProperty().get();
        }

        public final void setAdjusting(boolean active) {
            adjustingProperty().set(active);
        }

        public BooleanProperty adjustingProperty() {
            return adjusting;
        }
    }
}
