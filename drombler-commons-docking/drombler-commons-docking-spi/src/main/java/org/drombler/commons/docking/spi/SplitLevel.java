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
package org.drombler.commons.docking.spi;

import java.util.Objects;
import org.drombler.commons.client.geometry.Orientation;

/**
 * The split level in a multi split pane.
 *
 * The root level is at level 0 and has a {@link Orientation#VERTICAL} orientation.
 *
 * Each even level has a {@link Orientation#VERTICAL} orientation and each odd level a {@link Orientation#HORIZONTAL}
 * orientation.
 *
 * @author puce
 */
public class SplitLevel {

    /**
     * TODO: field or method?
     */
    public static final SplitLevel ROOT = new SplitLevel(0);
    private final int level;
    private final Orientation orientation;

    private SplitLevel(int level) {
        this.level = level;
        this.orientation = calculateOrientation();
    }

    private Orientation calculateOrientation() {
        return getLevel() % 2 == 0 ? Orientation.VERTICAL : Orientation.HORIZONTAL;
    }

    /**
     * Gets the level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the orientation.
     *
     * @return the orientation
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Returns a SplitLevel for the given level.
     *
     * @param level the level
     * @return a SplitLevel for the given level
     */
    public static SplitLevel valueOf(int level) {
        if (level == ROOT.level) {
            return ROOT;
        } else {
            return new SplitLevel(level);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int hashCode() {
        return Objects.hash(level, orientation);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof SplitLevel)) {
            return false;
        }
        SplitLevel other = (SplitLevel) obj;

        return level == other.level
                && Objects.equals(orientation, other.orientation);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String toString() {
        return "SplitLevel[" + "level=" + level + ", orientation=" + orientation + ']';
    }
}
