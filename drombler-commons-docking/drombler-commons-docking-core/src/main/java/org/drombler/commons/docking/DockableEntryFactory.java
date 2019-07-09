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
package org.drombler.commons.docking;

/**
 * A {@link DockableEntry} factory.
 *
 * @author puce
 * @param <D> the Dockable type
 * @param <DATA> the Dockable data type
 * @param <E> the Dockable entry type
 */
public interface DockableEntryFactory<D, DATA extends DockableData, E extends DockableEntry<D, DATA>> {

    /**
     * Creates a new Dockable entry.
     *
     * @param dockable the Dockable
     * @param kind the kind of the Dockable
     * @param dockableData the Dockable data
     * @param dockablePreferences the Dockable preferences
     * @return a new Dockable entry
     */
    E createDockableEntry(D dockable, DockableKind kind, DATA dockableData, DockablePreferences dockablePreferences);
}
