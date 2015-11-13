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
package org.drombler.commons.docking.fx.impl.skin;

import javafx.beans.binding.Bindings;
import javafx.scene.control.SkinBase;
import javafx.scene.control.SplitPane;
import org.drombler.commons.docking.fx.impl.DockingSplitPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author puce
 */
public class DockingSplitPaneSkin extends SkinBase<DockingSplitPane> {

    private static final Logger LOG = LoggerFactory.getLogger(DockingSplitPaneSkin.class);
    private DividerPositionRecalculator dividerPositionRecalculator;
    private LayoutConstraintsDescriptorManager layoutConstraintsDescriptorManager;
    private DockingSplitPaneChildPreferencesManager dockingSplitPaneChildPreferencesManager;

    private SplitPane splitPane = new SplitPane() {

        @Override
        protected void layoutChildren() {
//            try {
//                throw new IllegalArgumentException();
//            } catch (IllegalArgumentException e) {
//                e.printStackTrace();
//            }
            LOG.debug("{}: layoutChildren...", getSkinnable());
            layoutConstraintsDescriptorManager.setAdjusting(true);
            super.layoutChildren();
            layoutConstraintsDescriptorManager.setAdjusting(false);
            LOG.debug("{}: layoutChildren finished!", getSkinnable());
        }

//        @Override
//        public void requestLayout() {
//            LOG.debug("{}: requestLayout...", getSkinnable());
//            super.requestLayout(); //To change body of generated methods, choose Tools | Templates.
//        }
    };

    public DockingSplitPaneSkin(DockingSplitPane control) {
        super(control);
        getChildren().add(splitPane);
        splitPane.orientationProperty().bind(getSkinnable().orientationProperty());
//        splitPane.getItems().addAll(control.getDockingSplitPaneChildren());
        Bindings.bindContent(splitPane.getItems(), control.getDockingSplitPaneChildren());

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
        layoutConstraintsDescriptorManager = new LayoutConstraintsDescriptorManager(control, splitPane);
        dockingSplitPaneChildPreferencesManager = new DockingSplitPaneChildPreferencesManager(control);

        dividerPositionRecalculator = new DividerPositionRecalculator(control, splitPane);
        dividerPositionRecalculator.adjustingProperty().bindBidirectional(
                layoutConstraintsDescriptorManager.adjustingProperty());
        dividerPositionRecalculator.recalculateDividerPositions();
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
    @Override
    public void dispose() {
        Bindings.unbindContent(splitPane.getItems(), getSkinnable().getDockingSplitPaneChildren());
//        splitPane.getItems().removeListener(splitPaneItemsListener);

//        control.getDockingSplitPaneChildren().forEach(child -> removeChildListeners(child));
        dividerPositionRecalculator.close();
        dividerPositionRecalculator.adjustingProperty().unbindBidirectional(layoutConstraintsDescriptorManager.
                adjustingProperty());
        dividerPositionRecalculator = null;

        dockingSplitPaneChildPreferencesManager.close();
        dockingSplitPaneChildPreferencesManager = null;

        layoutConstraintsDescriptorManager.close();
        layoutConstraintsDescriptorManager = null;

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
