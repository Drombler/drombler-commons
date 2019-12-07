/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2015 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.client.geometry;

/**
 * The horizontal alignment.
 *
 * @author puce
 */
public enum HorizontalAlignment {
    LEFT,
    CENTER,
    RIGHT,
    LEADING {
        /**
         * {@inheritDoc ]
         */
        @Override
        public HorizontalAlignment orient(boolean leftToRight, boolean mirroringEnabled) {
            return leftToRight || mirroringEnabled ? LEFT : RIGHT;
        }
    },
    TRAILING {
        /**
         * {@inheritDoc ]
         */
        @Override
        public HorizontalAlignment orient(boolean leftToRight, boolean mirroringEnabled) {
            return leftToRight || mirroringEnabled ? RIGHT : LEFT;
        }
    };

    /**
     * Gets the horizontal alignment for the given orientation.
     *
     * @param leftToRight left-to-right flag
     * @param mirroringEnabled mirroring enabled flag
     * @return the horizontal alignment for the given orientation
     */
    public HorizontalAlignment orient(boolean leftToRight, boolean mirroringEnabled) {
        return this;
    }

}
