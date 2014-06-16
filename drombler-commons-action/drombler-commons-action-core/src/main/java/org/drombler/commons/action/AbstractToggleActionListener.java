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
 *
 * @author puce
 * @param <E> the type of the action event
 */
public abstract class AbstractToggleActionListener<E> extends AbstractActionListener<E> implements ToggleActionListener<E> {

    private boolean selected = false;

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        if (this.selected != selected) {
            boolean oldValue = this.selected;
            this.selected = selected;
            onSelectionChanged(oldValue, selected);
            getPropertyChangeSupport().firePropertyChange("selected", oldValue, selected);
        }
    }

    /**
     * Does nothing by default.
     *
     * @param event the action event
     */
    @Override
    public void onAction(E event) {
        // do nothing
    }
}
