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
 * Copyright 2018 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.settings.fx;

import javafx.scene.Node;

/**
 *
 * @author puce
 */

@Deprecated
public class Settings<T> {

    private final SettingsCategory category;
    private final Class<T> settingsType;
    private final Class<? extends Node> paneType;

    /**
     *
     * @param category
     * @param settingsType
     * @param paneType
     */
    public Settings(SettingsCategory category, Class<T> settingsType, Class<? extends Node> paneType) {
        this.category = category;
        this.settingsType = settingsType;
        this.paneType = paneType;
    }
    
}
