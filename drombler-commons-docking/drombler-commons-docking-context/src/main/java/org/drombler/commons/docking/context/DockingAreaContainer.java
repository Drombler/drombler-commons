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
import org.drombler.commons.context.Context;
import org.drombler.commons.docking.DockableData;
import org.drombler.commons.docking.DockableEntry;
import org.drombler.commons.docking.DockablePreferences;
import org.drombler.commons.docking.DockingAreaDescriptor;
import org.softsmithy.lib.util.ResourceLoader;

/**
 *
 * @author puce
 * @param <D>
 * @param <DATA>
 * @param <E>
 */
public interface DockingAreaContainer<D, DATA extends DockableData, E extends DockableEntry<D, DATA>> {

    boolean addDockingArea(DockingAreaDescriptor dockingAreaDescriptor);

    boolean addDockable(E dockableEntry, boolean active, Context... implicitLocalContexts);

    void addDockingAreaContainerListener(DockingAreaContainerListener<D, DATA, E> listener);

    void removeDockingAreaContainerListener(DockingAreaContainerListener<D, DATA, E> listener);

    String getDefaultEditorAreaId();

    Set<E> getDockables();

    void setActiveDockable(E dockableEntry);

    E getActiveDockable();

    boolean openAndRegisterNewView(D dockable, boolean b, String displayName, String icon, ResourceLoader resourceLoader);

    boolean openView(D dockable, boolean active);

    boolean openEditorForContent(Object content, Class<? extends D> editorType, String icon, ResourceLoader resourceLoader);

    @Deprecated
    void inject(D dockable);

    void registerDefaultDockablePreferences(Class<?> dockableClass, DockablePreferences dockablePreferences);

    DockablePreferences getDockablePreferences(D dockable);

}
