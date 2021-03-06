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
package org.drombler.commons.action;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * A base class for {@link ActionListener}s.
 *
 * @author puce
 * @param <E> the type of the action event
 */
public abstract class AbstractActionListener<E> implements ActionListener<E> {

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private boolean enabled = true;

    /**
     * Creates a new instance of this class.
     */
    public AbstractActionListener() {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the enabled state of this Action.
     *
     * @param enabled the enabled state of this Action
     */
    protected void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            boolean oldValue = this.enabled;
            this.enabled = enabled;
            getPropertyChangeSupport().firePropertyChange("enabled", oldValue, enabled);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        getPropertyChangeSupport().addPropertyChangeListener(propertyName, listener);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        getPropertyChangeSupport().removePropertyChangeListener(propertyName, listener);
    }

    /**
     * Gets the {@link PropertyChangeSupport} of this bean.
     *
     * @return the PropertyChangeSupport
     */
    protected PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }
}
