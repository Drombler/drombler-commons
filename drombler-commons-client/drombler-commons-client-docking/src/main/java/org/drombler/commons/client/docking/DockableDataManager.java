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
package org.drombler.commons.client.docking;

/**
 * A manager to manage the {@link FXDockableData}.
 *
 *
 * @author puce
 * @param <D> the Dockable type
 * @param <DATA>
 */
public interface DockableDataManager<D, DATA extends DockableData> {

    /**
     * Gets the {@link FXDockableData} of the specified Dockable.
     *
     * @param dockable the Dockable
     * @return the FXDockableData of the specified Dockable
     */
    DATA getDockableData(D dockable);

    DATA getClassDockableData(D dockable);

    void registerDockableData(D dockable, DATA dockableData);

    DATA unregisterDockableData(D dockable);

    void registerClassDockableData(Class<?> type, DATA dockableData);

    DATA unregisterClassDockableData(Class<?> type);

}
