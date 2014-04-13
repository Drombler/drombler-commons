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
package org.drombler.commons.client.docking;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Objects;

/**
 * TODO: good name?
 *
 * @author puce
 */
final class DockableInfo {

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private String title;
    private String icon;

    /**
     * Gets the title of the Dockable.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the Dockable.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        if (!Objects.equals(this.title, title)) {
            String oldTitle = this.title;
            this.title = title;
            propertyChangeSupport.firePropertyChange("title", oldTitle, title);
        }
    }

    public String getIcon() {
        return icon;
    }

    protected void setIcon(String icon) {
        if (!Objects.equals(this.icon, icon)) {
            String oldValue = this.icon;
            this.icon = icon;
            getPropertyChangeSupport().firePropertyChange("icon", oldValue, icon);
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        getPropertyChangeSupport().addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        getPropertyChangeSupport().addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        getPropertyChangeSupport().removePropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        getPropertyChangeSupport().removePropertyChangeListener(propertyName, listener);
    }

    private PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

}
