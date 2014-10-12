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
package org.drombler.commons.fx.docking;

import javafx.scene.Node;
import org.drombler.commons.client.docking.DockableEntry;
import org.drombler.commons.client.docking.DockablePreferences;

/**
 * An entry in the Docking System, which groups a Dockable with its {@link DockablePreferences} and
 * {@link FXDockableData}.
 *
 * @author puce
 */
public class FXDockableEntry extends DockableEntry<Node> {

    private final FXDockableData dockableData;

    /**
     * Creates a new instance of this class.
     *
     * @param dockable the Dockable
     * @param dockableData the {@link FXDockableData}
     * @param dockablePreferences the {@link DockablePreferences}
     */
    public FXDockableEntry(Node dockable, FXDockableData dockableData, DockablePreferences dockablePreferences) {
        super(dockable, dockablePreferences);
        this.dockableData = dockableData;
    }

    /**
     * Gets the {@link FXDockableData}.
     *
     * @return the DockableData
     */
    public FXDockableData getDockableData() {
        return dockableData;
    }

}
