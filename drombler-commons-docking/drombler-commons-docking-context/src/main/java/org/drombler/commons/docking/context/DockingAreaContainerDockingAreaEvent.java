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

import java.util.EventObject;
import org.drombler.commons.docking.DockableData;
import org.drombler.commons.docking.DockableEntry;

/**
 *
 * @author puce
 * @param <D>
 * @param <DATA>
 * @param <E>
 */
public class DockingAreaContainerDockingAreaEvent<D, DATA extends DockableData, E extends DockableEntry<D, DATA>> extends EventObject {

    private static final long serialVersionUID = 3793017833508157560L;

    private final String areaId;

    public DockingAreaContainerDockingAreaEvent(DockingAreaContainer<D, DATA, E> source, String areaId) {
        super(source);
        this.areaId = areaId;
    }

    /**
     * @return the areaId
     */
    public String getAreaId() {
        return areaId;
    }

}
