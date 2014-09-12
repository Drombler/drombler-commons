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
 * An injection point for the {@link DockableData}.
 *
 * TODO: Replace with CDI?
 *
 * @param <D> the DockableData type
 *
 * @author puce
 */
public interface DockableDataSensitive<D extends DockableData> {

    /**
     * Sets the {@link DockableData}.
     *
     * @param dockableData the DockableData
     */
    void setDockableData(DockableData dockableData);
}
