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

import java.util.List;
import java.util.Objects;

/**
 * The Docking Area descriptor describes a Docking Area.
 *
 * TODO: Immutable?
 *
 * @author puce
 */
public final class DockingAreaDescriptor {

    private String id;
    private int position;
    private List<Integer> parentPath;
    private boolean permanent = false;
    private LayoutConstraintsDescriptor layoutConstraints;

    /**
     * Creates a new instance of this class.
     */
    public DockingAreaDescriptor() {
    }

    /**
     * Gets the Docking Area ID.
     *
     * @return the Docking Area ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the Docking Area ID.
     *
     * @param id the Docking Area ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the position of the Docking Area in the parent split pane.
     *
     * @return the position of the Docking Area in the parent split pane
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the position of the Docking Area in the parent split pane.
     *
     * @param position the position of the Docking Area in the parent split pane
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Gets the path positions of the parent split pane.
     *
     * @return the path positions of the parent split pane
     */
    public List<Integer> getParentPath() {
        return parentPath;
    }

    /**
     * Sets the path positions of the parent split pane.
     *
     * @param parentPath the path positions of the parent split pane
     */
    public void setParentPath(List<Integer> parentPath) {
        this.parentPath = parentPath;
    }

    /**
     * Returns true, if the Docking Area is visible also when it's empty (permanently visible), and returns false, if
     * the Docking Area is only visible when it's not empty
     *
     * @return true if permanently visible, else false
     */
    public boolean isPermanent() {
        return permanent;
    }

    /**
     * Specifies if the Docking Area should be permanently visible. If true, the Docking Area is visible also when it's
     * empty (permanently visible), else the Docking Area is only visible when it's not empty.
     *
     * @param permanent If true, the Docking Area is visible also when it's empty (permanently visible), else the
     * Docking Area is only visible when it's not empty
     */
    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }

    /**
     * Gets the layout constraints of the Docking Area.
     *
     * @return the layout constraints of the Docking Area
     */
    public LayoutConstraintsDescriptor getLayoutConstraints() {
        return layoutConstraints;
    }

    /**
     * Sets the layout constraints of the Docking Area.
     *
     * @param layoutConstraints the layout constraints of the Docking Area
     */
    public void setLayoutConstraints(LayoutConstraintsDescriptor layoutConstraints) {
        this.layoutConstraints = layoutConstraints;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.id);
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
        if (!(obj instanceof DockingAreaDescriptor)) {
            return false;
        }
        final DockingAreaDescriptor other = (DockingAreaDescriptor) obj;
        return Objects.equals(this.id, other.id);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String toString() {
        return "DockingAreaDescriptor{" + "id=" + id + ", position=" + position + ", path=" + parentPath + ", permanent=" + permanent + ", layoutConstraints=" + layoutConstraints + '}';
    }

}
