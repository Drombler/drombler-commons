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
 * @param <DATA> the Dockable data type
 */
public class DockableEntry<D, DATA extends DockableData> {

    private final D dockable;
    private final DATA dockableData;
    private final DockableKind kind;
    private final DockablePreferences dockablePreferences;

    /**
     * Creates a new instance of this class.
     *
     * @param dockable the Dockable
     * @param dockableData
     * @param kind the kind of the Dockable
     * @param dockablePreferences the {@link DockablePreferences} of the Dockable
     */
    protected DockableEntry(D dockable, DockableKind kind, DATA dockableData, DockablePreferences dockablePreferences) {
        this.dockable = dockable;
        this.kind = kind;
        this.dockableData = dockableData;
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
     * Gets the kind of the Dockable.
     *
     * @return the kind of the Dockable
     */
    public DockableKind getKind() {
        return kind;
    }

    /**
     * Gets the {@link DockableData}.
     *
     * @return the DockableData
     */
    public DATA getDockableData() {
        return dockableData;
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
        final DockableEntry<?, ?> other = (DockableEntry<?, ?>) obj;
        return Objects.equals(this.dockable, other.dockable);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String toString() {
        return "DockableEntry[dockablePreferences=" + dockablePreferences + "]";
    }

}
