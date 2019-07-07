/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2015 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.docking.spi;

import org.drombler.commons.docking.Deselect;
import org.drombler.commons.docking.DockableEntry;
import org.drombler.commons.docking.Select;
import org.softsmithy.lib.util.PositionableAdapter;

/**
 * A utility class for Docking Areas.
 *
 * @author puce77
 */
public final class DockingAreaUtils {

    private DockingAreaUtils() {
    }

    /**
     * Handles onSelectionChanged events.<br>
     * <br>
     * Calls the method of the previously selected Dockable, which is annotated with {@link Deselect}. <br>
     * Calls the method of the currently selected Dockable, which is annotated with {@link Select}.
     *
     * @param oldValue the previously selected Dockable
     * @param newValue the currently selected Dockable
     */
    public static void onSelectionChanged(PositionableAdapter<? extends DockableEntry<?, ?>> oldValue,
            PositionableAdapter<? extends DockableEntry<?, ?>> newValue) {
        RefelectionUtils.invokeAnnotatedDockableMethod(oldValue, Deselect.class);
        RefelectionUtils.invokeAnnotatedDockableMethod(newValue, Select.class);
    }
}
