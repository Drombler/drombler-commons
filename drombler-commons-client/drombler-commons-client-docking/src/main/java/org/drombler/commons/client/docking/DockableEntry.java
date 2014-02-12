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

import java.util.Objects;

public class DockableEntry<D> {

    private final D dockable;
    private final DockablePreferences dockablePreferences;

    public DockableEntry(D dockable, DockablePreferences dockablePreferences) {
        this.dockable = dockable;
        this.dockablePreferences = dockablePreferences;
    }

    /**
     * @return the dockable
     */
    public D getDockable() {
        return dockable;
    }

    /**
     * @return the dockablePreferences
     */
    public DockablePreferences getDockablePreferences() {
        return dockablePreferences;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.dockable);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DockableEntry)) {
            return false;
        }
        final DockableEntry<D> other = (DockableEntry<D>) obj;
        return Objects.equals(this.dockable, other.dockable);
    }

}

