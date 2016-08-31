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

import java.util.EventObject;
import org.drombler.commons.docking.DockableData;
import org.drombler.commons.docking.DockableEntry;

/**
 *
 * @author puce
 * @param <D>
 * @param <DATA>
 * @param <E>
 */
public class DockingAreaContainerDockableEvent<D, DATA extends DockableData, E extends DockableEntry<D, DATA>> extends EventObject {

    private static final long serialVersionUID = -888760518227930671L;

    private final E dockableEntry;

    public DockingAreaContainerDockableEvent(DockingAreaContainer<D, DATA, E> source, E dockableEntry) {
        super(source);
        this.dockableEntry = dockableEntry;
    }

    /**
     * @return the dockableEntry
     */
    public E getDockableEntry() {
        return dockableEntry;
    }

}
