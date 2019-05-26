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

import org.softsmithy.lib.beans.Bean;

/**
 * A GUI toolkit agnostic interface which keeps the state (enabled/ disabled) and the logic between menu items and toolbar buttons in sync.
 *
 * @param <E> the type of the action event
 */
// TODO: extend EventListener?
// TODO: E extend EventObject?
public interface ActionListener<E> extends Bean {

    /**
     * This method which gets called when the action gets fired.
     *
     * @param event the action event
     */
    void onAction(E event);

    /**
     * Returns true, if this action is enabled, else false.
     *
     * @return true, if this action is enabled, else false
     */
    boolean isEnabled();

}
