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
package org.drombler.commons.docking.fx;

import javafx.scene.Node;
import org.drombler.commons.docking.DockableEntryFactory;
import org.drombler.commons.docking.DockableKind;
import org.drombler.commons.docking.DockablePreferences;

/**
 * A factory for {@link FXDockableEntry}.
 *
 * @author puce
 */
public class FXDockableEntryFactory implements DockableEntryFactory<Node, FXDockableData, FXDockableEntry> {

    /**
     * {@inheritDoc }
     */
    @Override
    public FXDockableEntry createDockableEntry(Node dockable, DockableKind kind, FXDockableData dockableData, DockablePreferences dockablePreferences) {
        return new FXDockableEntry(dockable, kind, dockableData, dockablePreferences);
    }
}
