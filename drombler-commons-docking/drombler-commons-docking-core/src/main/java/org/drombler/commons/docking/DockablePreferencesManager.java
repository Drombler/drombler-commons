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
 * A manager to manage the {@link DockablePreferences}.
 *
 * Note: This class is some pre-work for changing (once drag'n'drop is supported) DockablePreferences.
 *
 * @author puce
 * @param <D> the Dockable type
 */
public interface DockablePreferencesManager<D> {

    /**
     * Gets the {@link DockablePreferences} of the specified Dockable.
     *
     * @param dockable the Dockable
     * @return the DockablePreferences of the specified Dockable
     */
    DockablePreferences getDockablePreferences(D dockable);

    /**
     * Registers the default {@link DockablePreferences}
     *
     * @param dockableClass
     * @param dockablePreferences
     */
    void registerDefaultDockablePreferences(Class<?> dockableClass, DockablePreferences dockablePreferences);
    
//    void registerDockablePreferences(D dockable, DockablePreferences dockablePreferences);
    /**
     * Unregisters the {@link DockablePreferences} of the specified Dockable.
     *
     * @param dockable the {@link DockablePreferences} of the specified Dockable
     * @return
     */
    DockablePreferences unregisterDockablePreferences(D dockable);

    /**
     * Resets the {@link DockablePreferences} of all Dockables to their default values.
     */
    void reset();
}
