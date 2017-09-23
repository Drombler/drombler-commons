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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import org.softsmithy.lib.beans.Bean;
import static org.softsmithy.lib.beans.PropertyChangeUtils.firePropertyChange;

/**
 * The layout constraints of a Docking Area.
 *
 * @author puce
 */
public final class LayoutConstraintsDescriptor implements Bean {

    public static final double FLEXIBLE_PREF = -1.0;

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private double prefWidth;
    private double prefHeight;

    /**
     * Creates a new instance of this class.
     *
     * @param prefWidth the preferred width of the Docking Area. Can be negative to indicate a flexible width.
     * @param prefHeight the preferred height of the Docking Area. Can be negative to indicate a flexible height.
     */
    private LayoutConstraintsDescriptor(double prefWidth, double prefHeight) {
        this.prefWidth = getPrefSize(prefWidth);
        this.prefHeight = getPrefSize(prefHeight);
    }

    private double getPrefSize(double prefSize) {
        if (isPreferred(prefSize)) {
            return prefSize;
        } else {
            return FLEXIBLE_PREF;
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
        return new LayoutConstraintsDescriptor(prefWidth, prefHeight);
    }

    public static LayoutConstraintsDescriptor prefWidth(double prefWidth) {
        return new LayoutConstraintsDescriptor(prefWidth, FLEXIBLE_PREF);
    }

    public static LayoutConstraintsDescriptor prefHeight(double prefHeight) {
        return new LayoutConstraintsDescriptor(FLEXIBLE_PREF, prefHeight);
    }

    public static LayoutConstraintsDescriptor flexible() {
        return new LayoutConstraintsDescriptor(FLEXIBLE_PREF, FLEXIBLE_PREF);
    }

    /**
     * Gets the preferred width of the Docking Area. Can be negative to indicate a flexible width.
     *
     * @return the preferred width of the Docking Area
     */
    public double getPrefWidth() {
        return prefWidth;
    }

    public void setPrefWidth(double prefWidth) {
        double oldValue = this.prefWidth;
        this.prefWidth = getPrefSize(prefWidth);
        firePropertyChange(propertyChangeSupport, "prefWidth", oldValue, this.prefWidth);
    }

    /**
     * Gets the preferred height of the Docking Area. Can be negative to indicate a flexible height.
     *
     * @return the preferred height of the Docking Area
     */
    public double getPrefHeight() {
        return prefHeight;
    }

    public void setPrefHeight(double prefHeight) {
        double oldValue = this.prefHeight;
        this.prefHeight = getPrefSize(prefHeight);
        firePropertyChange(propertyChangeSupport, "prefHeight", oldValue, this.prefHeight);
    }

    public static boolean isFlexible(double size) {
        return !isPreferred(size);
    }

    public static boolean isPreferred(double size) {
        return size >= 0;
    }

    @Override
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    @Override
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
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
