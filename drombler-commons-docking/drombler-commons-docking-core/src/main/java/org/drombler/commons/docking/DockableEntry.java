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
package org.drombler.commons.docking;

import java.util.Objects;

/**
 * An entry in the Docking System, which groups a Dockable with its {@link DockablePreferences}.
 *
 * @author puce
 * @param <D> the Dockable type
 */
public class DockableEntry<D> {

    private final D dockable;
    private final DockablePreferences dockablePreferences;

    /**
     * Creates a new instance of this class.
     *
     * @param dockable the Dockable
     * @param dockablePreferences the {@link DockablePreferences} of the Dockable
     */
    protected DockableEntry(D dockable, DockablePreferences dockablePreferences) {
        this.dockable = dockable;
        this.dockablePreferences = dockablePreferences;
    }

    /**
     * Gets the Dockable.
     *
     * @return the Dockable
     */
    public D getDockable() {
        return dockable;
    }


    /**
     * Gets the dockable preferences.
     *
     * @return the dockable preferences
     */
    public DockablePreferences getDockablePreferences() {
        return dockablePreferences;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.dockable);
        return hash;
    }

    /**
     * {@inheritDoc }
     */
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

    @Override
    public String toString() {
        return "DockableEntry[dockablePreferences=" + dockablePreferences + "]";
    }

}
