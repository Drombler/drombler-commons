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
package org.drombler.commons.docking.context;

import java.util.Set;
import java.util.SortedSet;
import org.drombler.commons.context.Context;
import org.drombler.commons.docking.DockableData;
import org.drombler.commons.docking.DockableEntry;
import org.drombler.commons.docking.DockablePreferences;
import org.drombler.commons.docking.DockingAreaDescriptor;
import org.drombler.commons.docking.DockingAreaKind;
import org.softsmithy.lib.beans.Bean;
import org.softsmithy.lib.util.ResourceLoader;
import org.softsmithy.lib.util.SetChangeListener;

/**
 * A Docking Area container. A Docking Area container contains a set of Docking Areas and a set of Dockables (Dockable entries), which are possibly attached to a Docking Area.
 *
 * @param <D> the Dockable type
 * @param <DATA> the Dockable data type
 * @param <E> the Dockable entry type
 */
public interface DockingAreaContainer<D, DATA extends DockableData, E extends DockableEntry<D, DATA>> extends Bean {

    /**
     * The activeDockable property name.
     */
    static final String ACTIVE_DOCKABLE_PROPERTY_NAME = "activeDockable";

    /**
     * Adds a Docking Area to this container.
     *
     * @param dockingAreaDescriptor the Docking Area descriptor
     * @return true, if it was added, else false
     */
    boolean addDockingArea(DockingAreaDescriptor dockingAreaDescriptor);

    /**
     * Adds a Dockable to this container.
     *
     * @param dockableEntry the Dockable entry to add
     * @param active flag if the added Dockable entry should be active
     * @param implicitLocalContexts a list of implicit local contexts to be associated with the Dockable
     * @return true, if the Dockable was added, else false
     */
    boolean addDockable(E dockableEntry, boolean active, Context... implicitLocalContexts);

    /**
     * Adds a Docking Area set change listener.
     *
     * @param listener a Docking Area set change listener
     */
    void addDockingAreaSetChangeListener(SetChangeListener<DockingAreaDescriptor> listener);

    /**
     * Removes a Docking Area set change listener.
     *
     * @param listener a Docking Area set change listener
     */
    void removeDockingAreaSetChangeListener(SetChangeListener<DockingAreaDescriptor> listener);

    /**
     * Adds a Dockable set change listener.
     *
     * @param listener a Dockable set change listener
     */
    void addDockableSetChangeListener(SetChangeListener<E> listener);

    /**
     * Removes a Dockable set change listener.
     *
     * @param listener a Dockable set change listener
     */
    void removeDockableSetChangeListener(SetChangeListener<E> listener);

    /**
     * Gets the defaul Docking Area id fo
     *
     * @return
     * @see DockingAreaKind#EDITOR
     */
    String getDefaultEditorAreaId();

    /**
     * Gets all Dockables of this Docking Area container.
     *
     * @return all Dockables of this Docking Area container
     */
    Set<E> getDockables();

    /**
     * Gets all modified Dockables of this Docking Area container, sorted by their titles.
     *
     * @return all Dockables of this Docking Area container
     * @see DockableData#getTitle()
     */
    SortedSet<E> getSortedModifiedDockables();

    /**
     * Sets the active Dockable.
     *
     * This is a bound property.
     *
     * @param dockableEntry the Dockable to set active
     */
    void setActiveDockable(E dockableEntry);

    /**
     * Gets the active Dockable.
     *
     * This is a bound property.
     *
     * @return the active Dockable
     */
    E getActiveDockable();

    /**
     * Opens and registers a new view Dockable.
     *
     * @param viewType the view type
     * @param active flag if the new view should be set as the active Dockable in this container.
     * @param displayName the display name of th the view
     * @param icon the icon name pattern to get icon for the view
     * @param resourceLoader the resource loader to load the icon
     * @return the Dockable entry for the view
     */
    E openAndRegisterNewView(Class<? extends D> viewType, boolean active, String displayName, String icon, ResourceLoader resourceLoader);

    /**
     * Closes and unregisters a view.
     *
     * @param viewEntry the view entry for the view to close and unregister
     */
    void closeAndUnregisterView(E viewEntry);

    /**
     * Opens an Editor for the specified content.
     *
     * If there is already an open Editor for the specified content, no new Editor will be opened but the existing one will be set active.
     *
     * @param content the content for the editor to open/ select.
     * @param editorType the type of the Editor. It must provide a constructor which takes the specified content as its single parameter.
     * @param icon the icon to use for the Editor
     * @param resourceLoader the {@link ResourceLoader} to load the icon
     * @return true if the Editor could be opened/ selected else false
     */
    // TODO: require UniqueKeyProvider<?> or DataHandler<?> as content?
    E openEditorForContent(Object content, Class<? extends D> editorType, String icon, ResourceLoader resourceLoader);

    /**
     * Closes all editors of the specified type.
     *
     * @param editorType the editor type
     */
    void closeEditors(Class<? extends D> editorType);

    /**
     * Registers the default {@link DockablePreferences} for the specified Dockable class.
     *
     * @param dockableClass the Dockable class
     * @param dockablePreferences the default DockablePreferences
     */
    void registerDefaultDockablePreferences(Class<?> dockableClass, DockablePreferences dockablePreferences);

    /**
     * Unregisters the default {@link DockablePreferences} for the specified Dockable class.
     *
     * @param dockableClass the Dockable class
     * @return the previously registered DockablePreferences if any, else null
     */
    DockablePreferences unregisterDefaultDockablePreferences(Class<?> dockableClass);

    /**
     * Gets the {@link DockablePreferences} for the specified Dockable.
     *
     * @param dockable the Dockable
     * @return the DockablePreferences
     */
    DockablePreferences getDockablePreferences(D dockable);

}
