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

/**
 * A GUI toolkit agnostic interface which keeps the state (enabled/ disabled) and the logic between menu items and
 * toolbar buttons in sync.
 *
 * TODO: extend EventListener?
 *
 * TODO: E extend EventObject?
 *
 * @author puce
 * @param <E> the type of the action event
 */
public interface ActionListener<E> {

    /**
     * This method which gets called when the action gets fired.
     *
     * @param event the action event
     */
    void onAction(E event);

    /**
     * Returns true, if this action is disabled, else false.
     *
     * @return true, if this action is disabled, else false
     */
    boolean isDisabled();

    /**
     * Registers a {@link PropertyChangeListener} for the specified property.
     *
     * @param propertyName the property to observe
     * @param listener the PropertyChangeListener to register
     */
    void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

    /**
     * Unegisters a {@link PropertyChangeListener} for the specified property.
     *
     * @param propertyName the property to stop to observe
     * @param listener the PropertyChangeListener to unregister
     */
    void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);
}
