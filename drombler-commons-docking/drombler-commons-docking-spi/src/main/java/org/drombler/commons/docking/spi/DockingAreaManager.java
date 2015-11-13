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
package org.drombler.commons.docking.spi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @param <A> the type of the Docking Area
 * @author puce
 */
// TODO: check thread safty
// TODO: move this class to Client - Docking - SPI
public class DockingAreaManager<A extends DockingArea<A>> {

    private final Map<Integer, A> dockingAreas = new HashMap<>();
    private final Map<Integer, DockingAreaManager<A>> dockingAreaManagers = new HashMap<>();
    private final DockingAreaManager<A> parent;
    private final int position;
    private final SplitLevel level;

    public DockingAreaManager(DockingAreaManager<A> parent, int position, int level) {
        this(parent, position, SplitLevel.valueOf(level));
    }

    public DockingAreaManager(DockingAreaManager<A> parent, int position, SplitLevel level) {
        this.parent = parent;
        this.position = position;
        this.level = level;
    }

    public void addDockingArea(List<Integer> path, A dockingArea) {
        addDockingArea(path.iterator(), dockingArea);
    }

    private void addDockingArea(Iterator<Integer> path, A dockingArea) {
        if (path.hasNext()) {
            Integer childPosition = path.next();
            if (!dockingAreaManagers.containsKey(childPosition)) {
                dockingAreaManagers.put(childPosition, new DockingAreaManager<>(this, childPosition,
                        SplitLevel.valueOf(level.getLevel() + 1)));
            }
            dockingAreaManagers.get(childPosition).addDockingArea(path, dockingArea);
        } else {
            // TODO: handle case where 2 dockingAreas have the same position
            // TODO: handle case where dockingArea and dockingAreaManager have the same position
            dockingArea.setParentManager(this);
            dockingAreas.put(dockingArea.getPosition(), dockingArea);
        }
    }

    private ShortPathPart createShortPathPart(int position) {
        return new ShortPathPart(position, level);
    }

    private boolean isShortPathRelevant(int position, boolean emptyPath) {
        return (!isOnlyActualContent(position)) || (emptyPath && parent == null);
    }

    private boolean isOnlyActualContent(int position) {
        return isCurrentlyOnlyActualContent(position)
                || isFutureOnlyActualContent(position);
    }

    private boolean isCurrentlyOnlyActualContent(int position) {
        return containsActualPosition(position) && getActualContentSize() == 1;
    }

    private boolean isFutureOnlyActualContent(int position) {
        return !containsActualPosition(position) && getActualContentSize() == 0;
    }

    private int getActualContentSize() {
        return getNonEmptyAreaManagers().size() + getVisualizableDockingAreas().size();
    }

    private Map<Integer, DockingAreaManager<A>> getNonEmptyAreaManagers() {
        Map<Integer, DockingAreaManager<A>> nonEmptyAreaManagers = new HashMap<>();
        dockingAreaManagers.entrySet().stream().
                filter(entry -> entry.getValue().getActualContentSize() > 0).
                forEach(entry -> nonEmptyAreaManagers.put(entry.getKey(), entry.getValue()));
        return nonEmptyAreaManagers;
    }

    private Map<Integer, A> getVisualizableDockingAreas() {
        Map<Integer, A> visualizableDockingAreas = new HashMap<>();
        dockingAreas.entrySet().stream().
                filter(entry -> entry.getValue().isVisual()).
                forEach(entry -> visualizableDockingAreas.put(entry.getKey(), entry.getValue()));
        return visualizableDockingAreas;
    }

    private boolean containsActualPosition(int position) {
        return (dockingAreaManagers.containsKey(position)
                && dockingAreaManagers.get(position).getActualContentSize() > 0)
                || (dockingAreas.containsKey(position)
                && dockingAreas.get(position).isVisual());
    }

    public List<ShortPathPart> getShortPath(A dockingArea) {
        if (!(dockingAreas.containsKey(dockingArea.getPosition())
                && dockingAreas.get(dockingArea.getPosition()).equals(dockingArea))) {
            throw new IllegalStateException(
                    "The specified docking area must be a child of this manager: " + dockingArea);
        }

        if (dockingArea.isVisual()) {
            return calculateShortPath(dockingArea);
        } else {
            return Collections.emptyList();
        }
    }

    private List<ShortPathPart> calculateShortPath(A dockingArea) {
        List<ShortPathPart> shortPath = calculateReversedShortPath(dockingArea);
        Collections.reverse(shortPath);
        return shortPath;
    }

    private List<ShortPathPart> calculateReversedShortPath(A dockingArea) {
        List<ShortPathPart> shortPath = new ArrayList<>();
        int currentChildPosition = dockingArea.getPosition();
        for (DockingAreaManager<A> currentParent = this; currentParent != null; currentParent = currentParent.parent) {
            if (currentParent.isShortPathRelevant(currentChildPosition, shortPath.isEmpty())) {
                shortPath.add(currentParent.createShortPathPart(currentChildPosition));
            }
            currentChildPosition = currentParent.position;
        }
        return shortPath;
    }

    @Override
    public String toString() {
        return String.format("%s[position=%d, level=%s]", DockingAreaManager.class.getSimpleName(), position, level);
    }

}
