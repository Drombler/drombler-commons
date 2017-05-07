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
import org.softsmithy.lib.beans.Bean;
import org.softsmithy.lib.util.ResourceLoader;
import org.softsmithy.lib.util.SetChangeListener;

/**
 *
 * @author puce
 * @param <D>
 * @param <DATA>
 * @param <E>
 */
public interface DockingAreaContainer<D, DATA extends DockableData, E extends DockableEntry<D, DATA>> extends Bean {

    static final String ACTIVE_DOCKABLE_PROPERTY_NAME = "activeDockable";

    boolean addDockingArea(DockingAreaDescriptor dockingAreaDescriptor);

    boolean addDockable(E dockableEntry, boolean active, Context... implicitLocalContexts);

    void addDockingAreaSetChangeListener(SetChangeListener<DockingAreaDescriptor> listener);

    void removeDockingAreaSetChangeListener(SetChangeListener<DockingAreaDescriptor> listener);

    void addDockableSetChangeListener(SetChangeListener<E> listener);

    void removeDockableSetChangeListener(SetChangeListener<E> listener);

    String getDefaultEditorAreaId();

    Set<E> getDockables();

    SortedSet<E> getSortedModifiedDockables();

    /**
     * bound property
     *
     * @param dockableEntry
     */
    void setActiveDockable(E dockableEntry);

    /**
     * bound property
     *
     * @return
     */
    E getActiveDockable();

    E openAndRegisterNewView(Class<? extends D> viewType, boolean active, String displayName, String icon, ResourceLoader resourceLoader);

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

    void closeEditors(Class<? extends D> editorType);

    void registerDefaultDockablePreferences(Class<?> dockableClass, DockablePreferences dockablePreferences);

    DockablePreferences unregisterDefaultDockablePreferences(Class<?> dockableClass);

    DockablePreferences getDockablePreferences(D dockable);

}
