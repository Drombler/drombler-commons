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
package org.drombler.commons.docking;

/**
 * The dockable preferences defines the preferred Docking Area and the preferred position in that Docking Area.
 *
 * @author puce
 */
// TODO: replace with a JAXB-based implementation
public class DockablePreferences {
    private String areaId;
    private int position;

    /**
     * Creates a new instance of this class.
     */
    public DockablePreferences() {
    }

    /**
     * Copy constructor. Creates a new instance of this class.
     *
     * @param dockablePreferences the DockablePreferences to copy
     */
    public DockablePreferences(DockablePreferences dockablePreferences) {
        this.areaId = dockablePreferences.areaId;
        this.position = dockablePreferences.position;
    }

    /**
     * Gets the preferred Docking Area ID.
     *
     * @return the preferred Docking Area ID
     */
    public String getAreaId() {
        return areaId;
    }

    /**
     * Sets the preferred Docking Area ID.
     *
     * @param areaId the preferred Docking Area ID
     */
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    /**
     * Gets the preferred position in the Docking Area.
     *
     * @return the preferred position in the Docking Area
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the preferred position in the Docking Area.
     *
     * @param position the preferred position in the Docking Area
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String toString() {
        return "DockablePreferences[" + "areaId=" + areaId + ", position=" + position + "]";
    }

}
