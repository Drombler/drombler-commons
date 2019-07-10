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
package org.drombler.commons.fx.geometry;

import java.util.EnumMap;
import java.util.Map;
import javafx.geometry.Orientation;

/**
 * Utility class for {@link Orientation}.
 *
 * @author puce
 */
public final class OrientationUtils {

    //TODO: not safe as changes to Orientation are missed at compile time. 
    // Possible solutions:
    // - Use a unit test to test if the static block throws an IllegalStateException (current solution) 
    private static final Map<org.drombler.commons.client.geometry.Orientation, Orientation> ORIENTATIONS
            = new EnumMap<>(org.drombler.commons.client.geometry.Orientation.class);

    static {
        ORIENTATIONS.put(org.drombler.commons.client.geometry.Orientation.HORIZONTAL, Orientation.HORIZONTAL);
        ORIENTATIONS.put(org.drombler.commons.client.geometry.Orientation.VERTICAL, Orientation.VERTICAL);
        for (org.drombler.commons.client.geometry.Orientation orientation : org.drombler.commons.client.geometry.Orientation.
                values()) {
            if (!ORIENTATIONS.containsKey(orientation)) {
                throw new IllegalStateException("No mapping for: " + orientation);
            }
        }
    }

    /**
     * Gets the JavaFX orientation for the given {@code org.drombler.commons.client.geometry.Orientation}.
     *
     * @param orientation an {@code org.drombler.commons.client.geometry.Orientation}
     * @return the JavaFX orientation for the given {@code org.drombler.commons.client.geometry.Orientation}
     */
    public static Orientation getOrientation(org.drombler.commons.client.geometry.Orientation orientation) {
        return ORIENTATIONS.get(orientation);
    }
}
