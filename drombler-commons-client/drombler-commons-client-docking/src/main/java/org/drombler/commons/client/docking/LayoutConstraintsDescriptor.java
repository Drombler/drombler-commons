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
package org.drombler.commons.client.docking;

/**
 * The layout constraints of a Docking Area.
 *
 * @author puce
 */
public final class LayoutConstraintsDescriptor {

    public static final double FLEXIBLE_PREF = -1.0;
    private static final LayoutConstraintsDescriptor FLEXIBLE_LAYOUT_CONSTRAINTS_DESCRIPTOR
            = new LayoutConstraintsDescriptor(FLEXIBLE_PREF, FLEXIBLE_PREF);

    private final double prefWidth;
    private final double prefHeight;

    /**
     * Creates a new instance of this class.
     *
     * @param prefWidth the preferred width of the Docking Area. Can be negative to indicate a flexible width.
     * @param prefHeight the preferred height of the Docking Area. Can be negative to indicate a flexible height.
     */
    private LayoutConstraintsDescriptor(double prefWidth, double prefHeight) {
        if (prefWidth >= 0) {
            this.prefWidth = prefWidth;
        } else {
            this.prefWidth = FLEXIBLE_PREF;
        }

        if (prefHeight >= 0) {
            this.prefHeight = prefHeight;
        } else {
            this.prefHeight = FLEXIBLE_PREF;
        }
    }

    /**
     * Gets a LayoutConstraintsDescriptor with the specified preferred width and preferred height
     *
     * @param prefWidth the preferred width of the Docking Area. Can be negative to indicate a flexible width.
     * @param prefHeight the preferred height of the Docking Area. Can be negative to indicate a flexible height.
     * @return a LayoutConstraintsDescriptor
     */
    public static LayoutConstraintsDescriptor getLayoutConstraints(double prefWidth, double prefHeight) {
        if (prefWidth < 0 && prefHeight < 0) {
            return FLEXIBLE_LAYOUT_CONSTRAINTS_DESCRIPTOR;
        } else {
            return new LayoutConstraintsDescriptor(prefWidth, prefHeight);
        }
    }

    public static LayoutConstraintsDescriptor prefWidth(double prefWidth) {
        if (prefWidth < 0) {
            return FLEXIBLE_LAYOUT_CONSTRAINTS_DESCRIPTOR;
        } else {
            return new LayoutConstraintsDescriptor(prefWidth, FLEXIBLE_PREF);
        }
    }

    public static LayoutConstraintsDescriptor prefHeight(double prefHeight) {
        if (prefHeight < 0) {
            return FLEXIBLE_LAYOUT_CONSTRAINTS_DESCRIPTOR;
        } else {
            return new LayoutConstraintsDescriptor(FLEXIBLE_PREF, prefHeight);
        }
    }

    public static LayoutConstraintsDescriptor flexible() {
        return FLEXIBLE_LAYOUT_CONSTRAINTS_DESCRIPTOR;
    }

    /**
     * Gets the preferred width of the Docking Area. Can be negative to indicate a flexible width.
     *
     * @return the preferred width of the Docking Area
     */
    public double getPrefWidth() {
        return prefWidth;
    }

    /**
     * Gets the preferred height of the Docking Area. Can be negative to indicate a flexible height.
     *
     * @return the preferred height of the Docking Area
     */
    public double getPrefHeight() {
        return prefHeight;
    }

    @Override
    public String toString() {
        return "LayoutConstraintsDescriptor[prefWidth=" + prefWidth + ", prefHeight=" + prefHeight + "]";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.prefWidth) ^ (Double.doubleToLongBits(this.prefWidth) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.prefHeight) ^ (Double.doubleToLongBits(this.prefHeight) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LayoutConstraintsDescriptor)) {
            return false;
        }
        LayoutConstraintsDescriptor descriptor = (LayoutConstraintsDescriptor) obj;
        return prefWidth == descriptor.prefWidth
                && prefHeight == descriptor.prefHeight;
    }

}
