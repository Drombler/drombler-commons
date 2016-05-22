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
 * A manager to manage the {@link DockableData}.
 *
 *
 * @author puce
 * @param <D> the Dockable type
 * @param <DATA> the DockableData type
 */
public interface DockableDataManager<D, DATA extends DockableData> {

    /**
     * Gets the registered {@link DockableData} of the specified Dockable.
     *
     * @param dockable the Dockable
     * @return the DockableData of the specified Dockable
     */
    DATA getDockableData(D dockable);

    /**
     * Registers the {@link DockableData} for the specified Dockable.
     *
     * @param dockable the Dockable
     * @param dockableData the DockableData to register
     */
    void registerDockableData(D dockable, DATA dockableData);

    /**
     * Unregisters the {@link DockableData} for the specified Dockable.
     *
     * @param dockable the Dockable
     * @return the unregistered DockableData
     */
    DATA unregisterDockableData(D dockable);
}
