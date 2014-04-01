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
public class LayoutConstraintsDescriptor {

    private double prefWidth = -1.0;
    private double prefHeight = -1.0;

    /**
     * Creates a new instance of this class.
     */
    public LayoutConstraintsDescriptor() {
    }

    /**
     * Gets the preferred width of the Docking Area. Can be negative to indicate a flexible width (default).
     *
     * @return the preferred width of the Docking Area
     */
    public double getPrefWidth() {
        return prefWidth;
    }

    /**
     * Sets the preferred width of the Docking Area. Can be negative to indicate a flexible width (default).
     *
     * @param prefWidth the preferred width of the Docking Area
     */
    public void setPrefWidth(double prefWidth) {
        this.prefWidth = prefWidth;
    }

    /**
     * Gets the preferred height of the Docking Area. Can be negative to indicate a flexible height (default).
     *
     * @return the preferred height of the Docking Area
     */
    public double getPrefHeight() {
        return prefHeight;
    }

    /**
     * Sets the preferred height of the Docking Area. Can be negative to indicate a flexible height (default).
     *
     * @param prefHeight the preferred height of the Docking Area
     */
    public void setPrefHeight(double prefHeight) {
        this.prefHeight = prefHeight;
    }
}
