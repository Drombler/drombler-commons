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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.SetChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.layout.BorderPane;
import org.drombler.commons.client.docking.DockableEntry;
import org.drombler.commons.client.docking.DockablePreferences;
import org.drombler.commons.client.docking.DockingAreaDescriptor;
import org.drombler.commons.client.docking.SplitLevel;
import org.drombler.commons.fx.docking.DockablePane;
import org.drombler.commons.fx.docking.DockingPane;
import org.drombler.commons.fx.docking.impl.DockingAreaManager;
import org.drombler.commons.fx.docking.impl.DockingAreaPane;
import org.drombler.commons.fx.docking.impl.DockingSplitPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softsmithy.lib.util.PositionableAdapter;

/**
 *
 * @author puce
 */
public class DockingPaneSkin implements Skin<DockingPane> {
    private static final Logger LOG = LoggerFactory.getLogger(DockingPaneSkin.class);

    private DockingPane control;
    private BorderPane pane = new BorderPane();
    private DockingSplitPane rootSplitPane = new DockingSplitPane(0, 0, SplitLevel.ROOT);
    private final DockingAreaManager rootDockingAreaManager = new DockingAreaManager(null, 0, SplitLevel.ROOT);
    private final Map<String, DockingAreaPane> dockingAreaPanes = new HashMap<>();
    private final SetChangeListener<DockingAreaDescriptor> dockingAreaDescriptorSetChangeListener = new SetChangeListener<DockingAreaDescriptor>() {

        @Override
        public void onChanged(SetChangeListener.Change<? extends DockingAreaDescriptor> change) {
            if (change.wasAdded()) {
                addDockingArea(change.getElementAdded());
            } else if (change.wasRemoved()) {
// TODO: remove DockingArea
            }
        }
    };

    private final SetChangeListener<DockableEntry<? extends DockablePane>> dockableEntrySetChangeListener = new SetChangeListener<DockableEntry<? extends DockablePane>>() {

        @Override
        public void onChanged(SetChangeListener.Change<? extends DockableEntry<? extends DockablePane>> change) {
            if (change.wasAdded()) {
                addDockable(change.getElementAdded());
            } else if (change.wasRemoved()) {
// TODO: remove Dockable
            }
        }
    };

    public DockingPaneSkin(DockingPane control) {
        this.control = control;
        pane.setCenter(rootSplitPane);
        this.control.getDockingAreaDescriptors().addListener(dockingAreaDescriptorSetChangeListener);
        this.control.getDockables().addListener(dockableEntrySetChangeListener);

        for (DockingAreaDescriptor dockingAreaDescriptor : this.control.getDockingAreaDescriptors()) {
            addDockingArea(dockingAreaDescriptor);
        }

        for (DockableEntry<? extends DockablePane> dockableEntry : this.control.getDockables()) {
            addDockable(dockableEntry);
        }
    }

    @Override
    public DockingPane getSkinnable() {
        return control;
    }

    @Override
    public Node getNode() {
        return pane;
    }

    @Override
    public void dispose() {
        control.getDockables().removeListener(dockableEntrySetChangeListener);
        control.getDockingAreaDescriptors().removeListener(dockingAreaDescriptorSetChangeListener);
        control = null;
        pane = null;
        rootSplitPane = null;
    }

    private void addDockingArea(DockingAreaDescriptor dockingAreaDescriptor) {
        LOG.debug("Added docking area: {}", dockingAreaDescriptor.getId());
        DockingAreaPane dockingArea = createDockingArea(dockingAreaDescriptor);

        dockingArea.getSelectionModel().selectedItemProperty().
                addListener(new ChangeListener<PositionableAdapter<DockablePane>>() {
                    @Override
                    public void changed(ObservableValue<? extends PositionableAdapter<DockablePane>> ov,
                            PositionableAdapter<DockablePane> oldValue, PositionableAdapter<DockablePane> newValue) {
                        if (newValue != null) {
                            control.setActiveDockable(newValue.getAdapted());
                        }
                    }
                });

        dockingArea.getDockables().addListener(new ListChangeListener<PositionableAdapter<DockablePane>>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends PositionableAdapter<DockablePane>> change) {
                while (change.next()) {
                    if (change.wasPermutated()) {
                        for (int i = change.getFrom(); i < change.getTo(); ++i) {
                            // TODO: ???
                        }
                    } else if (change.wasUpdated()) {
                        // TODO: ???
                    } else if (change.wasRemoved()) {
                        removeDockables(change.getRemoved());
                    } else if (change.wasAdded()) {
                        // TODO: ???
                    } else if (change.wasReplaced()) {
                        // TODO: ???
                    }
                }
            }

            private void removeDockables(List<? extends PositionableAdapter<DockablePane>> dockablePanes) {
                for (PositionableAdapter<DockablePane> dockablePane : dockablePanes) {
                    control.removeDockable(dockablePane.getAdapted());
                }
            }
        });
        dockingAreaPanes.put(dockingArea.getAreaId(), dockingArea);
        rootDockingAreaManager.addDockingArea(dockingAreaDescriptor.getPath(), dockingArea);
        handleDockingArea(dockingArea);
    }

    private DockingAreaPane createDockingArea(DockingAreaDescriptor dockingAreaDescriptor) {
        DockingAreaPane dockingAreaPane = new DockingAreaPane(dockingAreaDescriptor.getId(),
                dockingAreaDescriptor.getPosition(), dockingAreaDescriptor.isPermanent());
        dockingAreaPane.setLayoutConstraints(dockingAreaDescriptor.getLayoutConstraints());
        return dockingAreaPane;
    }

    private void addDockable(DockableEntry<? extends DockablePane> dockableEntry) {
        addDockable(dockableEntry.getDockable(), dockableEntry.getDockablePreferences());
    }

    private void addDockable(DockablePane dockablePane, DockablePreferences dockablePreferences) {
        DockingAreaPane dockingArea = getDockingArea(dockablePreferences.getAreaId());
        if (dockingArea != null) { // TODO: needed?
            dockingArea.addDockable(new PositionableAdapter<>(dockablePane,
                    dockablePreferences.getPosition()));
            this.control.setActiveDockable(dockablePane);
            LOG.debug("Dockable '{}' added to the Docking Area '{}'.", dockablePane.getTitle(), dockablePreferences.
                    getAreaId());
        }
    }

    private DockingAreaPane getDockingArea(String areaId) {
        return dockingAreaPanes.get(areaId);
    }

    private void handleDockingArea(DockingAreaPane dockingArea) {
        LOG.debug("Adding docking area: {}", dockingArea.getAreaId());
        if (!dockingArea.isPermanent()) {
            dockingArea.getDockables().addListener(new DockingAreaChangeListener(dockingArea));
        }
        if (dockingArea.isVisualizable()) {
            rootSplitPane.addDockingArea(dockingArea);
        }
    }

    private class DockingAreaChangeListener implements ListChangeListener<PositionableAdapter<DockablePane>> {

        private final DockingAreaPane dockingArea;

        public DockingAreaChangeListener(DockingAreaPane dockingArea) {
            this.dockingArea = dockingArea;
        }

        @Override
        public void onChanged(ListChangeListener.Change<? extends PositionableAdapter<DockablePane>> change) {
            if (dockingArea.isVisualizable() && !dockingArea.isVisualized()) {
                rootSplitPane.addDockingArea(dockingArea);
            } else if (!dockingArea.isVisualizable() && dockingArea.isVisualized()) {
                rootSplitPane.removeDockingArea(dockingArea);
            }
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof DockingAreaChangeListener)) {
                return false;
            }
            DockingAreaChangeListener listener = (DockingAreaChangeListener) obj;
            return Objects.equals(dockingArea, listener.dockingArea);
        }

        @Override
        public int hashCode() {
            return Objects.hash(dockingArea);
        }
    }
}
