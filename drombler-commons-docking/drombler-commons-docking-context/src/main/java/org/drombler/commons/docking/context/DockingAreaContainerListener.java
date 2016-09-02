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
package org.drombler.commons.docking.context;

import java.util.EventListener;
import org.drombler.commons.docking.DockableData;
import org.drombler.commons.docking.DockableEntry;

/**
 * TODO: more methods might be added in future
 *
 * @author puce
 * @param <D>
 * @param <DATA>
 * @param <E>
 */
public interface DockingAreaContainerListener<D, DATA extends DockableData, E extends DockableEntry<D, DATA>> extends EventListener {

    void dockingAreaAdded(DockingAreaContainerDockingAreaEvent<D, DATA, E> event);

    void dockingAreaRemoved(DockingAreaContainerDockingAreaEvent<D, DATA, E> event);

    void dockableAdded(DockingAreaContainerDockableEvent<D, DATA, E> event);

    void dockableRemoved(DockingAreaContainerDockableEvent<D, DATA, E> event);

    void activeDockableChanged(DockingAreaContainerActiveDockableChangedEvent<D, DATA, E> event);

//    void activeDockableChanged(PropertyChangeEvent event);
}
