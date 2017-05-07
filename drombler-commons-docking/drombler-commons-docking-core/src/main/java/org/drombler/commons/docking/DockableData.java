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
 * Copyright 2014 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.docking;

import org.softsmithy.lib.beans.Bean;

/**
 * Data about a Dockable.
 *
 * @author puce
 */
public interface DockableData extends Bean {

    public static final String TITLE_PROPERTY_NAME = "title";
    public static final String MODIFIED_PROPERTY_NAME = "modified";

    /**
     * Gets the title of a Dockable.
     *
     * @return the title of a Dockable
     */
    String getTitle();

    /**
     * Sets the title of a Dockable.
     *
     * @param title the title of a Dockable
     */
    void setTitle(String title);

    boolean isModified();

    void setModified(boolean modified);

}
