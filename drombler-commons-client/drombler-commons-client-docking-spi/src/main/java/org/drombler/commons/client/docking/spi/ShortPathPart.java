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
package org.drombler.commons.client.docking.spi;

import java.util.Objects;

/**
 *
 * @author puce
 */
public class ShortPathPart {

    private final int position;
    private final SplitLevel inLogicalLevel;

    /**
     * {@code inLogicalLevel} refers to the parent, so read it as: The {@code position} in the split pane at the logical
     * (not shortened) level {@code inLogicalLevel}.
     *
     * @param position
     * @param inLogicalLevel
     */
    public ShortPathPart(int position, int inLogicalLevel) {
        this(position, SplitLevel.valueOf(inLogicalLevel));
    }

    public ShortPathPart(int position, SplitLevel inLogicalLevel) {
        this.position = position;
        this.inLogicalLevel = inLogicalLevel;
    }

    /**
     * @return the pathPart
     */
    public int getPosition() {
        return position;
    }

    /**
     * @return the level
     */
    public SplitLevel getInLogicalLevel() {
        return inLogicalLevel;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int hashCode() {
        return Objects.hash(position, inLogicalLevel);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ShortPathPart)) {
            return false;
        }
        ShortPathPart other = (ShortPathPart) obj;

        return position == other.position
                && Objects.equals(inLogicalLevel, other.inLogicalLevel);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String toString() {
        return "ShortPathPart[position=" + position + ", inLogicalLevel=" + inLogicalLevel + ']';
    }
}
