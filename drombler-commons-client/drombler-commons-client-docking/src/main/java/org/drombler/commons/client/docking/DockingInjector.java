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
 * Copyright 2014 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.client.docking;

/**
 * TODO: Replace with CDI?
 *
 * @author puce
 * @param <D>
 * @param <DATA>
 */
public class DockingInjector<D, DATA extends DockableData> {

    private final DockableDataManager<D, DATA> dockableDataManager;

    public DockingInjector(DockableDataManager<D, DATA> dockableDataManager) {
        this.dockableDataManager = dockableDataManager;
    }

    public void inject(D target) {
        if (target instanceof DockableDataSensitive) {
            ((DockableDataSensitive<DATA>) target).setDockableData(dockableDataManager.getDockableData(target));
        }
    }
}
