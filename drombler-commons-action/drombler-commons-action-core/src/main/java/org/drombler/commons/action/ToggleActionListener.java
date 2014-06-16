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

/**
 * A GUI toolkit agnostic interface which keeps the state (enabled/ disabled, selected/ unselected etc.) and the logic
 * between toggable menu items and toggable toolbar buttons in sync.
 *
 * @author puce
 * @param <E> the type of the action event
 */
public interface ToggleActionListener<E> extends ActionListener<E> {

    /**
     * Returns true, if this toggable action is selected, else false.
     *
     * @return true, if this toggable action is selected, else false
     */
    boolean isSelected();

    /**
     * Sets the selection state of this toggable action.
     *
     * @param selected true, to select this toggable action, else false
     */
    void setSelected(boolean selected);

    /**
     * This method gets called when the selection state of this toggable action changes.
     *
     * @param oldValue the old selection state
     * @param newValue the new selection state
     */
    void onSelectionChanged(boolean oldValue, boolean newValue);
}
